package org.stlpriory.robotics.subsystems;

import org.stlpriory.robotics.RobotMap;
import org.stlpriory.robotics.utils.Constants;

import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.command.Subsystem;

public class Shooter extends Subsystem {
	private Talon rightShooter;
	private Talon leftShooter;
	public boolean holding = false;
	public Shooter()
	{
		rightShooter = new Talon(RobotMap.RIGHT_SHOOTER);
		leftShooter = new Talon(RobotMap.LEFT_SHOOTER);
	}
	public void shoot() 
	{
		rightShooter.set(1);
		leftShooter.set(-1);		
	}
	public void suck()
	{
		rightShooter.set(-1);
		leftShooter.set(1);
	}
	public void keep()
	{
		rightShooter.set(-Constants.KEEPING_SPEED);
		leftShooter.set(Constants.KEEPING_SPEED);
	}
	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub

	}
	public void stop() {
		// TODO Auto-generated method stub
		rightShooter.set(0);
		leftShooter.set(0);
	}

}
