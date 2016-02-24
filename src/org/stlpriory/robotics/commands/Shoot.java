package org.stlpriory.robotics.commands;

import java.util.concurrent.TimeUnit;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.stlpriory.robotics.Robot;
import org.stlpriory.robotics.hardware.MiniCIMMotorSpecs;
import org.stlpriory.robotics.subsystems.ShooterSubsystem;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class Shoot extends Command {
	private boolean isDone = false;
    private double targetRightSpeed = ShooterSubsystem.SHOOT_SPEED;
    private double targetLeftSpeed = ShooterSubsystem.SHOOT_SPEED;
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
        double rightSpeed = Robot.shooter.getRightSpeed();
        double leftSpeed  = Robot.shooter.getLeftSpeed();
        double minSpeed   = Math.max(rightSpeed, leftSpeed);

//        Robot.shooter.reset();
        // Get shooter motors up to full speed
        if (minSpeed > 1.5 ) {
            System.out.println("right speed " + rightSpeed + " left speed " +leftSpeed + " min speed "+ minSpeed);
            Robot.shooter.setSpeeds(ShooterSubsystem.SHOOT_SPEED, ShooterSubsystem.SHOOT_SPEED);
            System.out.println("not max");
            targetRightSpeed = ShooterSubsystem.SHOOT_SPEED;
            targetLeftSpeed = ShooterSubsystem.SHOOT_SPEED;
            Robot.oi.vibrate(true);

        } else{
            double diffSpeed  = Math.abs(rightSpeed - leftSpeed);
            SmartDashboard.putNumber("difference",diffSpeed);
            System.out.println("right speed = "+rightSpeed+", leftSpeed = "+leftSpeed+", diff = "+diffSpeed+
                               ", arm extended = "+Robot.shooter.isLoaderArmExtended()+", arm retracted = "+
                               Robot.shooter.isLoaderArmRetracted());
            if (diffSpeed < .2) {
                Robot.shooter.extendLoaderArm();
                System.out.println("extending");
                Robot.oi.vibrate(false);
                pause(1);
                isDone = true;
                
            } else if (leftSpeed > rightSpeed) {

                targetLeftSpeed  = targetLeftSpeed - ShooterSubsystem.DECREASE_VALUE;
                Robot.shooter.setSpeeds(targetRightSpeed, targetLeftSpeed);
                Robot.oi.vibrate(true);
            } else {
                targetRightSpeed = targetRightSpeed - ShooterSubsystem.DECREASE_VALUE;
                Robot.shooter.setSpeeds(targetRightSpeed, targetLeftSpeed);
                Robot.oi.vibrate(true);
            }
        }
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished()
    {
		return isDone;
	}

	// Called once after isFinished returns true
	protected void end() {
		Robot.shooter.stop();
		Robot.shooter.retractLoaderArm();
		Robot.oi.vibrate(false);
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
