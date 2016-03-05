package org.stlpriory.robotics.commands;

import org.stlpriory.robotics.Robot;
import org.stlpriory.robotics.subsystems.BallHolderSubsystem;
import org.stlpriory.robotics.utils.Debug;

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
		System.out.println("got angle");
		Robot.saveRobotConfigFile();
		System.out.println("saved file");
                Robot.setProperties();
		SmartDashboard.putString(BallHolderSubsystem.POT_SETTING_STATUS, "All good");
	}

	@Override
	protected boolean isFinished() {
		return true;
	}

	@Override
	protected void end() {
		Debug.println("The pot value was " + Robot.ballHolder.getAngle());
	}

	@Override
	protected void interrupted() {

	}
}
