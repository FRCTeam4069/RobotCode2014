/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package frc.team4069.thirdyear.commands;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import frc.team4069.thirdyear.commands.CommandBase;

/**
 *
 * @author Almond
 */
class CalibrateCommand extends CommandBase {

  
    private DriverStation m_ds;
    
    private boolean hasLockOn = false;
    double blobCountFiltered;

    public CalibrateCommand() {
        requires(drivetrain);
        m_ds = DriverStation.getInstance();
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
        drivetrain.brake();
    }
    
    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        drivetrain.tankDrive(1, 1);
        Timer.delay(3);
        drivetrain.brake();
        Timer.delay(2);
        drivetrain.tankDrive(-1,-1);
        Timer.delay(3);
        drivetrain.brake();
        Timer.delay(3);
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
