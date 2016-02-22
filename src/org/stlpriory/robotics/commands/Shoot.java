package org.stlpriory.robotics.commands;

import java.util.concurrent.TimeUnit;

import org.stlpriory.robotics.Robot;
import org.stlpriory.robotics.subsystems.ShooterSubsystem;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class Shoot extends Command {
	private boolean isDone;
	private int times = 1;

	public Shoot() {
		requires(Robot.shooter);
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		isDone = false;
		System.out.printf("Started%n");
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
        // Get shooter motors up to full speed
        if (Robot.shooter.getSpeed() < ShooterSubsystem.MIN_SHOOTING_SPEED) {
            Robot.shooter.setSpeeds(ShooterSubsystem.SHOOT_SPEED, ShooterSubsystem.SHOOT_SPEED);

        } else {
            double rightSpeed = Robot.shooter.getRightSpeed();
            double leftSpeed  = Robot.shooter.getLeftSpeed();
            System.out.println("right speed = "+rightSpeed+", leftSpeed = "+leftSpeed);
            
            if (leftSpeed > rightSpeed) {
                rightSpeed = ShooterSubsystem.SHOOT_SPEED;
                leftSpeed  = leftSpeed - ShooterSubsystem.DECREASE_VALUE;
                Robot.shooter.setSpeeds(rightSpeed, leftSpeed);
                
            } else if (rightSpeed > leftSpeed) {
                rightSpeed = rightSpeed - ShooterSubsystem.DECREASE_VALUE;
                leftSpeed  = ShooterSubsystem.SHOOT_SPEED;
                Robot.shooter.setSpeeds(rightSpeed, leftSpeed);
                
            } else {
                Robot.shooter.extendLoaderArm();
                pause(2);
                isDone = true;
            }
        }
	    
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
	
    private void pause(final double seconds) {
        long t0 = System.nanoTime();
        long elapsedTime = TimeUnit.NANOSECONDS.toSeconds(System.nanoTime() - t0);
        
        while (elapsedTime < seconds) {
            elapsedTime = TimeUnit.NANOSECONDS.toSeconds(System.nanoTime() - t0);
        }
    }

}
