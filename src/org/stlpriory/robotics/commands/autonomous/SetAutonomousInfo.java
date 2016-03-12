package org.stlpriory.robotics.commands.autonomous;
import org.stlpriory.robotics.Robot;

import edu.wpi.first.wpilibj.command.Command;
class SetAutonomousInfo extends Command {
    public SetAutonomousInfo()
    {
        super("SetAutonomousInfo");
    }
    public void initialize()
    {
    }
    public void execute()
    {
        Robot.autonomousInfo.setHeading(Robot.drivetrain.getAngle());
    }
    public void end()
    {
    }
    public boolean isFinished()
    {
        return true;
    }
    public void interrupted()
    {
        end();
    }
}
