package org.stlpriory.robotics.commands;

import org.stlpriory.robotics.Robot;
import org.stlpriory.robotics.subsystems.BallHolderSubsystem;
import org.stlpriory.robotics.utils.Debug;

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
		if (Double.parseDouble(Robot.ROBOT_PROPS
				.getProperty(Robot.POT_LOW_VALUE)) == BallHolderSubsystem.EMPTY_VALUE) {
			Robot.ROBOT_PROPS.setProperty(Robot.POT_LOW_VALUE,
					Double.toString(Robot.ballHolder.getAngle()));
			System.out.println("got angle");
			Robot.saveRobotConfigFile();
			System.out.println("saved file");
			Robot.ballHolder.setLowValue(Double.valueOf(Robot.ROBOT_PROPS
					.getProperty(Robot.POT_LOW_VALUE)));
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
		Debug.println("The pot value was "
				+ Robot.ballHolder.getAngle());
	}

	@Override
	protected void interrupted() {

	}
}
