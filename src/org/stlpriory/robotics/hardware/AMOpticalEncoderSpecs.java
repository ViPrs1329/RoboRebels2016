package org.stlpriory.robotics.hardware;

/**
 * Specifications for the E4T OEM Miniature Optical Encoder Kit (am-3132). The E4T 
 * miniature optical encoder provides digital quadrature encoder feedback for high 
 * volume, limited space applications. The E4T is designed to be a drop in replacement 
 * for the E4P that offers higher maximum speed and increased output drive.
 * 
 * see http://www.andymark.com/product-p/am-3132.htm
 * see http://cdn.usdigital.com/assets/assembly/E4T%20Assembly%20Instructions.pdf
 * see http://cdn.usdigital.com/assets/drawings/20465.pdf
 */
public class AMOpticalEncoderSpecs {
    public static final int PULSES_PER_REV  = 1440;
    public static final int CYCLES_PER_REV  = 360;

    public static final double MIN_VOLTAGE  = 5.0d;  // volts
    
    private AMOpticalEncoderSpecs() {
        // do not allow instances
    }
    
    /**
     * Return the encoder native units for position given an input number of rotations
     * @param inputRotations
     * @return velocity in native units
     */
    public static double getPosition(final double inputRotations) {
        return inputRotations * PULSES_PER_REV;
    }
    
    /**
     * Return the encoder native units for velocity given an input value 
     * in units of rotations per second
     * @param inputRPS input number of rotations per second
     * @return velocity in native units per second
     */
    public static double getVelocity(final double inputRPS) {
        return 0.1 * inputRPS * PULSES_PER_REV;
    }
}
