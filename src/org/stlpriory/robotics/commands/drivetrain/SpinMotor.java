package org.stlpriory.robotics;

import edu.wpi.first.wpilibj.command.Command;

public class SpinMotor extends Command {
	
	private int counter;
	
	public SpinMotor()
	{
		super("SpinMotor");
		requires(Robot.drivetrain);
		counter = 0;
	}
	@Override
	protected void initialize() {
		System.out.println("button");
	}

	@Override
	protected void execute() {
		Robot.drivetrain.tankDrive(1, 1);
		counter++;
	}

	@Override
	protected boolean isFinished() {
		return counter >= 10;		
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
