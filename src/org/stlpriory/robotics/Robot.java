
package org.stlpriory.robotics;

import org.stlpriory.robotics.commands.CANTalonTestCommand;
import org.stlpriory.robotics.commands.DebugPIDCommand;
import org.stlpriory.robotics.commands.autonomous.AutonomousCommand;
import org.stlpriory.robotics.subsystems.BallHolder;
import org.stlpriory.robotics.subsystems.ExampleSubsystem;
import org.stlpriory.robotics.subsystems.Shooter;
import org.stlpriory.robotics.subsystems.TestTankDrivetrain;
import org.stlpriory.robotics.utils.Constants;
import org.stlpriory.robotics.utils.Debug;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.CANTalon.FeedbackDevice;
import edu.wpi.first.wpilibj.CANTalon.TalonControlMode;
import edu.wpi.first.wpilibj.IterativeRobot;
//import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The VM is configured to automatically run this class, and to call the functions corresponding to each mode, as described in the
 * IterativeRobot documentation. If you change the name of this class or the package after creating this project, you must also update the
 * manifest file in the resource directory.
 */
public class Robot extends IterativeRobot {

    public static final ExampleSubsystem exampleSubsystem = new ExampleSubsystem();
    public static OI oi;
    public static TestTankDrivetrain drivetrain = new TestTankDrivetrain();
    public static BallHolder ballHolder = new BallHolder();
    public static Shooter shooter = new Shooter();
    //    private Timer timer = new Timer();

    private Command autonomousCommand;

    /**
     * This function is run when the robot is first started up and should be used for any initialization code.
     */
    @Override
    public void robotInit() {
        System.out.println("Starting robot");
        oi = new OI();

//        // See all commands running on the scheduler
//        if (Debug.isDebugMode()) {
//            SmartDashboard.putData(Scheduler.getInstance());
//        }

    // ============================================================================
    //           LOGIC RELATED TO TUNING TALON PID VALUES
    // ============================================================================
        CANTalon talon = Robot.drivetrain.leftFront;
        talon.setFeedbackDevice(FeedbackDevice.QuadEncoder);
        // For quadrature encoders, each unit corresponds a quadrature edge
        // (4X). So a 250 count encoder will produce 1000 edge events per
        // rotation. An example speed of 200 would then equate to 20% of a
        // rotation per 100ms, or 10 rotations per second.
        talon.configEncoderCodesPerRev(1000);
        talon.reverseSensor(false);
        talon.reverseOutput(false);
        
        talon.setForwardSoftLimit(10000);
        talon.enableForwardSoftLimit(false);
        talon.setReverseSoftLimit(-10000);
        talon.enableReverseSoftLimit(false);
        
        talon.setAllowableClosedLoopErr(2000);
        
        // Set the appropriate target value on the talon, depending on the mode.
        // In Current mode, the value is in amperes
        // In PercentVbus mode, the value is between -1.0 and 1.0, with 0.0 as stopped
        // In Follower mode, the value is the integer device ID of the talon to duplicate
        // In Voltage mode, the value is in volts
        // In Speed mode, the value is in position change per 100ms
        // In Position mode, value is in encoder ticks or an analog value, depending on the sensor
        talon.changeControlMode(TalonControlMode.Speed);
        double targetValue = 0.5 * 1000.0;  // 50% speed x 1000 RPM
        talon.set(targetValue);
        
        SmartDashboard.putData("Test CANTalon", new CANTalonTestCommand(talon));
        SmartDashboard.putData("Set PID", new DebugPIDCommand(talon));
        SmartDashboard.putNumber("talon.set(value)", targetValue);
    }

    @Override
    public void disabledPeriodic() {
        Scheduler.getInstance().run();
        updateStatus();
    }

    @Override
    public void autonomousInit() {

        this.autonomousCommand = new AutonomousCommand();
        if (this.autonomousCommand != null) {
            this.autonomousCommand.start();
        }
    }

    /**
     * This function is called periodically during autonomous
     */
    @Override
    public void autonomousPeriodic() {
        Scheduler.getInstance().run();
    }

    @Override
    public void teleopInit() {
        // This makes sure that the autonomous stops running when
        // teleop starts running. If you want the autonomous to
        // continue until interrupted by another command, remove
        // this line or comment it out.
        if (this.autonomousCommand != null) {
            this.autonomousCommand.cancel();
        }

        updateStatus();
    }

    /**
     * This function is called when the disabled button is hit. You can use it to reset subsystems before shutting down.
     */
    @Override
    public void disabledInit() {

    }

    /**
     * This function is called periodically during operator control
     */
    @Override
    public void teleopPeriodic() {
        Scheduler.getInstance().run();
        updateStatus();
    }

    /**
     * This function is called periodically during test mode
     */
    @Override
    public void testPeriodic() {
        LiveWindow.run();
    }

    /**
     * Call the updateStatus methods on all subsystems
     */
    public void updateStatus() {
        if (Debug.isDebugMode()) {
//            drivetrain.updateStatus();
//            ballHolder.updateStatus();
//            shooter.updateStatus();
        }
    }

}
