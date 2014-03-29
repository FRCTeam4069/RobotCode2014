package frc.team4069.thirdyear.commands;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.team4069.thirdyear.utils.LowPassFilter;
import java.util.Date;

/**
 * Command that moves the robot when the target is detected.
 *
 * @author Edmund
 */
public class AutonomousMobilityCommand extends CommandBase {

    private final DriverStation m_ds;
    private Date startTime;
    
    public AutonomousMobilityCommand() {
        requires(drivetrain);
        requires(shooter);
        requires(pickup);
        m_ds = DriverStation.getInstance();
        startTime = new Date();
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
        drivetrain.brake();
        startTime = new Date();
    }

    /**
     * Drives forward. Used for non-shooting autonomous to get mobility points.
     */
    protected void execute() {
        if (new Date().getTime() - startTime.getTime() > 1.5 * DELAY) {
            drivetrain.brake();
        } else if (new Date().getTime() - startTime.getTime() > DELAY) {
            drivetrain.tankDrive(0.7, 0.7);
        }
    }
    public static final int DELAY = 4000;

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return !m_ds.isAutonomous();
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
