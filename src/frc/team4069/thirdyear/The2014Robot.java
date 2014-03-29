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
import frc.team4069.thirdyear.commands.AutonomousMobilityCommand;
import frc.team4069.thirdyear.commands.DriveCommand;

/**
 * So, at the end of build season I still had no idea how command-based 
 * robots were programmed. They sounded cool, and possibly good for asynchronous
 * tasks, so I included commands and subsystems for no reason. 
 * 
 * After seeing command-based robot projects at competition easily 
 * exceeding 30 files and 800 lines of code, and seeing people try to
 * debug them, I decided that it was a good thing I didn't know how to use
 * commands properly earlier.
 * 
 * TODO: Remove commands and subsystems from this robot, asap.
 */
public class The2014Robot extends IterativeRobot {

    private Command autonomousCommand;
    private Command autonMobilityCommand;
    private Command driveCommand;

    /**
     * Run when the robot is first started up and used for initialization code.
     */
    public void robotInit() {
        CommandBase.init();
        autonMobilityCommand = new AutonomousMobilityCommand();
        autonomousCommand = new AutonomousCommand();
        driveCommand = new DriveCommand();
    }

    /**
     * Called when autonomous control begins. It schedules an instance of
     * {@link AutonomousCommand}.
     */
    public void autonomousInit() {
        if (m_ds.getDigitalIn(RobotMap.AUTON_SELECTOR_1)) {
            autonMobilityCommand.start();
        } else {
            autonomousCommand.start();
        }
        driveCommand.cancel();
    }

    /**
     * Called periodically during autonomous.
     */
    public void autonomousPeriodic() {
        Scheduler.getInstance().run();
    }

    /**
     * Called when operator control begins. It schedules an instance of
     * {@link DriveCommand}.
     */
    public void teleopInit() {
        autonomousCommand.cancel();
        autonMobilityCommand.cancel();
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
     * This function is called periodically during test mode. Currently does
     * nothing.
     */
    public void testPeriodic() {
        Scheduler.getInstance().run();
        //  LiveWindow.run();
    }

    public void disabledInit() {
        autonMobilityCommand.cancel();
        autonomousCommand.cancel();
        driveCommand.cancel();
    }
}
