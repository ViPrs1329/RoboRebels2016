package org.stlpriory.robotics.subsystems;

import org.stlpriory.robotics.Robot;
import org.stlpriory.robotics.Robot.RobotType;
import org.stlpriory.robotics.hardware.AMOpticalEncoderSpecs;
import org.stlpriory.robotics.hardware.CIMMotorSpecs;
import org.stlpriory.robotics.utils.Debug;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.CANTalon.FeedbackDevice;
import edu.wpi.first.wpilibj.CANTalon.TalonControlMode;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.RobotDrive.MotorType;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Robot drive train subsystem consisting of 4 CIM motors configured in 2 master/slave arrangements. 
 * The motors are controlled by Talon SRX speed controllers through a CAN bus and encoder feedback.
 */
public class CANDrivetrainSubsystem extends Subsystem {

    public static double P_VALUE = 0.5;
    public static double I_VALUE = 0.02;
    public static double D_VALUE = 0;
    public static double F_VALUE = 0.5;
    public static int IZONE_VALUE = (int) (0.2 * AMOpticalEncoderSpecs.PULSES_PER_REV);
    
    public static double RAMP_RATE = 2;

//    // For a Talon SRX these are the closed-loop outputs which, if exceeded, 
//    // the motor output is capped
//    public static final double TALON_SRX_POS_PEAK_OUTPUT = +1023.0d;
//    public static final double TALON_SRX_NEG_PEAK_OUTPUT = -1023.0d;
    
    private final CANTalon rightFront;
    private final CANTalon rightRear;
    private final CANTalon leftFront;
    private final CANTalon leftRear;

    public final RobotDrive drive;

    // ==================================================================================
    //                        C O N S T R U C T O R S
    // ==================================================================================

    public CANDrivetrainSubsystem() {
        this("CANDrivetrainSubsystem");
    }

    public CANDrivetrainSubsystem(final String name) {
        super(name);
        Debug.println("[CANDrivetrain Subsystem] Instantiating...");
        Debug.println("   P = " + P_VALUE);
        Debug.println("   I = " + I_VALUE);
        Debug.println("   D = " + D_VALUE);
        Debug.println("   F = " + F_VALUE);
        Debug.println("   I Zone = " + IZONE_VALUE);

        this.leftFront  = createMaster(DrivetrainSubsystem.LF_MOTOR_ID);
        this.rightFront = createMaster(DrivetrainSubsystem.RF_MOTOR_ID);
        if (MASTER_SLAVE_MODE) {
            this.leftRear  = createSlave(DrivetrainSubsystem.LR_MOTOR_ID, this.leftFront);          
            this.rightRear = createSlave(DrivetrainSubsystem.RR_MOTOR_ID, this.rightFront);            
        } else {
            this.leftRear  = createMaster(DrivetrainSubsystem.LR_MOTOR_ID);
            this.rightRear = createMaster(DrivetrainSubsystem.RR_MOTOR_ID);
        }

        if (Robot.robotType == RobotType.TANKBOT) {
            this.drive = new RobotDrive(this.rightFront, this.leftFront);
        } else {
            this.drive = new RobotDrive(this.leftFront, this.rightFront);
        }

        this.drive.setSafetyEnabled(false);
        this.drive.setExpiration(0.1);
        this.drive.setSensitivity(0.5);

        // invert the left side motors
        this.drive.setInvertedMotor(MotorType.kRearLeft, true);
        
        // Set the scaling factor used by RobotDrive when motor controllers are in
        // a mode other than PercentVbus. The scaling factor is multiplied with the 
        // output percentage [-1,1] computed by the drive functions. 
        this.drive.setMaxOutput(CIMMotorSpecs.MAX_SPEED_RPM);

        Debug.println("[CANDrivetrain Subsystem] Instantiation complete.");
    }

    // ==================================================================================
    //                      P U B L I C   M E T H O D S
    // ==================================================================================

    @Override
    public void updateStatus() {
        double leftFrontMotorOutput  = this.leftFront.getOutputVoltage() / this.leftFront.getBusVoltage();
        double leftRearMotorOutput   = this.leftRear.getOutputVoltage() / this.leftRear.getBusVoltage();
        double rightFrontMotorOutput = this.rightFront.getOutputVoltage() / this.rightFront.getBusVoltage();
        double rightRearMotorOutput  = this.rightRear.getOutputVoltage() / this.rightRear.getBusVoltage();

        SmartDashboard.putString("Control Mode", "Speed");
        SmartDashboard.putNumber("LF motor output", leftFrontMotorOutput);
        SmartDashboard.putNumber("LR motor output", leftRearMotorOutput);
        SmartDashboard.putNumber("RF motor output", rightFrontMotorOutput);
        SmartDashboard.putNumber("RR motor output", rightRearMotorOutput);

        SmartDashboard.putNumber("left speed", this.leftFront.getSpeed());
        SmartDashboard.putNumber("right speed", this.rightFront.getSpeed());
    }
    
