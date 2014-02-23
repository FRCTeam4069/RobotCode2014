package frc.team4069.thirdyear;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 *
 * @author Edmund
 */
public class OI {

    /**
     * Drive joystick port.
     */
    private static final int DRIVE_STICK_PORT = 1;
    /**
     * Shoot joystick port.
     */
    private static final int SHOOT_STICK_PORT = 2;
    /**
     * Driving joystick.
     */
    private Joystick driveStick;
    /**
     * Shooting joystick.
     */
    private Joystick shootStick;

    public OI() {
        driveStick = new Joystick(DRIVE_STICK_PORT);
        shootStick = new Joystick(SHOOT_STICK_PORT);
    }

    /**
     * Gets the driving joystick.
     *
     * @return
     */
    public Joystick getDriveStick() {
        return driveStick;
    }

    /**
     * Gets the shooting joystick.
     * NOTE: This is not currently used.
     *
     * @return
     */
    public Joystick getShootStick() {
        return shootStick;
    }
}
