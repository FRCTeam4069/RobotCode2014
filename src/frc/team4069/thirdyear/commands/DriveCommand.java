/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package frc.team4069.thirdyear.commands;

import com.sun.squawk.util.MathUtils;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.Command;
import frc.team4069.thirdyear.utils.LowPassFilter;
import java.util.Date;

/**
 * The command that runs during operator control.
 *
 * @author Edmund
 */
public class DriveCommand extends CommandBase {

    private DriverStation m_ds;
    private Joystick driveStick;
    private static final int FIR_LENGTH = 5;
    private double lastLeftStickX;
    private double lastLeftStickY;

    /**
     * Constructor.
     */
    public DriveCommand() {
        requires(drivetrain);
        requires(pickup);
        requires(shooter);
        lastLeftStickX = 0;
        lastLeftStickY = 0;
        m_ds = DriverStation.getInstance();
        driveStick = oi.getDriveStick();
        xFilter = new LowPassFilter(20);
        yFilter = new LowPassFilter(5);
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    }

    /**
     * Called at the beginning of operator control. Makes the drivetrain brake.
     */
    protected void initialize() {
        drivetrain.brake();
        lastPickupButton = false;
    }
    private boolean lastPickupButton;
    LowPassFilter xFilter;
    LowPassFilter yFilter;

    /**
     * Periodically called during operator control.
     */
    protected void execute() {
        double currentLeftStickX = (driveStick.getRawAxis(1)) * Math.abs(
                driveStick.getRawAxis(
                        1)) / 1.8;
        double currentLeftStickY = -driveStick.getRawAxis(3);
        currentLeftStickX = xFilter.calculate(currentLeftStickX);
        currentLeftStickY = yFilter.calculate(currentLeftStickY);
        //  outputX = currentLeftStickX * 0.07 + lastLeftStickX * 0.93;
        //  outputY = currentLeftStickY * 0.00006 + lastLeftStickY * 0.99994;
        //  lastLeftStickX = outputX;
        //  lastLeftStickY = outputY;
        //drivetrain.arcadeControlledDrive(currentLeftStickY, currentLeftStickX);
        drivetrain.arcadeDrive(currentLeftStickY, currentLeftStickX);
        boolean pickupIsSafe = !pickup.getReedSwitch();
        if (pickupIsSafe) {
            if (driveStick.getRawButton(3)) {
              //  shooter.spinWinch(1);
                shooter.moveToShootingAngle();
            } else if (driveStick.getRawButton(2)) {
                shooter.fireSolenoid(true);
            } else if (driveStick.getRawButton(1)) {
                shooter.spinWinch(-1);
            } else {
                shooter.spinWinch(0);
                shooter.fireSolenoid(false);
            }
        }
        //if (shooter.isArmReady()) {
        if (driveStick.getRawButton(4)) {
            pickup.spin(1);
        } else if (driveStick.getRawButton(8)) {
            pickup.spin(-1);
        } else {
            pickup.spin(0);
        }
        if (driveStick.getRawButton(6)) {
            pickup.move(true);
        } else if (driveStick.getRawButton(5)) {
            pickup.move(false);
        }
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return !m_ds.isOperatorControl();
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
