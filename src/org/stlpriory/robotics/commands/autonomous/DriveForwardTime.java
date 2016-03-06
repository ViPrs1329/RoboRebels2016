package org.stlpriory.robotics.commands.autonomous;

import org.stlpriory.robotics.Robot;
import org.stlpriory.robotics.subsystems.DrivetrainSubsystem.Direction;
import org.stlpriory.robotics.subsystems.DrivetrainSubsystem;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class DriveForwardTime extends Command {

    double startTime;
    double timeCurrent;
    double goalTime;
    double startHeading;
    Timer timer = new Timer();
    Direction direction;

    public DriveForwardTime(double time, Direction direction) {
        super("DriveWithGamepad");
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
        // Variable "din" needs to be in feet
        // If isFoward is true, it will drive forwards, otherwise it will drive
        // in reverse.
        requires(Robot.drivetrain);
        goalTime = time;
        this.startHeading = Robot.drivetrain.getAngle();
        this.direction = direction;

    }

    // Called just before this Command runs the first time
    protected void initialize() {
        timer.start();
        startTime = timer.get();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        if (direction == Direction.FORWARD) {
            Robot.drivetrain.driveForward(DrivetrainSubsystem.FORWARD_SPEED, startHeading);
            System.out.println("Driving forward for time");
        } else {
            Robot.drivetrain.tankDrive(-DrivetrainSubsystem.FORWARD_SPEED, startHeading);
            System.out.println("Driving backward for time");
        }
        SmartDashboard.putNumber("Robot Speed",
                Robot.drivetrain.getSpeed());
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        timeCurrent = timer.get();
        return timeCurrent - startTime > goalTime;
    }

    // Called once after isFinished returns true
    protected void end() {
        Robot.drivetrain.stop();
        System.out.println("I finished Driving");

    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
        Robot.drivetrain.stop();
    }
}
