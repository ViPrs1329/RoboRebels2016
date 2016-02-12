package org.stlpriory.robotics.hardware;

/**
 * Specifications for the VEX Robotics Motor Data - CIM Motor (217-2000). 
 * see http://motors.vex.com/cim-motor
 */
public class CIMMotorSpecs {
    public static final int MAX_SPEED_RPM   = 5330;
    public static final int MAX_SPEED_RPS   = MAX_SPEED_RPM / 60;
    public static final double MAX_VOLTAGE  = 12.0d;  // volts
    public static final double FREE_CURRENT = 2.7d; // amps
    
    private CIMMotorSpecs() {
        // do not allow instances
    }
}
