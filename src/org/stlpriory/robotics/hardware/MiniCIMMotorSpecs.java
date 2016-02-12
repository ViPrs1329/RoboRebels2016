package org.stlpriory.robotics.hardware;

/**
 * Specifications for the VEX Robotics Motor Data - Mini CIM Motor (217-3371). 
 * see http://motors.vex.com/mini-cim-motor
 */
public class MiniCIMMotorSpecs {
    public static final int MAX_SPEED_RPM   = 5840;
    public static final int MAX_SPEED_RPS   = MAX_SPEED_RPM / 60;
    public static final double MAX_VOLTAGE  = 12.0d;  // volts
    public static final double FREE_CURRENT = 1.5d; // amps
    
    private MiniCIMMotorSpecs() {
        // do not allow instances
    }
}
