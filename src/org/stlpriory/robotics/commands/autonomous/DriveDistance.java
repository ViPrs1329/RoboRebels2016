package org.stlpriory.robotics.commands.autonomous;

import org.stlpriory.robotics.Robot;
import org.stlpriory.robotics.subsystems.DrivetrainSubsystem;
import org.stlpriory.robotics.subsystems.DrivetrainSubsystem.Direction;
import org.stlpriory.robotics.utils.Utils;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

class DriveDistance extends Command {

	double goalDistance = 0.0;
    double startPosition;
	double distance;
	Direction direction;
	double startHeading;
	boolean shouldCorrect;
	double firstGyroReading;

	public DriveDistance(double din, Direction direction, double gyroReading, boolean shouldCorrect) {
		super("DriveWithGamepad");
		// Use requires() here to declare subsystem dependencies
		// eg. requires(chassis);
		// Variable "din" needs to be in feet
		// If isFoward is true, it will drive forwards, otherwise it will drive
		// in reverse.
		requires(Robot.drivetrain);
		goalDistance = Utils.TALONdistance(din);
		this.direction = direction;
		this.startHeading = Robot.drivetrain.getAngle();
		this.shouldCorrect = shouldCorrect;
	}

	// Called just before this Command runs the first time
	protected void initialize() {
            startPosition = Robot.drivetrain.getPosition();
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		if(shouldCorrect)
		{
			if(Math.abs(Robot.drivetrain.getAngle()-this.firstGyroReading)<=DrivetrainSubsystem.GYRO_TOLERANCE)
			{
				if(Robot.drivetrain.getAngle()>this.firstGyroReading)
				{
					Robot.drivetrain.tankDrive(DrivetrainSubsystem.AUTO_TURN_SPEED,-DrivetrainSubsystem.AUTO_TURN_SPEED);
					return;
				}
				else if(Robot.drivetrain.getAngle()<this.firstGyroReading)
				{
					Robot.drivetrain.tankDrive(-DrivetrainSubsystem.AUTO_TURN_SPEED,DrivetrainSubsystem.AUTO_TURN_SPEED);
					return;
				}
			}
			else
			{
				shouldCorrect = false;
			}
		}
		if (direction == Direction.FORWARD) {
			Robot.drivetrain.driveForward(-DrivetrainSubsystem.FORWARD_SPEED,
					startHeading);
			System.out.println("Driving forward");
		} else {
			Robot.drivetrain.driveForward(DrivetrainSubsystem.FORWARD_SPEED,
					startHeading);
			System.out.println("Driving backward");
		}
//		SmartDashboard.putNumber("Robot Speed", Robot.drivetrain.getSpeed());
	}

	// Make this return true when this Command no longer needs to run execute()
        protected boolean isFinished() {
            double totalDistance = Math.abs(Robot.drivetrain.getPosition() - startPosition);
            System.out.println("I've gone " + totalDistance + " encoder units");
            SmartDashboard.putNumber("Distance", totalDistance);
            return totalDistance >= goalDistance;
        }

	// Called once after isFinished returns true
	protected void end() {
		Robot.drivetrain.stop();
		System.out.println("I finished Driving");

	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
		Robot.drivetrain.stop();
	}
}
