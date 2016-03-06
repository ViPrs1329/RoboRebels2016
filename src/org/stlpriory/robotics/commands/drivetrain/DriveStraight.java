package org.stlpriory.robotics.commands.drivetrain;

import org.stlpriory.robotics.Robot;
import org.stlpriory.robotics.subsystems.DrivetrainSubsystem.Direction;

import edu.wpi.first.wpilibj.command.Command;


public class DriveStraight extends Command {
    // If we are using PID, we have to change this to 
    // match the max speed we can input. 
    public static final double COEFFICIENT = 1;

    private Direction direction;
    private int axisNumber;
    private startAngle;

    public DriveStraight(Direction direction, int axisNumber)
    {
        requires(Robot.drivetrain);
        this.axisNumber = axisNumber;
        this.direction = direction;
    }
    private double getInput()
    {
        return Robot.oi.getController().getRawAxis(axisNumber);
    }
    public void initialize()
    {
        startAngle = Robot.drivetrain.getAngle();
    }
    public void execute()
    {
        double i = getInput() * (direction == Direction.REVERSE ? 1 : -1);
        Robot.drivetrain.driveForward(i * COEFFICIENT, startAngle);
    }
    public boolean isFinished()
    {
        return false;
    }
    public void end()
    {
        Robot.drivetrain.stop();
    }
    public void interrupted()
    {
        end();
    }
}
