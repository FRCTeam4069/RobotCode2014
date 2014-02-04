package frc.team4069.thirdyear.subsystems;

import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import frc.team4069.thirdyear.RobotMap;

/**
 * Drivetrain subsystem.
 * @author Edmund
 */
public class DriveTrain extends Subsystem {

    private Talon leftMotor;
    private Talon rightMotor;

    public DriveTrain() {
        super();
        leftMotor = new Talon(RobotMap.LEFT_MOTOR);
        rightMotor = new Talon(RobotMap.RIGHT_MOTOR);
    }
    
    /**
     * Adds motors to the live window for use during test mode.
     */
    
    private void initLiveWindow() {
        LiveWindow.addActuator("Drivetrain", "Left Motors", leftMotor);
        LiveWindow.addActuator("Drivetrain", "Right Motors", rightMotor);
    }

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }

    /**
     * Tank drive: makes the robot move.
     *
     * @param left Speed for the left motors.
     * @param right Speed for the right motors.
     */
    public void tankDrive(double left, double right) {
        leftMotor.set(left);
        rightMotor.set(right);
    }

    /**
     * Stops the robot. May make use of encoder-based PID, for now it just sets
     * speed to zero.
     */
    public void brake() {
        leftMotor.set(0);
        rightMotor.set(0);
    }
}
