package org.stlpriory.robotics.commands.drivetrain;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.stlpriory.robotics.Robot;
import org.stlpriory.robotics.utils.Constants;

//We may need to make a class in Utils to convert inAngle to whatever units the robot uses for rotation
public class Rotate extends Command {

	double goalAngle = 0.0;
	double startTime;
	double angle;
	double speed;
	double totalAngle = 0.0;
	double timeCurrent;
	Timer timer = new Timer();
	boolean direction;

	public Rotate(double inAngle, double speed, boolean direction) {
		super("DriveWithGamepad");
		// if direction is true, turns left
		//els turns right
		requires(Robot.drivetrain);
		this.speed = speed;
		this.direction = direction;
//		if (direction)
//			speed *= (-1.0);
		setTimeout(1.5);
		goalAngle = inAngle;
	}

	public Rotate(double inAngle, double speed) {
		this(inAngle, speed, false);
	}

	public Rotate(double inAngle) {
		this(inAngle, Constants.DEFAULT_ROTATION_SPEED, false);
	}

	public Rotate(double inAngle, boolean direction) {
		this(inAngle, Constants.DEFAULT_ROTATION_SPEED, direction);
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		timer.start();
		startTime = timer.get();
		totalAngle = 0.;
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		if (direction) {
			Robot.drivetrain.tankDrive(speed,0);
		}
		else {
			Robot.drivetrain.tankDrive(0,speed);
		}
		SmartDashboard.putNumber("Robot Speed",
				Robot.drivetrain.getRobotSpeed());
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		timeCurrent = timer.get();
		angle = (Robot.drivetrain.getRobotSpeed() * (timeCurrent - startTime));
		totalAngle = totalAngle + angle;
		if (totalAngle >= goalAngle) {
			return true;
		}
		startTime = timer.get();
		SmartDashboard.putNumber("Angle", totalAngle);
		return false;
	}

	// Called once after isFinished returns true
	protected void end() {
//		Robot.drivetrain.mecanum_drive(0.0, 0, 0);

	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
//		Robot.drivetrain.mecanum_drive(0.0, 0, 0);

	}
}
