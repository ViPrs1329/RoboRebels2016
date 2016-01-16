package org.stlpriory.robotics.subsystems;

import org.stlpriory.robotics.RobotMap;
import org.stlpriory.robotics.commands.drivetrain.DriveWithGamepad;
import org.stlpriory.robotics.utils.Debug;
import org.stlpriory.robotics.utils.Utils;

import edu.wpi.first.wpilibj.Jaguar;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.RobotDrive.MotorType;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Drivetrain extends Subsystem {

    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    Jaguar left_front, right_front, left_rear, right_rear;
    RobotDrive drive;

    public Drivetrain() {
        super("Drivetrain");
        Debug.println("[Drivetrain Subsystem] Instantiating...");

        this.left_front = new Jaguar(RobotMap.LEFT_FRONT_WHEEL_JAGUAR_PWM_CHANNEL);
        this.right_front = new Jaguar(RobotMap.RIGHT_FRONT_WHEEL_JAGUAR_PWM_CHANNEL);
        this.left_rear = new Jaguar(RobotMap.LEFT_REAR_WHEEL_JAGUAR_PWM_CHANNEL);
        this.right_rear = new Jaguar(RobotMap.RIGHT_REAR_WHEEL_JAGUAR_PWM_CHANNEL);
        
        Debug.println("[DriveTrain Subsystem] Initializing left front Jaguar to PWM channel " + RobotMap.LEFT_FRONT_WHEEL_JAGUAR_PWM_CHANNEL);
        Debug.println("[DriveTrain Subsystem] Initializing left rear Jaguar to PWM channel " + RobotMap.LEFT_REAR_WHEEL_JAGUAR_PWM_CHANNEL);
        Debug.println("[DriveTrain Subsystem] Initializing right front Jaguar to PWM channel " + RobotMap.RIGHT_FRONT_WHEEL_JAGUAR_PWM_CHANNEL);
        Debug.println("[DriveTrain Subsystem] Initializing right rear Jaguar to PWM channel " + RobotMap.RIGHT_REAR_WHEEL_JAGUAR_PWM_CHANNEL);

        this.drive = new RobotDrive(this.left_front, this.left_rear, this.right_front, this.right_rear);
        this.drive.setSafetyEnabled(false);
        this.drive.setExpiration(0.1);
        this.drive.setSensitivity(0.5);
        //drive.setMaxOutput(Constants.DRIVE_MAX_OUTPUT);
        this.drive.setInvertedMotor(MotorType.kFrontLeft, true);  // invert the left side motors
        this.drive.setInvertedMotor(MotorType.kRearLeft, true);   // you may need to change or remove this to match your robot

        Debug.println("[DriveTrain Subsystem] Instantiation complete.");
    }

    public void stop() {
        drive.stopMotor();
    }

    /**
     * Drive method for Mecanum wheeled robots.
     */
    public void mecanum_drive(Joystick joystick) {
        //Robot.drivetrain.mecanum_drive(Robot.oi.getGamePad().getRawAxis(1),Robot.oi.getGamePad().getRawAxis(0),Robot.oi.getGamePad().getRawAxis(2));
        /*
         * Three-axis joystick mecanum control.
         * Let x represent strafe left/right
         * Let y represent rev/fwd
         * Let z represent spin CCW/CW axes
         * where each varies from -1 to +1.
         * So:
         * y = -1 corresponds to full speed reverse,
         * y= +1 corresponds to full speed forward,
         * x= -1 corresponds to full speed strafe left,
         * x= +1 corresponds to full speed strafe right,
         * z= -1 corresponds to full speed spin CCW,
         * z= +1 corresponds to full speed spin CW
         *
         * Axis indexes:
         * 1 - LeftX
         * 2 - LeftY
         * 3 - Triggers (Each trigger = 0 to 1, axis value = right - left)
         * 4 - RightX
         * 5 - RightY
         * 6 - DPad Left/Right
         */

        double rawLeftX = joystick.getRawAxis(1);
        double rawLeftY = joystick.getRawAxis(2);
        double rawZ = joystick.getRawAxis(3);

        double scaledLeftX = Utils.scale(rawLeftX);
        double scaledLeftY = Utils.scale(rawLeftY);

        double right = -scaledLeftX;
        double forward = scaledLeftY;
        double rotation = -rawZ;
        double clockwise = rawZ;

        this.drive.mecanumDrive_Cartesian(right, -forward, rotation, clockwise);
    }

    public void mecanum_drive(final double forward, final double right, final double rotation) {
        this.drive.mecanumDrive_Cartesian(right, forward, rotation, 0);
    }

    public void arcade_drive(final double forward, final double rotation) {
        // put arcade drive logic here
    }

    @Override
    public void initDefaultCommand() {
        Debug.println("[DriveTrain.initDefaultCommand()] Setting default command to " + DriveWithGamepad.class.getName());
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
        setDefaultCommand(new DriveWithGamepad());
    }
}
