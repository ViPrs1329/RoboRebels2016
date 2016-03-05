package org.stlpriory.robotics.commands.drivetrain;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.stlpriory.robotics.utils.Constants;
import org.stlpriory.robotics.utils.Utils;
import org.stlpriory.robotics.Robot;
import org.stlpriory.robotics.subsystems.BallHolderSubsystem.Direction;

public class DriveForward extends Command {

    double startTime;
    double timeCurrent;
    double goalTime;
    Timer timer = new Timer();
    Direction direction;

    public DriveForward(double time, Direction direction) {
        super("DriveWithGamepad");
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
        // Variable "din" needs to be in feet
        // If isFoward is true, it will drive forwards, otherwise it will drive
        // in reverse.
        requires(Robot.drivetrain);
        goalTime = time;
        this.direction = direction;

    }

    // Called just before this Command runs the first time
    protected void initialize() {
        timer.start();
        startTime = timer.get();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        if (direction = Direction.FORWARD) {
            Robot.drivetrain.tankDrive(DrivetrainSubsystem.FORWARD_SPEED, DrivetrainSubsystem.FORWARD_SPEED);
            System.out.println("Driving forward for time");
        } else {
            Robot.drivetrain.tankDrive(-DrivetrainSubsystem.FORWARD_SPEED, -DrivetrainSubsystem.FORWARD_SPEED);
        }
        SmartDashboard.putNumber("Robot Speed",
                Robot.drivetrain.getRobotSpeed());
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        timeCurrent = timer.get(); // fix this
        return timeCurrent - startTime > goalTime;
        SmartDashboard.putNumber("Distance", totalDistance);
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
