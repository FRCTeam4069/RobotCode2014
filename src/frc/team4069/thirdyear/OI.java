package frc.team4069.thirdyear;

import frc.team4069.thirdyear.commands.ShootCommand;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import frc.team4069.thirdyear.commands.ReloadCommand;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 *
 * @author Edmund
 */
public class OI {


    /* Joystick Ports */
    private static final int DRIVE_STICK_PORT = 1;
    private static final int SHOOT_STICK_PORT = 2;
    /* Joystick Objects */
    private Joystick driveStick;
    private Joystick shootStick;

    public OI() {
        driveStick = new Joystick(DRIVE_STICK_PORT);
        shootStick = new Joystick(SHOOT_STICK_PORT);
        Button shootButton = new JoystickButton(shootStick, 1);
        shootButton.whenPressed(new ShootCommand());
        shootButton.whenReleased(new ReloadCommand());
    }

    /* Getters */
    public Joystick getDriveStick() {
        return driveStick;
    }

    public Joystick getShootStick() {
        return shootStick;
    }
}
