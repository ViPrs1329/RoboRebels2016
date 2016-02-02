package org.stlpriory.robotics.commands.drivetrain;

import org.stlpriory.robotics.utils.Utils;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Strafe extends Command {

	double goalDistance = 0.0;
	double startTime;
	double distance;
	double totalDistance = 0.0;
	double timeCurrent;
	Timer timer = new Timer();
	boolean right;

	public Strafe(double din, boolean isRight) {
		super("DriveWithGamepad");
		// Use requires() here to declare subsystem dependencies
		// eg. requires(chassis);
		// Variable "din" needs to be in feetIf isRight is true, it will strafe
//		requires(Robot.drivetrain);
		goalDistance = Utils.TALONdistance(din);
		right = isRight;
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		timer.start();
		startTime = timer.get();
		totalDistance = 0.0;
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		if (right) {
//			Robot.drivetrain.mecanum_drive(0, Constants.DEFAULT_STRAFE_SPEED, 0);
			// this number may need to be changed
		} else {
//			Robot.drivetrain.mecanum_drive(0, -1
//					* Constants.DEFAULT_STRAFE_SPEED, 0);
		}
//		SmartDashboard.putNumber("Robot Speed",
//				Robot.drivetrain.getRobotSpeed());
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		timeCurrent = timer.get(); // fix this
//		distance = (Robot.drivetrain.getRobotSpeed() * (timeCurrent - startTime));
		totalDistance = totalDistance + distance;
		if (totalDistance >= goalDistance) {
			return true;
		}
		startTime = timer.get();
		SmartDashboard.putNumber("Distance", totalDistance);
		return false;
	}

	// Called once after isFinished returns true
	protected void end() {
//		Robot.drivetrain.mecanum_drive(0.0, 0., 0.);

	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
	}
}
