package org.stlpriory.robotics.commands;

import org.stlpriory.robotics.Robot;
import org.stlpriory.robotics.utils.Debug;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.CANTalon.FeedbackDevice;
import edu.wpi.first.wpilibj.CANTalon.FeedbackDeviceStatus;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Command used to test PID values
 */
public class CANTalonTestCommand extends Command {

    private final CANTalon talon;

    public CANTalonTestCommand(final CANTalon talon) {
        requires(Robot.drivetrain);
        this.talon = talon;
    }

    // Called just before this Command runs the first time
    @Override
    protected void initialize() {
        Debug.println("CANTalonTestCommand ...");
        Debug.println("   P = " + this.talon.getP());
        Debug.println("   I = " + this.talon.getI());
        Debug.println("   D = " + this.talon.getD());
        Debug.println("   F = " + this.talon.getF());
        Debug.println("   I Zone = " + this.talon.getIZone());
        FeedbackDeviceStatus sensorStatus = this.talon.isSensorPresent(FeedbackDevice.QuadEncoder);
        Debug.println("   isSensorPluggedIn? " + (FeedbackDeviceStatus.FeedbackStatusPresent == sensorStatus));

        // ensure the talon is enabled
        if (!this.talon.isEnabled()) {
            this.talon.enable();
        }
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() {
        // talon.get() returns the current status of the Talon (usually a sensor value).
        // In Current mode, the return value is the output current.
        // In PercentVbus mode, the return value is the current applied throttle.
        // In Follower mode, the return value is the current applied throttle.
        // In Voltage mode, the return value is in volts
        // In Speed mode, the return value is the current speed.
        // In Position mode, the return value is the current sensor position.
        SmartDashboard.putNumber("talon.get()", this.talon.get());

        SmartDashboard.putNumber("talon.getError()", this.talon.getError());
        SmartDashboard.putNumber("sensorSpeed", this.talon.getSpeed()); // speed in RPM
        SmartDashboard.putNumber("quadEncVelocity", this.talon.getEncVelocity());
        SmartDashboard.putNumber("analogVelocity", this.talon.getAnalogInVelocity());
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    @Override
    protected void end() {
        this.talon.set(0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    @Override
    protected void interrupted() {
        this.talon.set(0);
    }
}
