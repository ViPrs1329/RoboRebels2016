package org.stlpriory.robotics.subsystems;

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
public class CANDrivetrainSubsystem extends Drivetrain {

    
    public final RobotDrive drive;

    // ==================================================================================
    //                        C O N S T R U C T O R S
    // ==================================================================================

    public CANDrivetrainSubsystem() {
        this("CANDrivetrainSubsystem");
    }

    public CANDrivetrainSubsystem(final String name) {
        Debug.println("[CANDrivetrain Subsystem] Instantiating...");
        Debug.println("   P = " + P_VALUE);
        Debug.println("   I = " + I_VALUE);
        Debug.println("   D = " + D_VALUE);
        Debug.println("   F = " + F_VALUE);
        Debug.println("   I Zone = " + IZONE_VALUE);
        
        SmartDashboard.putNumber("Speed", 0);

        this.leftFront = createMaster(LF_MOTOR_ID, true);
        this.leftRear  = createSlave(LR_MOTOR_ID, this.leftFront);

        this.rightFront = createMaster(RF_MOTOR_ID, false);
        this.rightRear  = createMaster(RR_MOTOR_ID, false);
//        this.rightRear  = createSlave(RR_MOTOR_ID, this.rightFront);

        this.drive = new RobotDrive(this.leftFront, this.rightFront);
        this.drive.setSafetyEnabled(false);
        this.drive.setExpiration(0.1);
        this.drive.setSensitivity(0.5);

        // invert the left side motors
        this.drive.setInvertedMotor(MotorType.kFrontLeft, true);
        this.drive.setInvertedMotor(MotorType.kRearLeft, true);

        Debug.println("[CANDrivetrain Subsystem] Instantiation complete.");
    }

    // ==================================================================================
    //                      P U B L I C   M E T H O D S
    // ==================================================================================

      
    public double getSpeedInRPM() {
        // Since the 2 left motors and 2 right motors are paired in a 
        // master/slave arrangement we only need to check the master
        double leftSide  = this.leftFront.getSpeed();
        double rightSide = this.rightFront.getSpeed();
        
        return (leftSide + rightSide) / 2.0;
    }

	@Override
	public void tankDrive(double left, double right) {
		// TODO Auto-generated method stub
		
	}
    
}
