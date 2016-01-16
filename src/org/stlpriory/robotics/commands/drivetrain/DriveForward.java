package org.stlpriory.robotics.commands.drivetrain;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.stlpriory.robotics.utils.Constants;
import org.stlpriory.robotics.utils.Utils;
import org.stlpriory.robotics.Robot;

public class DriveForward extends Command {

	double goalDistance = 0.0;
	double startTime;
	double distance;
	double totalDistance = 0.0;
	double timeCurrent;
	Timer timer = new Timer();
	boolean forward;

	public DriveForward(double din, boolean isForward) {
		super("DriveWithGamepad");
		// Use requires() here to declare subsystem dependencies
		// eg. requires(chassis);
		// Variable "din" needs to be in feet
		// If isFoward is true, it will drive forwards, otherwise it will drive
		// in reverse.
//		requires(Robot.drivetrain);
		setTimeout(.1);
		goalDistance = Utils.TALONdistance(din);
		forward = isForward;

	}

	// Called just before this Command runs the first time
	protected void initialize() {
		timer.start();
		startTime = timer.get();
		totalDistance = 0.0;
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		if (forward) {
//			Robot.drivetrain.mecanum_drive(-1 * Constants.DEFAULT_FORWARD_SPEED, 0, 0);
			System.out.println("Driving forward");
		} else {
//			Robot.drivetrain.mecanum_drive(Constants.DEFAULT_FORWARD_SPEED, 0,
//					0);
		}
//		SmartDashboard.putNumber("Robot Speed",
//				Robot.drivetrain.getRobotSpeed());
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		timeCurrent = timer.get(); // fix this
//		distance = (Robot.drivetrain.getRobotSpeed() * (timeCurrent - startTime));
		totalDistance = totalDistance + distance;
		System.out.println( "distance " + totalDistance);
		if (totalDistance >= goalDistance) {
			return true;
		}
		startTime = timer.get();
		SmartDashboard.putNumber("Distance", totalDistance);
		return false;
	}

	// Called once after isFinished returns true
	protected void end() {
//		Robot.drivetrain.mecanum_drive(0.0, 0, 0);
		System.out.println("I finished Driving");

	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
//		Robot.drivetrain.mecanum_drive(0, 0, 0);
	}
}
