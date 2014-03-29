package frc.team4069.thirdyear;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public interface RobotMap {

    //DRIVETRAIN
    /**
     * Left drive motor port.
     */
    public static final int LEFT_MOTOR = 1;
    /**
     * Right drive motor port.
     */
    public static final int RIGHT_MOTOR = 2;
    /**
     * Left encoder Digital I/O ports.
     */
    public static final int LEFT_ENCODER_1 = 2;
    public static final int LEFT_ENCODER_2 = 3;
    /**
     * Right encoder Digital I/O ports.
     */
    public static final int RIGHT_ENCODER_1 = 4;
    public static final int RIGHT_ENCODER_2 = 5;
    //SHOOTER
    /**
     * Winch forward and backward solenoid ports.
     */
    public static final int WINCH_PISTON_1 = 3;
    public static final int WINCH_PISTON_2 = 4;
    /**
     * Winch motor port.
     */
    public static final int WINCH_MOTOR = 4;
    /**
     * Compressor relay port.
     */
    public static final int COMPRESSOR = 1;
    /**
     * Pressure switch port.
     */
    public static final int PRESSURE_SWITCH = 1;
    /**
     * Arm potentiometer port.
     */
    public static final int ARM_POTENTIOMETER = 2;
    //PICKUP    
    /**
     * Pickup arm forward and backward solenoid ports.
     */
    public static final int PICKUP_PISTON_1 = 1;
    public static final int PICKUP_PISTON_2 = 2;
    /**
     * Pickup roller port.
     */
    public static final int PICKUP_ROLLER = 5;
    /**
     * Reed switch port.
     */
    public static final int WINCH_REEDSWITCH = 6;
    // DRIVERSTATION
    /**
     * Autonomous selector port.
     */
    public static final int AUTON_SELECTOR_1 = 1;
    
}
