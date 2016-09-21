package org.stlpriory.robotics.hardware;

/**
 * Specifications for the Andy Mark CIMcoder. This encoder mounts to the nose of a CIM Motor and
 * senses the rotations of the CIM Motor output shaft. A housing, mounted to the CIM Motor, protects
 * the encoder circuitry while a collet spins with the motor output shaft. Pull up resistors are
 * necessary to generate an output signal for the desired voltage level. This encoder has a 2 channel
 * quadrature output with 20 pulses per channel per revolution for sensing speed and direction.
 *
 * @see <a href="http://www.andymark.com/encoder-p/am-3314.htm"> Andy Mark CIMcoder</a>
 * @see <a href="http://files.andymark.com/PDFs/CIMcoder_Spec_Sheet_1-27-16.pdf"> CIMcoder Spec Sheet</a>
 */
public class CIMcoderSpecs {
    public static final int PULSES_PER_REV = 20;
    public static final double MIN_VOLTAGE = 4.0d;  // volts
    public static final double MAX_VOLTAGE = 24.0d; // volts

    private CIMcoderSpecs() {
        // do not allow instances
    }
}
