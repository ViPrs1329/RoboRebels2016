package org.stlpriory.robotics.hardware;

/**
 * Specifications for the Andy Mark Snow Blower Motor (am-2235)
 * see http://www.andymark.com/Motor-p/am-2235.htm
 */
public class SnowBlowerMotorSpecs {
    public static final int MAX_SPEED_RPM   = 100;
    public static final int MAX_SPEED_RPS   = MAX_SPEED_RPM / 60;
    public static final double MAX_VOLTAGE  = 12.0d;  // volts
    public static final double FREE_CURRENT = 5.0d;   // amps
    
    private SnowBlowerMotorSpecs() {
        // do not allow instances
    }
}
