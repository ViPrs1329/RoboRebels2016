
package org.stlpriory.robotics;

import org.stlpriory.robotics.subsystems.BallHolderSubsystem;
import org.stlpriory.robotics.subsystems.CANDrivetrainSubsystem;
import org.stlpriory.robotics.subsystems.DrivetrainSubsystem;
import org.stlpriory.robotics.subsystems.ShooterSubsystem;
import org.stlpriory.robotics.utils.Debug;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;

/**
 * The VM is configured to automatically run this class, and to call the functions corresponding 
 * to each mode, as described in the IterativeRobot documentation. If you change the name of this 
 * class or the package after creating this project, you must also update the manifest file in the 
 * resource directory.
 */
public class Robot extends IterativeRobot {

//    public static final int LEFT_FRONT_TALON_CHANNEL = 3;
//    public static final int LEFT_REAR_TALON_CHANNEL = 5;
//
//    public static final int RIGHT_FRONT_TALON_CHANNEL = 4;
//    public static final int RIGHT_REAR_TALON_CHANNEL = 1;
//    
//    public static double P_VALUE   = 0.5;
//    public static double I_VALUE   = 0.02;
//    public static double D_VALUE   = 0;
//    public static double F_VALUE   = 0.5;
//    public static int IZONE_VALUE  = (int) (0.2 * AMOpticalEncoderSpecs.PULSES_PER_REV);//100
//    public static double RAMP_RATE = 2;
//
//    public static DrivetrainSubsystem drivetrain = new DrivetrainSubsystem();
//    public static BallHolderSubsystem ballHolder = new BallHolderSubsystem();
//    public static ShooterSubsystem shooter = new ShooterSubsystem();
//    public static OI oi = new OI();
//
//    private Joystick xboxController;
//    private CANTalon talon;
//    private double targetValue = 0;
//
//    /**
//     * This function is run when the robot is first started up and should be used for any initialization code.
//     */
//    @Override
//    public void robotInit() {
//        this.xboxController = new Joystick(0);
//
//        int channel = 3;
//        createTalon(channel);
//
//        //SmartDashboard.putData("Test CANTalon", new CANTalonTestCommand(this.talon));
//        //SmartDashboard.putData("Auto Tune CANTalon", new PIDAutoTuneCommand(this.talon));
//        SmartDashboard.putData("Set PID", new DebugPIDCommand(this.talon));
//        SmartDashboard.putNumber("talon.set(value)", this.targetValue);
//    }
//
//    @Override
//    public void teleopPeriodic() {
//        Scheduler.getInstance().run();
//
//        double leftYstick = Utils.scale( this.xboxController.getAxis(AxisType.kY) );
//
//        double motorOutput = this.talon.getOutputVoltage() / this.talon.getBusVoltage();
//        SmartDashboard.putNumber("leftYstick input", leftYstick);
//        SmartDashboard.putNumber("motor output", motorOutput);
//        SmartDashboard.putNumber("motor output %", 100*motorOutput);
//        // Expect to see output of 2665
//        SmartDashboard.putNumber("sensor speed", this.talon.getSpeed()); // speed in RPM
//        SmartDashboard.putNumber("encoder speed", this.talon.getEncVelocity()); // speed in RPM
//
////        ballHolder.updateStatus();
//        // XBox button A or B?
//        
//        if (this.xboxController.getRawButton(1)) {
//            this.talon.changeControlMode(TalonControlMode.Speed);
//
//            double targetSpeed = leftYstick * CIMMotorSpecs.MAX_SPEED_RPM;
//            this.talon.set(targetSpeed);
//            
//            SmartDashboard.putString("Control Mode", "Speed");
//            // Expect to see output of 2665
//            SmartDashboard.putNumber("target speed", targetSpeed);
//
//        } else {
//            this.talon.changeControlMode(TalonControlMode.PercentVbus);
//            this.talon.set(leftYstick);
//            SmartDashboard.putString("Control Mode", "PercentVbus");
//        }
//        
//        // The difference between closed-loop set point and actual position/velocity in native units per 100ms
//        SmartDashboard.putNumber("closedLoopError", this.talon.getClosedLoopError());
//    }
//
//    private void createTalon(final int deviceNumber) {
//        this.talon = new CANTalon(deviceNumber);
//        this.talon.setFeedbackDevice(FeedbackDevice.QuadEncoder);
//
//        this.talon.configNominalOutputVoltage(+0.0f, -0.0f);
//        this.talon.configPeakOutputVoltage(+12.0f, -12.0f);
//
//        this.talon.configEncoderCodesPerRev(1000);//(AMOpticalEncoderSpecs.CYCLES_PER_REV);
//
//        // keep the motor and sensor in phase
//        this.talon.reverseSensor(false);
//
//        // Soft limits can be used to disable motor drive when the sensor position
//        // is outside of the limits
//        this.talon.setForwardSoftLimit(10000);
//        this.talon.enableForwardSoftLimit(false);
//        this.talon.setReverseSoftLimit(-10000);
//        this.talon.enableReverseSoftLimit(false);
//        
//        // brake mode: true for brake; false for coast
//        this.talon.enableBrakeMode(true);
//        
//        // Voltage ramp rate in volts/sec (works regardless of mode)
//        // 0V to 6V in one sec
//        //this.talon.setVoltageRampRate(12.0);
//
//        // The allowable close-loop error whereby the motor output is neutral regardless
//        // of the calculated result. When the closed-loop error is within the allowable 
//        // error the PID terms are zeroed (F term remains in effect) and the integral 
//        // accumulator is cleared. Value is in the same units as the closed loop error.
//        // Initially make the allowable error 10% of a revolution
//        int allowableClosedLoopErr = (int) (0.1 * AMOpticalEncoderSpecs.PULSES_PER_REV);
//        this.talon.setAllowableClosedLoopErr(2000);//allowableClosedLoopErr);
//
//        this.talon.setProfile(0);
//        this.talon.setP(P_VALUE);
//        this.talon.setI(I_VALUE);
//        this.talon.setD(D_VALUE);
//        this.talon.setF(F_VALUE);
//        this.talon.setIZone(IZONE_VALUE);
//        this.talon.setCloseLoopRampRate(RAMP_RATE);
//    }

    
// COMMENT OUT THE CODE ABOVE AND UNCOMMENT OUT THE CODE BELOW WHEN WE GET PUSH TO THE PROTOTYPE ROBOT
    
