package org.stlpriory.robotics.commands;

import org.stlpriory.robotics.Robot;
import org.stlpriory.robotics.subsystems.BallHolderSubsystem;
import org.stlpriory.robotics.subsystems.BallHolderSubsystem.Direction;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class BallHolderDown extends Command {
    
	private boolean forceable;

    public BallHolderDown(boolean forceable) {
        // Use requires() here to declare subsystem dependencies
        requires(Robot.ballHolder);
        this.forceable = forceable;
    }

    // Called just before this Command runs the first time
    @Override
    protected void initialize() {
    	
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() {
    	if(!isFinished())
        Robot.ballHolder.set(Direction.DOWN, BallHolderSubsystem.ARM_SPEED);
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    protected boolean isFinished() {
    	return Robot.ballHolder.getAngle() < BallHolderSubsystem.MIN_ANGLE && !(Robot.oi.forceButton.get() && forceable);
    }

    // Called once after isFinished returns true
    @Override
    protected void end() {
        Robot.ballHolder.stop();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    @Override
    protected void interrupted() {
        Robot.ballHolder.stop();
    }
}
