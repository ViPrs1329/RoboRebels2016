package org.stlpriory.robotics.commands.drivetrain;

import org.stlpriory.robotics.Robot;
import org.stlpriory.robotics.utils.Constants;
import org.stlpriory.robotics.utils.Utils;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class DriveForward extends Command {

    double goalDistance = 0.0;
    double startTime;
    double distance;
    double totalDistance = 0.0;
    double timeCurrent;
    double distance2;
    double lastVelocity;
    Timer timer = new Timer();
    boolean forward;

    public DriveForward(double din, boolean isForward) {
        super("DriveWithGamepad");
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
        // Variable "din" needs to be in feet
        // If isFoward is true, it will drive forwards, otherwise it will drive
        // in reverse.
        // requires(Robot.drivetrain);
        setTimeout(.1);
        goalDistance = Utils.TALONdistance(din);
        forward = isForward;

    }

    // Called just before this Command runs the first time
    protected void initialize() {
        timer.start();
        startTime = timer.get();
        totalDistance = 0.0;
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        if (forward) {
            		Robot.drivetrain.tankDrive(Constants.DEFAULT_FORWARD_SPEED,Constants.DEFAULT_FORWARD_SPEED);
            System.out.println("Driving forward");
        } else {
            			Robot.drivetrain.tankDrive(-1*Constants.DEFAULT_FORWARD_SPEED,-1*Constants.DEFAULT_FORWARD_SPEED);
        }
        		SmartDashboard.putNumber("Robot Speed",
        				Robot.drivetrain.getRobotSpeed());
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        if (getDistance() >= goalDistance) {
            return true;
        }
        SmartDashboard.putNumber("Distance", getDistance());
        return false;
    }
    private double getDistance()
    {
    	timeCurrent = timer.get();
		distance = (Robot.drivetrain.getRobotSpeed() * (timeCurrent - startTime));
		distance2 = (lastVelocity * (timeCurrent-startTime));
		totalDistance = totalDistance + ((distance+distance2)/2);//using trapezoid rule to estimate distance
		System.out.println( "distance " + totalDistance);
		startTime = timer.get();
		lastVelocity = Robot.drivetrain.getRobotSpeed();
		return distance;
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.drivetrain.tankDrive(0, 0);
        System.out.println("I finished Driving");

    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	Robot.drivetrain.tankDrive(0, 0);
    }
}
