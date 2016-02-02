package org.stlpriory.robotics.commands.drivetrain;

import edu.wpi.first.wpilibj.command.Command;
import org.stlpriory.robotics.*;

/**
 *
 */
public class DriveWithGamepad extends Command {

    public DriveWithGamepad() {
        super("DriveWithGamepad");
        requires(Robot.drivetrain);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        Robot.drivetrain.tankDrive(Robot.oi.getGamePad());
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
