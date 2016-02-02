package org.stlpriory.robotics.subsystems;

import org.stlpriory.robotics.RobotMap;
import org.stlpriory.robotics.utils.Constants;

import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class BallHolder extends Subsystem {
    private Talon rightMotor;
    private Talon leftMotor;
    private AnalogPotentiometer pot;
    public enum Direction {UP,DOWN};
    public BallHolder() 
    {
        rightMotor = new Talon(RobotMap.RIGHT_WINDOW_MOTOR);
        leftMotor = new Talon(RobotMap.LEFT_WINDOW_MOTOR);
        pot = new AnalogPotentiometer(RobotMap.POTENTIOMETER, Constants.POTENTIOMETER_SCALE_FACTOR, Constants.POTENTIOMETER_OFFSET);

    }
    public void set(double speed)
    {
        leftMotor.set(speed);
        rightMotor.set(speed);
    }
    public double getAngle()
    {

        return pot.get();

    }
    public void set(Direction dir, double speed)
    {
        speed = Math.abs(speed) * (dir == Direction.UP ? 1 : -1);
        this.set(speed);

    }
    @Override
    protected void initDefaultCommand() {

    }
    public void stop()
    {
        set(0);
    }

    public void updateStatus() {
        SmartDashboard.putNumber("Right ballholder speed", rightMotor.getSpeed());
        SmartDashboard.putNumber("Left ballholder speed", leftMotor.getSpeed());
        SmartDashboard.putNumber("Ballholder potentiometer", pot.get());
    }
    
}
