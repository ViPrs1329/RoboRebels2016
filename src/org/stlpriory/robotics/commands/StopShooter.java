package org.stlpriory.robotics.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.stlpriory.robotics.Robot;

public class StopShooter extends Command {

    public StopShooter() {
        requires(Robot.shooter);
    }

    @Override
    protected void initialize() {
        // TODO Auto-generated method stub

    }

    @Override
    protected void execute() {
        // TODO Auto-generated method stub
        Robot.shooter.stop();
    }

    @Override
    protected boolean isFinished() {
        // TODO Auto-generated method stub
        return true;
    }

    @Override
    protected void end() {
        // TODO Auto-generated method stub

    }

    @Override
    protected void interrupted() {
        // TODO Auto-generated method stub

    }

}
