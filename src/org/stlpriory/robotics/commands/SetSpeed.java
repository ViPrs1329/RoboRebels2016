package org.stlpriory.robotics.commands;

import org.stlpriory.robotics.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class SetSpeed extends Command {
	
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
