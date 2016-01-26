package org.stlpriory.robotics.commands;

import edu.wpi.first.wpilibj.command.Command;

import org.stlpriory.robotics.Robot;

/**
 *
 */
public class BallHolderDown extends Command {

    public BallHolderDown() {
        // Use requires() here to declare subsystem dependencies
        requires(Robot.ballHolder);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        Robot.ballHolder.set(Robot.ballholder.Direction.DOWN, Constants.BALL_HOLDER_DOWN_SPEED);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
        Robot.ballHolder.stop();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
        Robot.ballHolder.stop();
    }
}
