package org.stlpriory.robotics.commands.drivetrain;

import org.stlpriory.robotics.Robot;
import org.stlpriory.robotics.utils.Debug;

import edu.wpi.first.wpilibj.command.Command;

public class DebugCommand extends Command {

	public DebugCommand() {
		// TODO Auto-generated constructor stub
	}

	public DebugCommand(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}

	public DebugCommand(double timeout) {
		super(timeout);
		// TODO Auto-generated constructor stub
	}

	public DebugCommand(String name, double timeout) {
		super(name, timeout);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void initialize() {
		// TODO Auto-generated method stub
	}

	@Override
	protected void execute() {
		// TODO Auto-generated method stub
		System.out.println("Pressed button");
		Robot.testMotor.set(1);
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
