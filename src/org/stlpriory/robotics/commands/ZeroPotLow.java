package org.stlpriory.robotics.commands;

import org.stlpriory.robotics.Robot;
import org.stlpriory.robotics.subsystems.BallHolderSubsystem;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Created by Adam on 2/26/2016.
 */
public class ZeroPotLow extends Command {
    @Override
    protected void initialize() {
        System.out.println("Zeroing potentiometer low");
    }

    @Override
    protected void execute() {
        // This is only allowed to run if the high number has been set first, since the high one is more important.
        if (Double.parseDouble(Robot.ROBOT_PROPS.getProperty(Robot.POT_LOW_VALUE)) == BallHolderSubsystem.EMPTY_VALUE) {
            System.out.println("The top one has been set");
            Robot.ROBOT_PROPS.setProperty(Robot.POT_LOW_VALUE, Double.toString(Robot.ballHolder.getAngle()));
            System.out.println(String.format("Got low value; it's %f", Robot.ballHolder.getAngle()));
            Robot.saveRobotConfigFile();
            System.out.println(String.format("saved file; value in file is %s", Robot.ROBOT_PROPS.getProperty(Robot.POT_LOW_VALUE, "nonexistent")));
            Robot.setProperties();
        }
        else
        {
            SmartDashboard.putString(BallHolderSubsystem.POT_SETTING_STATUS, "Nope!");
        }
    }

    @Override
    protected boolean isFinished() {
        return true;
    }

    @Override
    protected void end() {
            SmartDashboard.putString(BallHolderSubsystem.POT_SETTING_STATUS, "Both are set");
    }

    @Override
    protected void interrupted() {

    }
}
