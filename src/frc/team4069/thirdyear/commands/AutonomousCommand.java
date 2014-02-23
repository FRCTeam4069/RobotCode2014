package frc.team4069.thirdyear.commands;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import java.util.Date;

/**
 * Command that moves the robot when the target is detected.
 *
 * @author Edmund
 */
public class AutonomousCommand extends CommandBase {

    protected NetworkTable roborealmTable;
    private DriverStation m_ds;
    private boolean hasLockOn;
    private boolean inPosition;
    private Date lockonTime;
    private Date inPositionTime;
    double blobCountFiltered;
    private static final double GOAL_DIST = 15 * 12;

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
        inPosition = false;
        drivetrain.resetDistance();
        Timer.delay(0.1);
    }

    /**
     * Checks for the vision target, drives to the low goal and shoots into the
     * high goal.
     * Future implementations may forgo checking for the vision target first,
     * because if it isn't triggered at the start it will be in the next five
     * seconds.
     */
    protected void execute() {
        pickup.move(false);
        double blobCount;
        blobCount = ((Double) roborealmTable.getValue("BLOB_COUNT",
                Double.valueOf(0))).doubleValue();
        blobCountFiltered = blobCount * 0.3 + blobCountFiltered * 0.7;
        if (blobCountFiltered > 0.5 && !hasLockOn) {
            hasLockOn = true;
            lockonTime = new Date();
        }
        if (hasLockOn) {
            driveLoop();
        } else if (inPosition) {
            shootLoop();
        } else {
            drivetrain.brake();
        }
        System.out.println("Speed " + drivetrain.getSpeed());
    }

    /**
     * Runs at the beginning of the autonomous loop. Drives the robot to the low
     * goal.
     */
    private void driveLoop() {
        if (drivetrain.getDistance() < GOAL_DIST * 0.73) {
            drivetrain.arcadeControlledDrive(-0.7, 0);
        } else if (drivetrain.getDistance() < GOAL_DIST * 0.91) {
            drivetrain.arcadeControlledDrive(-0.25, 0);
        } else if (drivetrain.getDistance() < GOAL_DIST) {
            shooter.moveToShootingAngle();
            drivetrain.arcadeControlledDrive(-0.25, 0);
        }
        if (drivetrain.getDistance() > GOAL_DIST
                || (Math.abs(drivetrain.getSpeed()) < 1.0
                && new Date().getTime() - lockonTime.getTime() > 1000)) {
            drivetrain.arcadeControlledDrive(0.05, 0);
            shooter.spinWinch(0);
            hasLockOn = false;
            inPosition = true;
            inPositionTime = new Date();
        }
    }

    /**
     * Runs when the robot has detected that it is at the low goal.
     */
    private void shootLoop() {
        drivetrain.brake();
        if (new Date().getTime() - inPositionTime.getTime() > 200) {
            shooter.fireSolenoid(true);
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
