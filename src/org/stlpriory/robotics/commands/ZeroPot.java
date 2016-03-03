package org.stlpriory.robotics.commands;

import edu.wpi.first.wpilibj.command.Command;

import org.stlpriory.robotics.Robot;
import org.stlpriory.robotics.utils.Debug;
import org.stlpriory.robotics.utils.PropertiesUtils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

/**
 * Created by Adam on 2/26/2016.
 */
public class ZeroPot extends Command {
	private Properties properties;

	@Override
	protected void initialize() {
		System.out.println("Zeroing potentiometer");
	}

	@Override
	protected void execute() {
		Robot.ROBOT_PROPS.setProperty(Robot.POT_ZERO_VALUE, Double.toString(Robot.ballHolder.getAbsoluteAngle()));
		Robot.saveRobotConfigFile();
		Robot.ballHolder.setZeroValue(Double.valueOf(Robot.ROBOT_PROPS.getProperty(Robot.POT_ZERO_VALUE)));
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
