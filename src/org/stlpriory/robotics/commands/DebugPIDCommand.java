package org.stlpriory.robotics.commands;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.stlpriory.robotics.Robot;
import org.stlpriory.robotics.utils.Debug;

public class DebugPIDCommand extends Command {

    private final CANTalon talon;

    public DebugPIDCommand(final CANTalon talon) {
        requires(Robot.drivetrain);
        this.talon = talon;
    }

    @Override
    protected void initialize() {
        Debug.println("DebugPIDCommand ...");
        Debug.println("   device ID = " + this.talon.getDeviceID());
        Debug.println("   P = " + this.talon.getP());
        Debug.println("   I = " + this.talon.getI());
        Debug.println("   D = " + this.talon.getD());
        Debug.println("   F = " + this.talon.getF());
    }

    @Override
    protected void execute() {
        this.talon.setProfile(0); // can be 0 or 1
        this.talon.setP(SmartDashboard.getNumber("P"));
        this.talon.setI(SmartDashboard.getNumber("I"));
        this.talon.setD(SmartDashboard.getNumber("D"));
        this.talon.setF(SmartDashboard.getNumber("F"));

        SmartDashboard.putNumber("P", this.talon.getP());
        SmartDashboard.putNumber("I", this.talon.getI());
        SmartDashboard.putNumber("D", this.talon.getD());
        SmartDashboard.putNumber("F", this.talon.getF());
    }

    @Override
    protected boolean isFinished() {
        return true;
    }

    @Override
    protected void end() {
        SmartDashboard.putNumber("P", this.talon.getP());
        SmartDashboard.putNumber("I", this.talon.getI());
        SmartDashboard.putNumber("D", this.talon.getD());
        SmartDashboard.putNumber("F", this.talon.getF());
    }

    @Override
    protected void interrupted() {
        // TODO Auto-generated method stub

    }

}
