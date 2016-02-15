package org.stlpriory.robotics.subsystems;

import org.stlpriory.robotics.OI;
import org.stlpriory.robotics.Robot;
import org.stlpriory.robotics.Robot.RobotType;
import org.stlpriory.robotics.commands.drivetrain.DriveWithGamepad;
import org.stlpriory.robotics.hardware.AMOpticalEncoderSpecs;
import org.stlpriory.robotics.hardware.CIMMotorSpecs;
import org.stlpriory.robotics.utils.Debug;
import org.stlpriory.robotics.utils.Utils;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.CANTalon.TalonControlMode;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.RobotDrive.MotorType;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Robot drive train subsystem consisting of 4 CIM motors configured in 2 master/slave arrangements. 
 * The motors are controlled by Talon SRX speed controllers wired for PWM control.
 */
public class DrivetrainSubsystem extends Subsystem {

    public static final int LF_MOTOR_ID = 4;
    public static final int LR_MOTOR_ID = 3;
    public static final int RF_MOTOR_ID = 2;
    public static final int RR_MOTOR_ID = 1;

    public static final double P_VALUE = 0.5;
    public static final double I_VALUE = 0.02;
    public static final double D_VALUE = 0;
    public static final double F_VALUE = 0.5;
    public static final int IZONE_VALUE = (int) (0.2 * AMOpticalEncoderSpecs.PULSES_PER_REV);
    
    public static final boolean MASTER_SLAVE_MODE = false;

    private final CANTalon rightFront;
    private final CANTalon rightRear;
    private final CANTalon leftFront;
    private final CANTalon leftRear;

    public final RobotDrive drive;

    // ==================================================================================
    //                        C O N S T R U C T O R S
    // ==================================================================================

    public DrivetrainSubsystem() {
        this("DrivetrainSubsystem");
    }

    public DrivetrainSubsystem(final String name) {
        super(name);
        Debug.println("[Drivetrain Subsystem] Instantiating...");

        this.leftFront = createMaster(LF_MOTOR_ID);
        if (MASTER_SLAVE_MODE) {
            this.leftRear  = createSlave(LR_MOTOR_ID, this.leftFront);        	
        } else {
            this.leftRear  = createMaster(LR_MOTOR_ID);
        }

        this.rightFront = createMaster(RF_MOTOR_ID);
        if (MASTER_SLAVE_MODE) {
            this.rightRear  = createSlave(RR_MOTOR_ID, this.rightFront);        	
        } else {
            this.rightRear  = createMaster(RR_MOTOR_ID);
        }

        if (Robot.robotType == RobotType.TANKBOT) {
            this.drive = new RobotDrive(this.rightFront, this.rightRear, this.leftFront, this.leftRear);
        } else {
            this.drive = new RobotDrive(this.leftFront, this.leftRear, this.rightFront, this.rightRear);
        }
        this.drive.setSafetyEnabled(false);
        this.drive.setExpiration(0.1);
        this.drive.setSensitivity(0.5);

        // Invert the left side motors
        this.drive.setInvertedMotor(MotorType.kFrontLeft, true);
        this.drive.setInvertedMotor(MotorType.kRearLeft, true);
        this.drive.setInvertedMotor(MotorType.kFrontRight, true);
        this.drive.setInvertedMotor(MotorType.kRearRight, true);

        Debug.println("[DriveTrain Subsystem] Instantiation complete.");
    }

    // ==================================================================================
    //                        C O N S T R U C T O R S
    // ==================================================================================

    public void tankDrive(final double leftStickValue, final double rightStickValue) {
        this.drive.tankDrive(leftStickValue, rightStickValue);
    }

    public void tankDrive(final Joystick joystick) {
        double leftStickValue  = Utils.scale( joystick.getRawAxis(OI.LEFT_STICK_Y_AXIS) );
        double rightStickValue = Utils.scale( joystick.getRawAxis(OI.RIGHT_STICK_Y_AXIS) );
        tankDrive(leftStickValue, rightStickValue);
    }

    public void stop() {
        this.drive.stopMotor();
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

    public void updateStatus() {
        double leftFrontMotorOutput  = this.leftFront.getOutputVoltage() / this.leftFront.getBusVoltage();
        double leftRearMotorOutput   = this.leftRear.getOutputVoltage() / this.leftRear.getBusVoltage();
        double rightFrontMotorOutput = this.rightFront.getOutputVoltage() / this.rightFront.getBusVoltage();
        double rightRearMotorOutput  = this.rightRear.getOutputVoltage() / this.rightRear.getBusVoltage();

        SmartDashboard.putString("Control Mode", "PercentVbus");
        SmartDashboard.putNumber("LF motor output", leftFrontMotorOutput);
        SmartDashboard.putNumber("LR motor output", leftRearMotorOutput);
        SmartDashboard.putNumber("RF motor output", rightFrontMotorOutput);
        SmartDashboard.putNumber("RR motor output", rightRearMotorOutput);
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

            // Voltage ramp rate in volts/sec (works regardless of mode)
            // (e.g. setVoltageRampRate(6.0) results in 0V to 6V in one sec)
            talon.setVoltageRampRate(12.0);

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
        return slaveMotor;
    }

}
