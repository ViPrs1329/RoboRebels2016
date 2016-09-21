package org.stlpriory.robotics.utils;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Trigger;

public class TriggerTrigger extends Trigger {
    private Joystick joystick;
    private int port;

    public TriggerTrigger(Joystick joystick, int port) {
        super();
        this.port = port;
        this.joystick = joystick;
    }

    public boolean get() {
        return joystick.getRawAxis(port) > 0;
    }

    public int getPort() {
        return port;
    }
}
