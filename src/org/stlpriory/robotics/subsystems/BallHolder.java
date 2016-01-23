package org.stlpriory.robotics.subsystems;

import org.stlpriory.robotics.RobotMap;

import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.command.Subsystem;

public class BallHolder extends Subsystem {
    private Talon rightMotor;
    private Talon leftMotor;
    public BallHolder() 
    {
        rightMotor = new Talon(RobotMap.RIGHT_WINDOW_MOTOR);
        leftMotor = new Talon(RobotMap.LEFT_WINDOW_MOTOR);
    }
    public void set(double speed)
    {
        leftMotor.set(speed);
        rightMotor.set(speed);
    }
	@Override
	protected void initDefaultCommand() {
		
	}
}
