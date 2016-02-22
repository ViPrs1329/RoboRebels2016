package org.stlpriory.robotics.commands;

import org.stlpriory.robotics.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class Throw extends Command {

    public Throw() {
        requires(Robot.shooter);
    }

    @Override
    protected void initialize() {
    }

    @Override
    protected void execute() {
        Robot.shooter.shoot();
        // if(they're going fast enough)
        Robot.shooter.extendLoaderArm();
    }

    @Override
    protected boolean isFinished() {
        return false;
    }

    @Override
    protected void end() {
    	Robot.shooter.retractLoaderArm();
        Robot.shooter.stop();
    }

    @Override
    protected void interrupted() {
        Robot.shooter.stop();
        Robot.shooter.retractLoaderArm();
    }

}
