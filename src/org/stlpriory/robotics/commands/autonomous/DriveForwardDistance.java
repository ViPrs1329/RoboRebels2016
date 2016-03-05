package org.stlpriory.robotics.commands.autonomous;

import org.stlpriory.robotics.Robot;
import org.stlpriory.robotics.subsystems.DrivetrainSubsystem.Direction;
import org.stlpriory.robotics.subsystems.DrivetrainSubsystem;
import org.stlpriory.robotics.utils.Utils;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

class DriveForwardDistance extends Command {

	double goalDistance = 0.0;
	double lastTime;
	double distance;
	double totalDistance = 0.0;
	double timeCurrent;
	Timer timer = new Timer();
	Direction direction;

	public DriveForwardDistance(double din, Direction direction) {
		super("DriveWithGamepad");
		// Use requires() here to declare subsystem dependencies
		// eg. requires(chassis);
		// Variable "din" needs to be in feet
		// If isFoward is true, it will drive forwards, otherwise it will drive
		// in reverse.
		requires(Robot.drivetrain);
		goalDistance = Utils.TALONdistance(din);
		this.direction = direction;
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		timer.start();
		lastTime = timer.get();
		totalDistance = 0.0;
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		if (direction == Direction.FORWARD) {
			Robot.drivetrain.tankDrive(DrivetrainSubsystem.FORWARD_SPEED,
					DrivetrainSubsystem.FORWARD_SPEED);
			System.out.println("Driving forward");
		} else {
			Robot.drivetrain.tankDrive(-DrivetrainSubsystem.FORWARD_SPEED,
					-DrivetrainSubsystem.FORWARD_SPEED);
		}
		SmartDashboard.putNumber("Robot Speed", Robot.drivetrain.getSpeed());
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		timeCurrent = timer.get(); // fix this
		distance = (Robot.drivetrain.getSpeed() * (timeCurrent - lastTime));
		totalDistance = totalDistance + distance;
		System.out.println("distance " + totalDistance);
		if (totalDistance >= goalDistance) {
			return true;
		}
		lastTime = timer.get();
		SmartDashboard.putNumber("Distance", totalDistance);
		return false;
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
