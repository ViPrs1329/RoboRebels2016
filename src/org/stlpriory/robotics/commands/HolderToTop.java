package org.stlpriory.robotics.commands;

import org.stlpriory.robotics.Robot;
import org.stlpriory.robotics.subsystems.BallHolderSubsystem;
import org.stlpriory.robotics.subsystems.BallHolderSubsystem.Direction;

import edu.wpi.first.wpilibj.command.Command;

public class HolderToTop extends Command {
    public static final double MAX_ANGLE = BallHolderSubsystem.POTENTIOMETER_SCALE_FACTOR;
    public static final double MIN_ANGLE = BallHolderSubsystem.POTENTIOMETER_OFFSET;
    public static final double TOLERANCE = (1.0 / 15.0) * BallHolderSubsystem.POTENTIOMETER_SCALE_FACTOR;

    public HolderToTop() {
        requires(Robot.ballHolder);
    }

    @Override
    protected void initialize() {
        // TODO Auto-generated method stub

    }

    @Override
    protected void execute() {
        // TODO Auto-generated method stub
        Robot.ballHolder.set(Direction.UP, 1);
    }

    @Override
    protected boolean isFinished() {
        // TODO Auto-generated method stub
        if (Robot.ballHolder.getAngle() > Math.abs((MAX_ANGLE - TOLERANCE))) {
            return true;
        } else {
            return false;
        }

    }

    @Override
    protected void end() {
        // TODO Auto-generated method stub
        Robot.ballHolder.set(0);
    }

    @Override
    protected void interrupted() {
        // TODO Auto-generated method stub
        Robot.ballHolder.set(0);

    }

}
