package org.stlpriory.robotics.subsystems;

import org.stlpriory.robotics.OI;
import org.stlpriory.robotics.hardware.AMOpticalEncoderSpecs;
import org.stlpriory.robotics.hardware.CIMMotorSpecs;
import org.stlpriory.robotics.utils.Debug;
import org.stlpriory.robotics.utils.Utils;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.CANTalon.FeedbackDevice;
import edu.wpi.first.wpilibj.CANTalon.TalonControlMode;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public abstract class Drivetrain extends Subsystem {
	
	public static final double P_VALUE = 0.5;
    public static final double I_VALUE = 0.02;
    public static final double D_VALUE = 0;
    public static final double F_VALUE = 0.5;
    public static final int IZONE_VALUE = (int) (0.2 * AMOpticalEncoderSpecs.PULSES_PER_REV);
    public static final double CAN_RAMP_RATE = 2;
    public static final double VOLTAGE_RAMP_RATE = 12;
    
    public static final int LF_MOTOR_ID = 3;
    public static final int LR_MOTOR_ID = 4;
    public static final int RF_MOTOR_ID = 2;
    public static final int RR_MOTOR_ID = 1;

    protected CANTalon rightFront;
    protected CANTalon rightRear;
    protected CANTalon leftFront;
    protected CANTalon leftRear;
	protected RobotDrive drive;
    
    public Drivetrain(final String name)
    {
    	super(name);
    }
    
    public abstract void tankDrive(double left, double right);
    
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
      //  setDefaultCommand(new DriveWithGamepad());
    }
    public abstract double getSpeedInRPM();
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
    

    // ==================================================================================
    //                    P R O T E C T E D   M E T H O D S
    // ==================================================================================

    protected CANTalon createMaster(final int deviceNumber, boolean isCAN) {
        try {
            CANTalon talon = new CANTalon(deviceNumber);
            
            if(isCAN){
	            talon.changeControlMode(TalonControlMode.Speed);
	            talon.setFeedbackDevice(FeedbackDevice.QuadEncoder);
	            int allowableClosedLoopErr = (int) (0.1 * AMOpticalEncoderSpecs.PULSES_PER_REV);
	            talon.setAllowableClosedLoopErr(allowableClosedLoopErr);

	            talon.setProfile(0);
	            talon.setP(P_VALUE);
	            talon.setI(I_VALUE);
	            talon.setD(D_VALUE);
	            talon.setF(F_VALUE);
	            talon.setIZone(IZONE_VALUE);

	            talon.setCloseLoopRampRate(CAN_RAMP_RATE);
	                        
	            talon.reverseSensor(false);
	            talon.configEncoderCodesPerRev(AMOpticalEncoderSpecs.CYCLES_PER_REV);



            } else {
            	talon.changeControlMode(TalonControlMode.PercentVbus); 
            	talon.setVoltageRampRate(VOLTAGE_RAMP_RATE);
            }
            talon.configNominalOutputVoltage(+0.0f, -0.0f);
            talon.configPeakOutputVoltage(+12.0f, -12.0f);

            
            // keep the motor and sensor in phase
            

            // Soft limits can be used to disable motor drive when the sensor position
            // is outside of the limits
            talon.setForwardSoftLimit(CIMMotorSpecs.MAX_SPEED_RPM);
            talon.enableForwardSoftLimit(false);
            talon.setReverseSoftLimit(-CIMMotorSpecs.MAX_SPEED_RPM);
            talon.enableReverseSoftLimit(false);

            // brake mode: true for brake; false for coast
            talon.enableBrakeMode(true);

            // Voltage ramp rate in volts/sec (works regardless of mode)
            // 0V to 6V in one sec
//            talon.setVoltageRampRate(6.0);

            // The allowable close-loop error whereby the motor output is neutral regardless
            // of the calculated result. When the closed-loop error is within the allowable
            // error the PID terms are zeroed (F term remains in effect) and the integral
            // accumulator is cleared. Value is in the same units as the closed loop error.
            // Initially make the allowable error 10% of a revolution
            

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
