package org.stlpriory.robotics.commands;

import org.stlpriory.robotics.Robot;
import org.stlpriory.robotics.hardware.AMOpticalEncoderSpecs;
import org.stlpriory.robotics.hardware.CIMMotorSpecs;
import org.stlpriory.robotics.utils.Debug;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.CANTalon.FeedbackDevice;
import edu.wpi.first.wpilibj.CANTalon.FeedbackDeviceStatus;
import edu.wpi.first.wpilibj.CANTalon.TalonControlMode;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Command used to test PID values
 */
public class PIDAutoTuneCommand extends Command {

    private final CANTalon talon;
    // command timeout in seconds
    private final double timeout = 15.0;

    private int executeCounter = 0;
    private int innerCounter = 0;
    private final double yStickValue = 0.5;
    private final double maxRPM = CIMMotorSpecs.MAX_SPEED_RPM;
    private final double targetRPM = this.yStickValue * this.maxRPM;

    // If the closed-loop output exceeds these settings the motor output is capped
    private final double posPeakOutput = +1023.0d;
    private final double negPeakOutput = -1023.0d;

    private String sensorStatus = "";
    private boolean reverseSensor = false;
    private double maxErr = this.targetRPM;
    private double errLow = this.maxErr;
    private double errHigh = this.maxErr;
    private double f_gain = 0;
    private double p_gain = 0;

    public PIDAutoTuneCommand(final CANTalon talon) {
        requires(Robot.drivetrain);
        this.talon = talon;
    }

    // Called just before this Command runs the first time
    @Override
    protected void initialize() {
        Debug.println("PIDAutoTuneCommand ...");
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
        this.talon.changeControlMode(TalonControlMode.Speed);
        this.talon.set(this.targetRPM);

        SmartDashboard.putNumber("target RPM", this.targetRPM);

        setTimeout(this.timeout);
    }

    // Called repeatedly (about every 20ms or 0.02s) when this Command is scheduled to run
    @Override
    protected void execute() {
        this.executeCounter++;

        double speed = this.talon.getSpeed();
        double target = this.targetRPM;
        SmartDashboard.putNumber("sensorSpeed", speed);
        SmartDashboard.putNumber("target", target);

        // Use the first 2 sec to spin up the motor
        if (this.executeCounter < 100) {
            SmartDashboard.putString("Status", "spinning up motor ...");

            FeedbackDeviceStatus status = this.talon.isSensorPresent(FeedbackDevice.QuadEncoder);
            switch (status) {
                case FeedbackStatusPresent:
                    this.sensorStatus = "FeedbackStatusPresent";
                    break;
                case FeedbackStatusNotPresent:
                    this.sensorStatus = "FeedbackStatusNotPresent";
                    break;
                case FeedbackStatusUnknown:
                default:
                    this.sensorStatus = "FeedbackStatusUnknown";
                    break;
            }

            // Use the next 2 sec to test that the motor and sensor are in phase
        } else if (this.executeCounter < 200) {
            SmartDashboard.putString("Status", "checking that motor and sensor are in phase ...");

            double sensorPosition = this.talon.getPosition();
            double throttle = this.yStickValue * this.posPeakOutput;

            if (((throttle > 0) && (sensorPosition < 0)) || ((throttle < 0) && (sensorPosition > 0))) {
                this.reverseSensor = true;
                this.talon.reverseSensor(true);
            }

            // Use the next 5 sec to compute a feed forward gain
        } else if (this.executeCounter < 450) {
            SmartDashboard.putString("Status", "computing a feed forward gain ...");

            // Convert our measured speed to sensor native units
            double speedInNativeUnits = (speed / 600) * AMOpticalEncoderSpecs.PULSES_PER_REV;
            // Calculate the feed forward gain
            this.f_gain = this.posPeakOutput / speedInNativeUnits;
            this.talon.setF(this.f_gain);

            // Use the remaining time to compute a proportional gain
        } else {
            SmartDashboard.putString("Status", "computing a proportional gain ...");
            this.innerCounter++;

            double err = Math.abs(speed - target);
            if (err > this.maxErr) {
                this.maxErr = err;
            }
            int errorInNativeUnits = this.talon.getClosedLoopError();
            // Adjust the throttle by a fixed value of 10%. The “Positive Peak Output” or
            // “Forward Peak Output” refers to the “strongest” motor output when the Closed-Loop
            // motor output is positive. If the Closed-Loop Output exceeds this setting, the
            // motor output is capped. The default value is +1023 as read in the web-based
            // configuration Self-Test. The peak outputs are +1023 representing full forward,
            // and -1023 representing full reverse
            this.p_gain = (0.1 * this.posPeakOutput) / errorInNativeUnits;

            // Sample data for 1 sec before adjusting the gain
            if (this.innerCounter < 20) {
                if ((speed > target) && (speed > this.errHigh)) {
                    this.errHigh = speed;
                }
                if ((speed < target) && (speed < this.errLow)) {
                    this.errLow = speed;
                }
            } else {
                // If the speed is oscillating about the target value reduce the gain
                if ((this.errLow < target) && (this.errHigh > target)) {
                    this.p_gain *= 0.1;
                }
                this.talon.setP(this.p_gain);

                this.innerCounter = 0;
                this.errHigh = target;
                this.errLow = target;
            }
        }

        SmartDashboard.putNumber("P", this.talon.getP());
        SmartDashboard.putNumber("I", this.talon.getI());
        SmartDashboard.putNumber("D", this.talon.getD());
        SmartDashboard.putNumber("F", this.talon.getF());

    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    protected boolean isFinished() {
        return isTimedOut();
    }

    // Called once after isFinished returns true
    @Override
    protected void end() {
        this.talon.set(0);
        SmartDashboard.putString("Status", "DONE ...");
        System.out.println("sensor status = " + this.sensorStatus);
        System.out.println("reversed sensor ? " + this.reverseSensor);
        System.out.println("Max error (RPM)   =  " + this.maxErr);
        System.out.println("Feed forward gain =  " + this.f_gain);
        System.out.println("Proportinal gain  =  " + this.p_gain);

        SmartDashboard.putNumber("P", this.talon.getP());
        SmartDashboard.putNumber("I", this.talon.getI());
        SmartDashboard.putNumber("D", this.talon.getD());
        SmartDashboard.putNumber("F", this.talon.getF());
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    @Override
    protected void interrupted() {
        this.talon.set(0);
    }
}
