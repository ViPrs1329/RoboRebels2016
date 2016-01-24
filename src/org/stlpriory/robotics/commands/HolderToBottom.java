package org.stlpriory.robotics.commands;

import org.stlpriory.robotics.Robot;
import org.stlpriory.robotics.subsystems.BallHolder.Direction;
import org.stlpriory.robotics.utils.Constants;

import edu.wpi.first.wpilibj.command.Command;

public class HolderToBottom extends Command {
	public HolderToBottom()
	{
		requires(Robot.ballHolder);	
	}
	@Override
	protected void initialize() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void execute() {
		// TODO Auto-generated method stub
		Robot.ballHolder.set(Direction.DOWN, 1);
	}

	@Override
	protected boolean isFinished() {
		// TODO Auto-generated method stub
		if(Robot.ballHolder.readAngle() < Math.abs((Constants.MIN_ANGLE-Constants.TOLERANCE)))
		{
			return true;
		}
		else
		{
			return false;
		}
	}

	@Override
	protected void end() {
		// TODO Auto-generated method stub
		Robot.ballHolder.set(0);

	}

	@Override
	protected void interrupted() {
		// TODO Auto-generated method stub
		Robot.ballHolder.set(0);

	}

}
