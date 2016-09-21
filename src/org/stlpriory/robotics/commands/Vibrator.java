package org.stlpriory.robotics.commands;

import edu.wpi.first.wpilibj.Joystick.RumbleType;
import edu.wpi.first.wpilibj.command.Command;
import org.stlpriory.robotics.Robot;

public class Vibrator extends Command {
    @Override
    protected void initialize() {
        // TODO Auto-generated method stub
        Robot.oi.getController().setRumble(RumbleType.kLeftRumble, 1);
        Robot.oi.getController().setRumble(RumbleType.kRightRumble, 1);
    }

    @Override
    protected void execute() {
        // TODO Auto-generated method stub
        Robot.oi.getController().setRumble(RumbleType.kRightRumble, (float) Math.random());
        Robot.oi.getController().setRumble(RumbleType.kLeftRumble, (float) Math.random());
        System.out.println("THAT'S THE WRONG BUTTON");
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
