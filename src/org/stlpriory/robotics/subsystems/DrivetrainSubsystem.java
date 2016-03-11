package org.stlpriory.robotics.subsystems;

import org.stlpriory.robotics.OI;
import org.stlpriory.robotics.Robot;
import org.stlpriory.robotics.commands.drivetrain.DriveWithGamepad;
import org.stlpriory.robotics.hardware.CIMMotorSpecs;
import org.stlpriory.robotics.utils.Debug;
import org.stlpriory.robotics.utils.Ramper;
import org.stlpriory.robotics.utils.Utils;

import edu.wpi.first.wpilibj.AnalogGyro;
import edu.wpi.first.wpilibj.BuiltInAccelerometer;
import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.CANTalon.TalonControlMode;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Robot drive train subsystem consisting of 4 CIM motors configured in 2 master/slave arrangements. 
 * The motors are controlled by Talon SRX speed controllers wired for PWM control.
 */
public class DrivetrainSubsystem extends Subsystem {

    public static final int LF_MOTOR_ID = 3;
    public static final int LR_MOTOR_ID = 4;
    public static final int RF_MOTOR_ID = 2;
    public static final int RR_MOTOR_ID = 1;

    public static final int GYRO_PORT = 1;
    
    public enum Direction {FORWARD, REVERSE};
    public Ramper leftRamper, rightRamper, rightRearRamper, leftRearRamper;

    public static final double DEFAULT_FORWARD_SPEED = 1;

    public static final boolean MASTER_SLAVE_MODE = true;
    public static final double FORWARD_SPEED = 0.7;
    public static final double AUTO_TURN_SPEED = 0.3;
    public static final double ACCELEROMETER_TOLERANCE = .05;
    public static final double GYRO_TOLERANCE = .1;

    private final CANTalon rightFront;
    private final CANTalon rightRear;
    private final CANTalon leftFront;
    private final CANTalon leftRear;
    private double lastAngle;

    private final AnalogGyro gyro;
    private final BuiltInAccelerometer accelerometer;


    // ==================================================================================
    //                        C O N S T R U C T O R S
    // ==================================================================================

    public DrivetrainSubsystem() {
        this("DrivetrainSubsystem");
    }

    public DrivetrainSubsystem(final String name) {
        super(name);
        Debug.println("[Drivetrain Subsystem] Instantiating...");

        this.leftFront  = createMaster(LF_MOTOR_ID);
        this.rightFront = createMaster(RF_MOTOR_ID);
        if (MASTER_SLAVE_MODE) {
            this.leftRear  = createSlave(LR_MOTOR_ID, this.leftFront);        	
            this.rightRear = createSlave(RR_MOTOR_ID, this.rightFront);            
        } else {
            this.leftRear  = createMaster(LR_MOTOR_ID);
            this.rightRear = createMaster(RR_MOTOR_ID);
        }
        this.leftRamper = new Ramper();
        this.rightRamper = new Ramper();
        this.rightRearRamper = new Ramper();
        this.leftRearRamper = new Ramper();
        gyro = new AnalogGyro(GYRO_PORT);
        gyro.initGyro();
        accelerometer = new BuiltInAccelerometer();
        Debug.println("[DriveTrain Subsystem] Instantiation complete.");
    }

    public void tankDrive(final double leftStickValue, final double rightStickValue) {
    	leftFront.set(-leftRamper.scale(leftStickValue));
    	rightFront.set(rightRamper.scale(rightStickValue));
    	if(!MASTER_SLAVE_MODE)
    	{
    		leftRear.set(-leftRearRamper.scale(leftStickValue));
    		rightRear.set(rightRearRamper.scale(rightStickValue));
    	}
    }

    public void controllerDrive(final Joystick joystick) {
        // This sums the triggers and the sticks appropriately so that they might
        // maybe work in a very natural-feeling way. I tried to make it so that it 
        // would also use the gyro if possible. We'll see how it works. 
        double rightValue = 0;
        double leftValue = 0;
        double leftStickValue  = Utils.scale(joystick.getRawAxis(OI.LEFT_STICK_Y_AXIS) );
        double rightStickValue = Utils.scale(joystick.getRawAxis(OI.RIGHT_STICK_Y_AXIS) );
        double rightTrigger = joystick.getRawAxis(OI.RIGHT_TRIGGER);
        double leftTrigger = joystick.getRawAxis(OI.LEFT_TRIGGER);
        rightValue -= rightTrigger;
        leftValue -= rightTrigger;
        rightValue += leftTrigger;
        leftValue += leftTrigger;
        rightValue += rightStickValue;
        leftValue += leftStickValue;
        if(leftStickValue == 0 && rightStickValue == 0 && (leftTrigger > .05 || rightTrigger > .05))
        {
            driveForward(rightValue, lastAngle);
        }
        else 
        {
            lastAngle = getAngle();
            tankDrive(leftValue, rightValue);
        }
    }

