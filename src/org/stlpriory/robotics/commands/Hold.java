package org.stlpriory.robotics.commands;

import org.stlpriory.robotics.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class Hold extends Command {
    public Hold()
    {
        requires(Robot.shooter);
    }
    @Override
    protected void initialize() {
    }

    @Override
    protected void execute() {
        Robot.shooter.keep();
    }

    @Override
    protected boolean isFinished() {
        return false;
    }

    @Override
    protected void end() {
        Robot.shooter.stop();
    }

    @Override
    protected void interrupted() {
        Robot.shooter.stop();
    }

}
