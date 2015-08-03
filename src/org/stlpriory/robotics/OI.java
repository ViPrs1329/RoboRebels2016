package org.stlpriory.robotics;

import org.stlpriory.robotics.commands.ElevatorDown;
import org.stlpriory.robotics.commands.ElevatorStop;
import org.stlpriory.robotics.commands.ElevatorUp;
import org.stlpriory.robotics.commands.HoldElevatorUp;
import org.stlpriory.robotics.commands.TogglePulse;
import org.stlpriory.robotics.commands.drivetrain.ShiftHigh;
import org.stlpriory.robotics.commands.drivetrain.ShiftLow;
import org.stlpriory.robotics.commands.drivetrain.ShiftSuperLow;
import org.stlpriory.robotics.triggers.ElevatorStill;
import org.stlpriory.robotics.utils.Debug;
import org.stlpriory.robotics.utils.Keymap;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

/**
 * This class is the glue that binds the controls on the physical operator interface to the commands and command groups that allow control
 * of the robot.
 */
public class OI {
    //// CREATING BUTTONS
    // One type of button is a joystick button which is any button on a joystick.
    // You create one by telling it which joystick it's on and which button
    // number it is.
    // Joystick stick = new Joystick(port);
    // Button button = new JoystickButton(stick, buttonNumber);

    // There are a few additional built in buttons you can use. Additionally,
    // by subclassing Button you can create custom triggers and bind those to
    // commands the same as any other Button.

    //// TRIGGERING COMMANDS WITH BUTTONS
    // Once you have a button, it's trivial to bind it to a button in one of
    // three ways:

    // Start the command when the button is pressed and let it run the command
    // until it is finished as determined by it's isFinished method.
    // button.whenPressed(new ExampleCommand());

    // Run the command while the button is being held down and interrupt it once
    // the button is released.
    // button.whileHeld(new ExampleCommand());

    // Start the command when the button is released  and let it run the command
    // until it is finished as determined by it's isFinished method.
    // button.whenReleased(new ExampleCommand());
	
	
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
    private final Joystick xboxController;
    private JoystickButton elevatorUpButton;
    private JoystickButton elevatorDownButton;
    private ElevatorStill elevatorStill;
    private JoystickButton shiftHighButton;
    private JoystickButton shiftLowButton;
    private JoystickButton pulseToggle;
    private JoystickButton shiftSuperLowButton;
    public OI() {
        Debug.println("[OI] Instantiating ...");
        Debug.println("[OI] Intitalizing gamepad to Driver's station USB port"  );
        
        this.xboxController = new Joystick(0);
        
        
        
        Debug.println("[OI] Initializing gamepad to raise elevator when the y button is pressed is pressed");
        elevatorUpButton = new JoystickButton(xboxController, Keymap.ELEVATOR_UP_BUTTON_KEY_MAP);
        elevatorUpButton.whenPressed(new ElevatorUp());
        elevatorUpButton.whenReleased(new ElevatorStop());
        
        Debug.println("[OI] Initializing gamepad to lower elevator when the a button is pressed is pressed");
        elevatorDownButton = new JoystickButton(xboxController, Keymap.ELEVATOR_DOWN_BUTTON_KEY_MAP);
        elevatorDownButton.whenPressed(new ElevatorDown());
        elevatorDownButton.whenReleased(new ElevatorStop());
        
        elevatorStill = new ElevatorStill(xboxController);
        elevatorStill.whileActive(new HoldElevatorUp());
        
        shiftHighButton = new JoystickButton(xboxController,Keymap.DRIVETAIN_SHIFT_HIGH_BUTTON_KEY_MAP);
        shiftHighButton.whenPressed(new ShiftHigh());
        
        shiftLowButton = new JoystickButton(xboxController,Keymap.DRIVETRAIN_SHIFT_LOW_BUTTON_KEY_MAP);
        shiftLowButton.whenPressed(new ShiftLow());
        
        pulseToggle = new JoystickButton(xboxController, Keymap.TOGGLE_PULSE);
        pulseToggle.whenPressed(new TogglePulse());
        
        shiftSuperLowButton = new JoystickButton(xboxController, Keymap.SHIFT_SUPER_LOW_BUTTON);
        shiftSuperLowButton.whenPressed(new ShiftSuperLow());

        Debug.println("[OI] Instantiation complete.");
    }

    public Joystick getGamePad() {
        return this.xboxController;
    }
}
