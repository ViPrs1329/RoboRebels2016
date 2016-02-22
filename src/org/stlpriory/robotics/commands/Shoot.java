package org.stlpriory.robotics.commands;

import org.stlpriory.robotics.Robot;
import org.stlpriory.robotics.subsystems.ShooterSubsystem;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class Shoot extends Command {
	private boolean isDone;
	double times = 1;
	public Shoot() {
		// Use requires() here to declare subsystem dependencies
		// eg. requires(chassis);
		requires(Robot.shooter);

	}

	// Called just before this Command runs the first time
	protected void initialize() {
		isDone = false;
		System.out.printf("Started%n");
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		if (Robot.shooter.getSpeed() < ShooterSubsystem.MIN_SHOOTING_SPEED) {
			Robot.shooter.shoot();
			return;
		}
		if (Math.abs((Robot.shooter.getRightSpeed() - Robot.shooter.getLeftSpeed())) <= ShooterSubsystem.MAX_DIFFERENCE) {
			Robot.shooter.extendLoaderArm();
			isDone = true;
			System.out.println("Extending arm");
		} 
//		else if (Robot.shooter.getLeftSpeed() > Robot.shooter.getRightSpeed()) {
//			Robot.shooter.setSpeeds(ShooterSubsystem.SHOOT_SPEED, ShooterSubsystem.SHOOT_SPEED
//							- (ShooterSubsystem.DECREASE_VALUE * times));
//		} 
//		else if (Robot.shooter.getRightSpeed() > Robot.shooter.getLeftSpeed()) {
//			Robot.shooter.setSpeeds(ShooterSubsystem.SHOOT_SPEED - (ShooterSubsystem.DECREASE_VALUE * times),
//					ShooterSubsystem.SHOOT_SPEED);
//		}
		times++;
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return isDone;
	}

	// Called once after isFinished returns true
	protected void end() {
		Robot.shooter.stop();
		Robot.shooter.retractLoaderArm();
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
		this.end();
	}
}
