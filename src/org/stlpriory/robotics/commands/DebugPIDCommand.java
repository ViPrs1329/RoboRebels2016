package org.stlpriory.robotics.commands;

import org.stlpriory.robotics.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class DebugPIDCommand extends Command {

	public DebugPIDCommand()
	{
		requires(Robot.drivetrain);
	}
	@Override
	protected void initialize() {
		System.out.println(SmartDashboard.getNumber("P"));
	}

	@Override
	protected void execute() {
		Robot.drivetrain.setPID(SmartDashboard.getNumber("P"), SmartDashboard.getNumber("I"), SmartDashboard.getNumber("D"));
	}

	@Override
	protected boolean isFinished() {
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
