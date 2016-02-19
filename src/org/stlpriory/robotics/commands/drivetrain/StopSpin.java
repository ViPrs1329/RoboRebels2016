package org.stlpriory.robotics.commands.drivetrain;

import org.stlpriory.robotics.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class StopSpin extends Command {
    private int counter;

    public StopSpin() {
        super("StopSpin");
        requires(Robot.drivetrain);
        this.counter = 0;
    }

    @Override
    protected void initialize() {
        this.counter = 0;
    }

    @Override
    protected void execute() {
        Robot.drivetrain.tankDrive(0, 0);
        this.counter++;
    }

    @Override
    protected boolean isFinished() {
        return this.counter >= 10;
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
