package frc.team4069.thirdyear.commands;

import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.networktables2.type.StringArray;

/**
 * Command that moves the robot when the target is detected.
 *
 * @author Edmund
 */
public class AutonomousCommand extends CommandBase {

    protected NetworkTable roborealmTable;
    private boolean hasLockOn = false;

    public AutonomousCommand() {
        requires(drivetrain);
        requires(shooter);
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
        roborealmTable = NetworkTable.getTable("ROBOREALM");
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        StringArray blobs = new StringArray();
        roborealmTable.retrieveValue("BLOBS", blobs);
        if (blobs.size() == 0) {
            hasLockOn = false;
        } else {
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
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
