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
public class DrivetrainSubsystem extends Drivetrain {
    
    public static final boolean MASTER_SLAVE_MODE = true;

    public final RobotDrive drive;

    // ==================================================================================
    //                        C O N S T R U C T O R S
    // ==================================================================================

    public DrivetrainSubsystem() {
        this("DrivetrainSubsystem");
    }

    public DrivetrainSubsystem(final String name) {
        Debug.println("[Drivetrain Subsystem] Instantiating...");

        this.leftFront = createMaster(LF_MOTOR_ID, false);
        if (MASTER_SLAVE_MODE) {
            this.leftRear  = createSlave(LR_MOTOR_ID, this.leftFront);        	
        } else {
            this.leftRear  = createMaster(LR_MOTOR_ID, false);
        }

        this.rightFront = createMaster(RF_MOTOR_ID, false);
        if (MASTER_SLAVE_MODE) {
            this.rightRear  = createSlave(RR_MOTOR_ID, this.rightFront);        	
        } else {
            this.rightRear  = createMaster(RR_MOTOR_ID, false);
        }

        if (Robot.robotType == RobotType.TANKBOT) {
            this.drive = new RobotDrive(this.rightFront, this.leftFront);
        } else {
            this.drive = new RobotDrive(this.leftFront, this.rightFront);
        }
        this.drive.setSafetyEnabled(false);
        this.drive.setExpiration(0.1);
        this.drive.setSensitivity(0.5);

        // Invert the left side motors
        // Note that, since we only give two motors in the constructor, it assumes that they are
        // the rear ones, so we only invert them.  
        this.drive.setInvertedMotor(MotorType.kRearRight, true);
        this.drive.setInvertedMotor(MotorType.kRearLeft, true);

        Debug.println("[DriveTrain Subsystem] Instantiation complete.");
    }

    // ==================================================================================
    //                        C O N S T R U C T O R S
    // ==================================================================================

    public void tankDrive(final double leftStickValue, final double rightStickValue) {
        this.drive.tankDrive(leftStickValue, rightStickValue);
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

     
}