    public double getSpeedInRPM() {
        // Since the 2 left motors and 2 right motors are paired in a 
        // master/slave arrangement we only need to check the master
        double leftSide  = this.leftFront.getSpeed();
        double rightSide = this.rightFront.getSpeed();
        
        return (leftSide + rightSide) / 2.0;
    }
    
    // ==================================================================================
    //                    P R O T E C T E D   M E T H O D S
    // ==================================================================================

    @Override
    static protected CANTalon createMaster(final int deviceNumber) {
        try {
            CANTalon talon = new CANTalon(deviceNumber);
            talon.changeControlMode(TalonControlMode.Speed);
            talon.setFeedbackDevice(FeedbackDevice.QuadEncoder);

            talon.configNominalOutputVoltage(+0.0f, -0.0f);
            talon.configPeakOutputVoltage(+12.0f, -12.0f);

            talon.configEncoderCodesPerRev(AMOpticalEncoderSpecs.CYCLES_PER_REV);

            // keep the motor and sensor in phase
            talon.reverseSensor(false);

            // Soft limits can be used to disable motor drive when the sensor position
            // is outside of the limits
            talon.setForwardSoftLimit(CIMMotorSpecs.MAX_SPEED_RPM);
            talon.enableForwardSoftLimit(false);
            talon.setReverseSoftLimit(-CIMMotorSpecs.MAX_SPEED_RPM);
            talon.enableReverseSoftLimit(false);

            // brake mode: true for brake; false for coast
            talon.enableBrakeMode(true);

            // Voltage ramp rate, in volts/sec, which limits the rate at which the 
            // throttle will change. Affects all  modes.
            // For example, 0V to 6V in one sec would be a value of 6.0
//            talon.setVoltageRampRate(RAMP_RATE);

            // The allowable close-loop error whereby the motor output is neutral regardless
            // of the calculated result. When the closed-loop error is within the allowable
            // error the PID terms are zeroed (F term remains in effect) and the integral
            // accumulator is cleared. Value is in the same units as the closed loop error.
            // Initially make the allowable error 10% of a revolution
            int allowableClosedLoopErr = (int) (0.1 * AMOpticalEncoderSpecs.PULSES_PER_REV);
            talon.setAllowableClosedLoopErr(allowableClosedLoopErr);
//            talon.setAllowableClosedLoopErr(2000); 

            talon.setProfile(0);
            talon.setP(P_VALUE);
            talon.setI(I_VALUE);
            talon.setD(D_VALUE);
            talon.setF(F_VALUE);
            talon.setIZone(IZONE_VALUE);

            // Closed loop ramp rate, in volts/sec, which limits the rate at which the 
            // throttle will change. Only affects position and speed closed loop modes.
            // For example, 0V to 6V in one sec would be a value of 6.0
            talon.setCloseLoopRampRate(RAMP_RATE);
            return talon;

        } catch (Exception e) {
            Debug.err(e.getLocalizedMessage());
            throw e;
        }
    }

    static protected CANTalon createSlave(final int deviceNumber, final CANTalon masterMotor) {
        CANTalon slaveMotor = new CANTalon(deviceNumber);
        slaveMotor.changeControlMode(TalonControlMode.Follower);
        slaveMotor.set(masterMotor.getDeviceID());
        System.out.printf("Slave motor set to id %d%n", masterMotor.getDeviceID());
        return slaveMotor;
    }



    public void tankDrive(final double leftStickValue, final double rightStickValue)
    {
        drive.tankDrive(leftStickValue, rightStickValue);
    }

    public void tankDrive(final Joystick joystick) {
        double leftStickValue  = Utils.scale(joystick.getRawAxis(OI.LEFT_STICK_Y_AXIS) );
        double rightStickValue = Utils.scale(joystick.getRawAxis(OI.RIGHT_STICK_Y_AXIS) );
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

        SmartDashboard.putString("Control Mode", rightFront.getControlMode().toString());
        SmartDashboard.putNumber("LF motor output", leftFrontMotorOutput);
        SmartDashboard.putNumber("LR motor output", leftRearMotorOutput);
        SmartDashboard.putNumber("RF motor output", rightFrontMotorOutput);
        SmartDashboard.putNumber("RR motor output", rightRearMotorOutput);

        SmartDashboard.putNumber("LF encoder output", leftFront.get(););
        SmartDashboard.putNumber("LR encoder output", leftRear.get());
        SmartDashboard.putNumber("RF encoder output", rightFront.get());
        SmartDashboard.putNumber("RR encoder output", rightRear.get());
    }
}
