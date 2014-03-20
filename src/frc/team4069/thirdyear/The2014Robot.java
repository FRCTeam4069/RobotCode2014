/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/
package frc.team4069.thirdyear;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import frc.team4069.thirdyear.commands.CommandBase;
import frc.team4069.thirdyear.commands.AutonomousCommand;
import frc.team4069.thirdyear.commands.DriveCommand;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class The2014Robot extends IterativeRobot {

    private Command autonomousCommand;
    private Command driveCommand;

    /**
     * Run when the robot is first started up and used for
     * initialization code.
     */
    public void robotInit() {
        CommandBase.init();
        autonomousCommand = new AutonomousCommand();
        driveCommand = new DriveCommand();
    }

    /**
     * Called when autonomous control begins.
     * It schedules an instance of {@link AutonomousCommand}.
     */
    public void autonomousInit() {
        autonomousCommand.start();
        driveCommand.cancel();
    }

    /**
     * Called periodically during autonomous.
     */
    public/*Potatoes*/ void autonomousPeriodic() {
        Scheduler.getInstance().run();
    }

    /**
     * Called when operator control begins.
     * It schedules an instance of {@link DriveCommand}.
     */
    public void teleopInit() {
        autonomousCommand.cancel();
        driveCommand.start();
    }

    /**
     * Called periodically during operator control.
     */
    public void teleopPeriodic() {
        Scheduler.getInstance().run();
    }

    /**
     * This function is called to initialize test mode
     */
    public void testInit() {
    }

    /**
     * This function is called periodically during test mode.
     * Currently does nothing.
     */
    public void testPeriodic() {
        Scheduler.getInstance().run();
        //  LiveWindow.run();
    }

    public void disabledInit() {
        autonomousCommand.cancel();
        driveCommand.cancel();
    }
}
