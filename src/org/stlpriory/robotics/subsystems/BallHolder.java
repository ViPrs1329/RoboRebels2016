package org.stlpriory.robotics.subsystems;

import org.stlpriory.robotics.RobotMap;
import org.stlpriory.robotics.utils.Constants;

import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.command.Subsystem;

public class BallHolder extends Subsystem {
    private Talon rightMotor;
    private Talon leftMotor;
    private AnalogPotentiometer pot;
    public BallHolder() 
    {
        rightMotor = new Talon(RobotMap.RIGHT_WINDOW_MOTOR);
        leftMotor = new Talon(RobotMap.LEFT_WINDOW_MOTOR);
        pot = new AnalogPotentiometer(RobotMap.POTENTIOMETER,Constants.POTENTIOMETER_SCALE_FACTOR,Constants.POTENTIOMETER_OFFSET);
        
    }
    public void set(double speed)
    {
        leftMotor.set(speed);
        rightMotor.set(speed);
    }
    public double readAngle()
    {
    	
    	return pot.get();
    	
    }
	@Override
	protected void initDefaultCommand() {
		
	}
}
