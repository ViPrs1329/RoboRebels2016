package org.stlpriory.robotics.commands;

import org.stlpriory.robotics.Robot;
import org.stlpriory.robotics.subsystems.BallHolderSubsystem.Direction;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class BallHolderDown extends Command {
    public static final double BALL_HOLDER_DOWN_SPEED = 1;

    public BallHolderDown() {
        // Use requires() here to declare subsystem dependencies
        requires(Robot.ballHolder);
    }

    // Called just before this Command runs the first time
    @Override
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() {
        Robot.ballHolder.set(Direction.DOWN, BALL_HOLDER_DOWN_SPEED);
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    protected boolean isFinished() {
        return false;
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
