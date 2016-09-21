package org.stlpriory.robotics.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.stlpriory.robotics.Robot;

public class SetSpeed extends Command {

    public SetSpeed() {
        requires(Robot.drivetrain);
    }

    @Override
    protected void initialize() {
        // TODO Auto-generated method stub
        Robot.drivetrain.tankDrive(SmartDashboard.getNumber("Speed"), SmartDashboard.getNumber("Speed"));
    }

    @Override
    protected void execute() {
        // TODO Auto-generated method stub

    }

    @Override
    protected boolean isFinished() {
        // TODO Auto-generated method stub
        return false;
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