    // Initialize robot subsystems
    public static final DrivetrainSubsystem drivetrain = new DrivetrainSubsystem();;
    public static final BallHolderSubsystem ballHolder = new BallHolderSubsystem();
    public static final ShooterSubsystem shooter = new ShooterSubsystem();
    
    // Human operator interface
    public static final OI oi = new OI();
    public Joystick xboxController;
    
    private Command autonomousCommand;
    private Timer timer = new Timer();

    // ==================================================================================
    //                            ROBOT INIT SECTION
    // ==================================================================================

    @Override
    public void robotInit() {
        Debug.println("[Robot.robotInit()] Initializing...");
        timer.start();

        // Initialize the human operator interface ...
        //OI oi = new OI();
        this.xboxController = oi.getController();
        
        timer.stop();
        Debug.println("[RoboRebels.robotInit()] Done in " + timer.get() * 1e6 + " ms");
        Debug.println("------------------------------------------");
        Debug.println("           Robot ready!");
        Debug.println("------------------------------------------");
    }

    @Override
    public void disabledInit() {
    }

    // ==================================================================================
    //                          AUTONOMOUS MODE SECTION
    // ==================================================================================

    @Override
    public void autonomousInit() {
    }

    @Override
    public void autonomousPeriodic() {
        Scheduler.getInstance().run();
    }

    // ==================================================================================
    //                            TELEOP MODE SECTION
    // ==================================================================================

    @Override
    public void teleopInit() {
        if (autonomousCommand != null) {
            autonomousCommand.cancel();
        }
        // Record initial status values
        updateStatus();
    }

    @Override
    public void teleopPeriodic() {
        Scheduler.getInstance().run();
        // Record updated status values
        updateStatus();
    }
    
    @Override
    public void disabledPeriodic() {
    }

    /**
     * Call the updateStatus methods on all subsystems
     */
    public void updateStatus() {
        // drivetrain.updateStatus();
        // ballHolder.updateStatus();
        // shooter.updateStatus();
    }

    @Override
    public void testInit() {
        LiveWindow.run();
    }

    
    
}
