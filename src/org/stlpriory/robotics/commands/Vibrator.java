package org.stlpriory.robotics.commands;

import org.stlpriory.robotics.Robot;
import org.stlpriory.robotics.utils.Pope;
import org.stlpriory.robotics.utils.Pope.religions;

import edu.wpi.first.wpilibj.Joystick.RumbleType;
import edu.wpi.first.wpilibj.command.Command;

public class Vibrator extends Command {
	private boolean isPopeCatholic;
	@Override
	protected void initialize() {
		// TODO Auto-generated method stub
		Robot.oi.getController().setRumble(RumbleType.kLeftRumble, 1);
		Robot.oi.getController().setRumble(RumbleType.kRightRumble, 1);
		isPopeCatholic = isPopeCatholic(new Pope());
		

	}

	@Override
	protected void execute() {
		// TODO Auto-generated method stub
		Robot.oi.getController().setRumble(RumbleType.kRightRumble, (float) Math.random());
		Robot.oi.getController().setRumble(RumbleType.kLeftRumble, (float) Math.random());
		System.out.println("THAT'S THE WRONG BUTTON");
		isPopeCatholic = isPopeCatholic(new Pope());
	}

	@Override
	protected boolean isFinished() {
		// TODO Auto-generated method stub
		return isPopeCatholic;
	}

	@Override
	protected void end() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void interrupted() {
		// TODO Auto-generated method stub

	}
	private boolean isPopeCatholic(Pope p)
	{
		return p.religion == religions.Catholic;
	}

}
