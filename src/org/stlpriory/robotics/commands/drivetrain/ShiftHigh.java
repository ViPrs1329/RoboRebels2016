package org.stlpriory.robotics.commands.drivetrain;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class ShiftHigh extends Command {

    public ShiftHigh() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
//    	requires(Robot.drivetrain);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
//    	Robot.drivetrain.shiftHigh();
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return true;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