    public void arcadeDrive(double speed, double rotation)
    {
        // I stole this from WPILib. Shhhh...
    	double leftMotorSpeed;
    	double rightMotorSpeed;
        if (speed > 0.0) {
            if (rotation > 0.0) {
                leftMotorSpeed = speed - rotation;
                rightMotorSpeed = Math.max(speed, rotation);
            } else {
                leftMotorSpeed = Math.max(speed, -rotation);
                rightMotorSpeed = speed + rotation;
            }
        } else {
            if (rotation > 0.0) {
                leftMotorSpeed = -Math.max(-speed, rotation);
                rightMotorSpeed = speed + rotation;
            } else {
                leftMotorSpeed = speed - rotation;
                rightMotorSpeed = -Math.max(-speed, -rotation);
            }
        }
        tankDrive(leftMotorSpeed, rightMotorSpeed);
    }
    public void driveForward(double speed, double desiredHeading)
    {
        // I stole this from the internet too.
        final double kP = .01;
        arcadeDrive(speed, (desiredHeading - getAngle()) * kP);
    }
    public void driveForward(double speed)
    {
        driveForward(speed, gyro.getAngle());
    }
    public void stop() {
        rightFront.set(0);
        leftFront.set(0);
        if(!MASTER_SLAVE_MODE)
        {
        	leftRear.set(0);
        	rightRear.set(0);
        }
    }

    @Override
    public void initDefaultCommand() {
        setDefaultCommand(new DriveWithGamepad());
    }
    
    public double getSpeedInRPM() {
        // Since the 2 left motors and 2 right motors are paired in a 
        // master/slave arrangement we only need to check the master
        Joystick joystick  = Robot.oi.getController();
        double leftYstick  = joystick.getRawAxis(OI.LEFT_STICK_Y_AXIS);
        double rightYstick = joystick.getRawAxis(OI.RIGHT_STICK_Y_AXIS);
        double leftSide  = leftYstick * CIMMotorSpecs.MAX_SPEED_RPM;
        double rightSide = rightYstick * CIMMotorSpecs.MAX_SPEED_RPM;
        
        return (leftSide + rightSide) / 2.0;
    }

    public double getPosition()
    {
        // Right front seems good
        return rightFront.getEncPosition();
    }
    public double getSpeed()
    {
        return (leftFront.getSpeed() + rightFront.getSpeed()) / 2;
    }
    public double getAngle()
    {
        return gyro.getAngle();
    }

    public double getY()
    {
        return accelerometer.getY();
    }
    public double getX()
    {
        return accelerometer.getX();
    }
    public double getZ()
    {
        return accelerometer.getZ();
    }
    public boolean isFlat()
    {
        return Math.abs(Robot.drivetrain.getZ() - 1) > DrivetrainSubsystem.ACCELEROMETER_TOLERANCE;
    }
    
    public void zeroGyro()
    {
    	this.gyro.calibrate();
    }
    public void updateStatus() {
        // double leftFrontMotorOutput  = this.leftFront.getOutputVoltage() / this.leftFront.getBusVoltage();
        // double leftRearMotorOutput   = this.leftRear.getOutputVoltage() / this.leftRear.getBusVoltage();
        // double rightFrontMotorOutput = this.rightFront.getOutputVoltage() / this.rightFront.getBusVoltage();
        // double rightRearMotorOutput  = this.rightRear.getOutputVoltage() / this.rightRear.getBusVoltage();

        // SmartDashboard.putString("Control Mode", rightFront.getControlMode().toString());
        // SmartDashboard.putNumber("LF motor output", leftFrontMotorOutput);
        // SmartDashboard.putNumber("LR motor output", leftRearMotorOutput);
        // SmartDashboard.putNumber("RF motor output", rightFrontMotorOutput);
        // SmartDashboard.putNumber("RR motor output", rightRearMotorOutput);
    	SmartDashboard.putNumber("Right encoder", rightFront.getEncPosition());
    	SmartDashboard.putNumber("Left encoder", leftFront.getEncPosition());
    	SmartDashboard.putNumber("Angle", getAngle());
    	SmartDashboard.putNumber("X axis", getX());
    	SmartDashboard.putNumber("Y axis", getY());
        SmartDashboard.putNumber("Z axis", getZ());
//    	SmartDashboard.putNumber("Current Left", leftFront.getOutputCurrent());
//    	SmartDashboard.putNumber("Current Right", rightFront.getOutputCurrent());
         SmartDashboard.putNumber("LF power draw", leftFront.getOutputCurrent());
         SmartDashboard.putNumber("LR power draw", leftRear.getOutputCurrent());
         SmartDashboard.putNumber("RF power draw", rightFront.getOutputCurrent());
         SmartDashboard.putNumber("RR power draw", rightRear.getOutputCurrent());
    }

    // ==================================================================================
    //                    P R O T E C T E D   M E T H O D S
    // ==================================================================================

    protected CANTalon createMaster(final int deviceNumber) {
        try {
            CANTalon talon = new CANTalon(deviceNumber);
            talon.changeControlMode(TalonControlMode.PercentVbus);

            talon.configNominalOutputVoltage(+0.0f, -0.0f);
            talon.configPeakOutputVoltage(+12.0f, -12.0f);

            // brake mode: true for brake; false for coast
            talon.enableBrakeMode(true);

            talon.setVoltageRampRate(0.0);

            return talon;

        } catch (Exception e) {
            Debug.err(e.getLocalizedMessage());
            throw e;
        }
    }

    protected CANTalon createSlave(final int deviceNumber, final CANTalon masterMotor) {
        CANTalon slaveMotor = new CANTalon(deviceNumber);
        slaveMotor.changeControlMode(TalonControlMode.Follower);
        slaveMotor.set(masterMotor.getDeviceID());
        System.out.printf("Slave motor set to id %d%n", masterMotor.getDeviceID());
        return slaveMotor;
    }

}
