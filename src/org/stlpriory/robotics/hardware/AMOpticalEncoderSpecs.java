package org.stlpriory.robotics.hardware;

/**
 * Specifications for the E4T OEM Miniature Optical Encoder Kit (am-3132). The E4T
 * miniature optical encoder provides digital quadrature encoder feedback for high
 * volume, limited space applications. The E4T is designed to be a drop in replacement
 * for the E4P that offers higher maximum speed and increased output drive.
 *
 * @see <a href="http://www.andymark.com/product-p/am-3132.htm"> Andy Mark E4T OEM Optical Encoder</a>
 * @see <a href="http://cdn.usdigital.com/assets/assembly/E4T%20Assembly%20Instructions.pdf"> Encoder Assembly Instructions</a>
 * @see <a href="http://cdn.usdigital.com/assets/drawings/20465.pdf"> Encoder Drawings</a>
 */
public class AMOpticalEncoderSpecs {
    public static final int PULSES_PER_REV = 1440;
    public static final int CYCLES_PER_REV = 360;
    public static final double MIN_VOLTAGE = 5.0d;  // volts

    private AMOpticalEncoderSpecs() {
        // do not allow instances
    }
}
