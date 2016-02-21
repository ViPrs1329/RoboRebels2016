package org.stlpriory.robotics.commands.drivetrain;


public class DriveStraight extends Command {
    // If we are using PID, we have to change this to 
    // match the max speed we can input. 
    public static final double COEFFICIENT = 1;
    public static final enum Direction {FORWARD, REVERSE};

    private Direction direction;
    private int axisNumber;

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

    }
    public void execute()
    {
        double i = getInput() * direction == Direction.REVERSE ? -1 : 1;
        Robot.drivetrain.tankDrive(i * COEFFICIENT, i * COEFFICIENT);
    }
    public boolean isFinished()
    {
        return false;
    }
    public void end()
    {
        Robot.drivetrain.tankDrive(0, 0);
    }
    public void interrupted()
    {
        end();
    }
}
