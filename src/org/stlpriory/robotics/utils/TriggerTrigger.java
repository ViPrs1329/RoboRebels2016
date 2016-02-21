package org.stlpriory.robotics.utils;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Trigger;

public class TriggerTrigger extends Trigger {
    // This might be unnecessary, but I have a sneaking suspicion that the 
    // zero point for the triggers isn't 0, so I have this. 
    public static final double ZERO_POINT = .5;
    private Joystick joystick;
    private int port;
    public TriggerTrigger(Joystick joystick, int port)
    {
        super();
        this.port = port;
        this.joystick = joystick;
    }
    public boolean get()
    {
        return joystick.getRawAxis(port) > ZERO_POINT;
    }
    public int getPort()
    {
        return port;
    }
}
