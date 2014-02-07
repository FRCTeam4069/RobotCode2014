package frc.team4069.thirdyear.commands;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.networktables.NetworkTable;

/**
 * Command that moves the robot when the target is detected.
 *
 * @author Edmund
 */
public class AutonomousCommand extends CommandBase {

    protected NetworkTable roborealmTable;
    private DriverStation m_ds;
    
    private boolean hasLockOn = false;
    double blobCountFiltered;

    public AutonomousCommand() {
        requires(drivetrain);
        requires(shooter);
        m_ds = DriverStation.getInstance();
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
        roborealmTable = NetworkTable.getTable("SmartDashboard");
        drivetrain.brake();
        blobCountFiltered = 0;
        hasLockOn = false;
    }
    
    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        double blobCount;
        blobCount = ((Double) roborealmTable.getValue("BLOB_COUNT", Double.valueOf(0))).doubleValue();
        blobCountFiltered = blobCount * 0.3 + blobCountFiltered * 0.7;
        if (blobCountFiltered > 0.5) {
            hasLockOn = true;
        }
        if (hasLockOn) {
            drivetrain.tankDrive(1, 1);
        } else {
            drivetrain.brake();
        }
    }

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
