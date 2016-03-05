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
//	private Properties properties;

	@Override
	protected void initialize() {
		System.out.println("Zeroing potentiometer");
	}

	@Override
	protected void execute() {
		Robot.ROBOT_PROPS.setProperty(Robot.POT_HIGH_VALUE, Double.toString(Robot.ballHolder.getAbsoluteAngle()));
		Robot.ROBOT_PROPS.setProperty(Robot.POT_LOW_VALUE, Double.toString(BallHolderSubsystem.EMPTY_VALUE));
		System.out.println("got angle");
		Robot.saveRobotConfigFile();
		System.out.println("saved file");
		Robot.ballHolder.setHighValue(Double.valueOf(Robot.ROBOT_PROPS.getProperty(Robot.POT_HIGH_VALUE)));
		Robot.ballHolder.setLowValue(BallHolderSubsystem.EMPTY_VALUE);
		SmartDashboard.putString(BallHolderSubsystem.POT_SETTING_STATUS, "All good");
	}

	@Override
	protected boolean isFinished() {
		return true;
	}

	@Override
	protected void end() {
		Debug.println("The pot value was " + Robot.ballHolder.getAbsoluteAngle());
	}

	@Override
	protected void interrupted() {

	}
}
