package org.stlpriory.robotics.commands;

import org.stlpriory.robotics.Robot;
import org.stlpriory.robotics.subsystems.BallHolderSubsystem;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Created by Adam on 2/26/2016.
 */
public class ZeroPotHigh extends Command {
    @Override
    protected void initialize() {
        System.out.println("Zeroing potentiometer");
    }

    @Override
    protected void execute() {
        Robot.ROBOT_PROPS.setProperty(Robot.POT_HIGH_VALUE, Double.toString(Robot.ballHolder.getAngle()));
        Robot.ROBOT_PROPS.setProperty(Robot.POT_LOW_VALUE, Double.toString(BallHolderSubsystem.EMPTY_VALUE));
        System.out.println("got angle; it's " + Robot.ballHolder.getAngle());
        Robot.saveRobotConfigFile();
        System.out.println(String.format("saved file; value in file is %s", Robot.ROBOT_PROPS.getProperty(Robot.POT_HIGH_VALUE, "nonexistent")));
        Robot.setProperties();
        SmartDashboard.putString(BallHolderSubsystem.POT_SETTING_STATUS, "All good");
    }

    @Override
    protected boolean isFinished() {
        return true;
    }

    @Override
    protected void end() {
    }

    @Override
    protected void interrupted() {

    }
}
