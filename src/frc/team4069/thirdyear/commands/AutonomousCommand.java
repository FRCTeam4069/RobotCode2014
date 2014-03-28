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
public class AutonomousCommand extends CommandBase {

    protected NetworkTable roborealmTable;
    private DriverStation m_ds;
    private boolean hasLockOn;
    private boolean inPosition;
    private boolean hotAtStart;
    private Date lockonTime;
    private Date inPositionTime;
    private Date startTime = new Date();
    private LowPassFilter blobFilter = new LowPassFilter(200);
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
        hotAtStart = false;
        startTime = new Date();
        drivetrain.resetDistance();
    }

    /**
     * Checks for the vision target, drives to the low goal and shoots into the
     * high goal. Future implementations may forgo checking for the vision
     * target first, because if it isn't triggered at the start it will be in
     * the next five seconds.
     */
    protected void execute() {
        System.out.println(shooter.getPotentiometerAngle());
        pickup.move(false);
        double blobCount = 0.0;
        blobCount = blobFilter.calculate(((Double) roborealmTable.getValue("BLOB_COUNT",
                Double.valueOf(0))).doubleValue());
        if (blobCount > 0.8) {
            hotAtStart = true;
        }
        if (!hasLockOn && !inPosition && (hotAtStart || new Date().getTime() - startTime.getTime() > 1800)) {
            hasLockOn = true;
            lockonTime = new Date();
        } else if (inPosition) {
            shootLoop();
        } else if (hasLockOn) {
            driveLoop();
        } else {
            shooter.moveToShootingAngle();
            drivetrain.brake();
        }
    }

    /**
     * Runs at the beginning of the autonomous loop. Drives the robot to the low
     * goal.
     */
    private void driveLoop() {
        if (drivetrain.getDistance() < GOAL_DIST * 0.73) {
            drivetrain.arcadeControlledDrive(0.6, 0);
            shooter.moveToShootingAngle();
        } else if (drivetrain.getDistance() < GOAL_DIST * 0.91) {
            shooter.moveToShootingAngle();
            drivetrain.arcadeControlledDrive(0.25, 0);
        } else if (drivetrain.getDistance() < GOAL_DIST) {
            shooter.moveToShootingAngle();
            drivetrain.arcadeControlledDrive(0.2, 0);
        }
        if (drivetrain.getDistance() > GOAL_DIST
                || (Math.abs(drivetrain.getSpeed()) < 1.0
                && new Date().getTime() - lockonTime.getTime() > 1000)) {
            //drivetrain.arcadeControlledDrive(0.05, 0);
            shooter.spinWinch(0);
            hasLockOn = false;
            inPosition = true;
            inPositionTime = new Date();
        }
    }

    /**
     * Runs when the robot hass detected that it is at the low goal.
     */
    private void shootLoop() {
        drivetrain.brake();
//        if (new Date().getTime() - inPositionTime.getTime() > 1000) {
//            pickup.move(false);
//        }
//        if (new Date().getTime() - inPositionTime.getTime() > 200 && hotAtStart) {
//            shooter.fireSolenoid(true);
//        } else
        if (new Date().getTime() - inPositionTime.getTime() > 3000) {
            shooter.fireSolenoid(false);
            shooter.moveToShootingAngle();
        } else if (new Date().getTime() - inPositionTime.getTime() > 2000) {
            shooter.spinWinch(0);
            shooter.fireSolenoid(true);
        } else {
            shooter.moveToShootingAngle();
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
