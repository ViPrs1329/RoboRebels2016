
package org.stlpriory.robotics;

import org.stlpriory.robotics.commands.CANTalonTestCommand;
import org.stlpriory.robotics.commands.DebugPIDCommand;
import org.stlpriory.robotics.commands.PIDAutoTuneCommand;
import org.stlpriory.robotics.subsystems.BallHolder;
import org.stlpriory.robotics.subsystems.Shooter;
import org.stlpriory.robotics.subsystems.TestTankDrivetrain;
import org.stlpriory.robotics.utils.Utils;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.CANTalon.FeedbackDevice;
import edu.wpi.first.wpilibj.CANTalon.TalonControlMode;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Joystick.AxisType;
//import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The VM is configured to automatically run this class, and to call the functions corresponding to each mode, as described in the
 * IterativeRobot documentation. If you change the name of this class or the package after creating this project, you must also update the
 * manifest file in the resource directory.
 */
public class Robot extends IterativeRobot {

    public static final int LEFT_FRONT_TALON_CHANNEL = 3;
    public static final int LEFT_REAR_TALON_CHANNEL = 5;

    public static final int RIGHT_FRONT_TALON_CHANNEL = 4;
    public static final int RIGHT_REAR_TALON_CHANNEL = 1;
    
    public static double P_VALUE = 0.5;
    public static double I_VALUE = 0.02;
    public static double D_VALUE = 0;
    public static double F_VALUE = 0.5;
    public static int IZONE_VALUE = 100;
    public static double RAMP_RATE = 10;

    public static TestTankDrivetrain drivetrain = new TestTankDrivetrain();
    public static BallHolder ballHolder = new BallHolder();
    public static Shooter shooter = new Shooter();
    public static OI oi = new OI();

    private Joystick xboxController;
    private CANTalon talon;
    private double targetValue = 0;

    /**
     * This function is run when the robot is first started up and should be used for any initialization code.
     */
    @Override
    public void robotInit() {
        this.xboxController = new Joystick(0);

        int channel = 3;
        createTalon(channel);

        //SmartDashboard.putData("Test CANTalon", new CANTalonTestCommand(this.talon));
        SmartDashboard.putData("Auto Tune CANTalon", new PIDAutoTuneCommand(this.talon));
        SmartDashboard.putData("Set PID", new DebugPIDCommand(this.talon));
        SmartDashboard.putNumber("talon.set(value)", this.targetValue);
    }

    @Override
    public void teleopPeriodic() {
        Scheduler.getInstance().run();

        double leftYstick = Utils.scale( this.xboxController.getAxis(AxisType.kY) );

        double motorOutput = this.talon.getOutputVoltage() / this.talon.getBusVoltage();
        SmartDashboard.putNumber("leftYstick input", leftYstick);
        SmartDashboard.putNumber("motor output", motorOutput);
        SmartDashboard.putNumber("speed RPM", this.talon.getSpeed()); // speed in RPM

        // XBox button A or B?
        if (this.xboxController.getRawButton(1)) {
            // leftYstick range [-1, 1].  Speed range [-1500, 1500] RPM
            double targetSpeed = leftYstick * 3000.0;
            this.talon.changeControlMode(TalonControlMode.Speed);
            this.talon.set(targetSpeed);
            
            SmartDashboard.putString("Control Mode", "Speed");
            SmartDashboard.putNumber("target speed RPM", targetSpeed);

        } else {
            this.talon.changeControlMode(TalonControlMode.PercentVbus);
            this.talon.set(leftYstick);
            SmartDashboard.putString("Control Mode", "PercentVbus");
        }

        
        SmartDashboard.putNumber("talon.get()", this.talon.get());
        SmartDashboard.putNumber("talon.getError()", this.talon.getError());
        // The error is in native units per 100ms.  
        SmartDashboard.putNumber("talon.getClosedLoopError()", this.talon.getClosedLoopError());
    }

    private void createTalon(final int deviceNumber) {
        this.talon = new CANTalon(deviceNumber);
        this.talon.setFeedbackDevice(FeedbackDevice.QuadEncoder);

        this.talon.configNominalOutputVoltage(+0.0f, -0.0f);
        this.talon.configPeakOutputVoltage(+12.0f, -12.0f);

        // For quadrature encoders, each unit corresponds a quadrature edge
        // (4X). So a 250 count encoder will produce 1000 edge events per
        // rotation. An example speed of 200 would then equate to 20% of a
        // rotation per 100ms, or 10 rotations per second.
        this.talon.configEncoderCodesPerRev(1000);

        this.talon.reverseSensor(false);
        this.talon.reverseOutput(false);
        this.talon.setCloseLoopRampRate(RAMP_RATE);

        this.talon.setForwardSoftLimit(10000);
        this.talon.enableForwardSoftLimit(false);

        this.talon.setReverseSoftLimit(-10000);
        this.talon.enableReverseSoftLimit(false);

        this.talon.setAllowableClosedLoopErr(2000);

        this.talon.setProfile(0);
        this.talon.setP(P_VALUE);
        this.talon.setI(I_VALUE);
        this.talon.setD(D_VALUE);
        this.talon.setF(F_VALUE);
        this.talon.setIZone(IZONE_VALUE);

//        // Set the appropriate target value on the talon, depending on the mode.
//        // In Current mode, the value is in amperes
//        // In PercentVbus mode, the value is between -1.0 and 1.0, with 0.0 as stopped
//        // In Follower mode, the value is the integer device ID of the talon to duplicate
//        // In Voltage mode, the value is in volts
//        // In Speed mode, the value is in position change per 100ms
//        // In Position mode, value is in encoder ticks or an analog value, depending on the sensor
//        this.talon.changeControlMode(TalonControlMode.Speed);
//        this.targetValue = 0.5 * 1000.0; // 50% speed x 1000 RPM
//        this.talon.set(this.targetValue);
    }

}
