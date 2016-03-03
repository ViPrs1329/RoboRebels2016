package org.stlpriory.robotics;

import org.stlpriory.robotics.commands.*;
import org.stlpriory.robotics.commands.drivetrain.DriveStraight;
import org.stlpriory.robotics.utils.Debug;
import org.stlpriory.robotics.utils.TriggerTrigger;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Joystick.RumbleType;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

/**
 * This class is the glue that binds the controls on the physical operator interface 
 * to the commands and command groups that allow control of the robot.
 */

public class OI {
    // Driver station port for controller (either 0 or 1)
    private static final int CONTROLLER_PORT = 0;

    /*
     * The bottons on the XBox controller follow this mapping
     * 1:  A
     * 2:  B
     * 3:  X
     * 4:  Y
     * 5:  Left Bumper
     * 6:  Right Bumper
     * 7:  Back
     * 8:  Start
     * 9:  Left thumbstickck
     * 10: Right thumbstick
     *
     * The axis on the controller follow this mapping
     * (all output is between -1 to 1)
     * 1:  Left stick X axis  (left:negative, right:positve)
     * 2:  Left stick Y axis  (up:negative, down:positive)
     * 3:  Triggers           (left:positive, right:negative)
     * 4:  Right stick X axis (left:negative, right:positive)
     * 5:  Right stick Y axis (up:negative, down:positive)
     * 6:  Directional pad
     */
    public static final int A_BUTTON = 1;
    public static final int B_BUTTON = 2;
    public static final int X_BUTTON = 3;
    public static final int Y_BUTTON = 4;
    public static final int RIGHT_BUMPER = 5;
    public static final int LEFT_BUMPER  = 6;
    public static final int BACK_BUTTON  = 7;
    public static final int START_BUTTON = 8;
    public static final int LEFT_STICK   = 9;
    public static final int RIGHT_STICK  = 10;
    public static final int LEFT_TRIGGER = 2;
    public static final int RIGHT_TRIGGER = 3;

    public static final int LEFT_STICK_X_AXIS  = 0;
    public static final int LEFT_STICK_Y_AXIS  = 1;
    public static final int RIGHT_STICK_X_AXIS = 4;
    public static final int RIGHT_STICK_Y_AXIS = 5;
    public static final int DIRECTION_PAD = 6;

    private final Joystick xboxController;
    private final JoystickButton holderUpButton;
    private final JoystickButton holderDownButton;
    private final JoystickButton throwButton;
    private final JoystickButton suckButton;
    private final JoystickButton holdSwitch;
    private final JoystickButton holderTop;
    private final JoystickButton holderBottom;
    public final JoystickButton forceButton;
    private final TriggerTrigger rightTriggerOn;
    private final TriggerTrigger leftTriggerOn;

	private JoystickButton debugWriteButton;

    public OI() {
        Debug.println("[OI] Instantiating ...");

        this.xboxController = new Joystick(CONTROLLER_PORT);

        this.holderUpButton = new JoystickButton(this.xboxController, B_BUTTON);
        this.holderUpButton.whileHeld(new BallHolderUp(true));

        this.holderDownButton = new JoystickButton(this.xboxController, A_BUTTON);
        this.holderDownButton.whileHeld(new BallHolderDown(true));

        this.throwButton = new JoystickButton(this.xboxController, Y_BUTTON);
        this.throwButton.whenPressed(new Shoot());
        this.throwButton.whenReleased(new StopShooter());

        this.suckButton = new JoystickButton(this.xboxController, X_BUTTON);
        this.suckButton.whileHeld(new Suck());

        this.holdSwitch = new JoystickButton(this.xboxController, START_BUTTON);
        this.holdSwitch.toggleWhenPressed(new Hold());

        this.holderTop = new JoystickButton(this.xboxController, RIGHT_BUMPER);
        this.holderTop.whenPressed(new BallHolderUp(false));

        this.holderBottom = new JoystickButton(this.xboxController, LEFT_BUMPER);
        this.holderBottom.whenPressed(new BallHolderDown(false));

        this.forceButton = new JoystickButton(this.xboxController, LEFT_STICK);

        this.rightTriggerOn = new TriggerTrigger(this.xboxController, RIGHT_TRIGGER);
        rightTriggerOn.whileActive(new DriveStraight(DriveStraight.Direction.FORWARD, rightTriggerOn.getPort()));

        this.leftTriggerOn = new TriggerTrigger(this.xboxController, LEFT_TRIGGER);
        leftTriggerOn.whileActive(new DriveStraight(DriveStraight.Direction.REVERSE, leftTriggerOn.getPort()));

        
        Debug.println("[OI] Instantiation complete.");
    }

    public Joystick getController() {
        return this.xboxController;
    }
    public void vibrate(boolean on)
    {
    	if(on)
    	{
    		this.xboxController.setRumble(RumbleType.kLeftRumble, 1);
    		this.xboxController.setRumble(RumbleType.kRightRumble, 1);
    	}
    	else
    	{
    		this.xboxController.setRumble(RumbleType.kLeftRumble, 0);
    		this.xboxController.setRumble(RumbleType.kRightRumble, 0);
    	}
    }
}
