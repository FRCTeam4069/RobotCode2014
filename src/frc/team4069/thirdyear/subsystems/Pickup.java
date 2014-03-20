/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package frc.team4069.thirdyear.subsystems;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.tables.ITable;
import frc.team4069.thirdyear.RobotMap;

/**
 * Pickup system. Includes roller motor and arm solenoid. Batteries not
 * included.
 *
 * @author Edmund
 */
public class Pickup extends Subsystem {

    DoubleSolenoid pickupArm;
    Talon pickupRoller;
    DigitalInput reedSwitch;

    public Pickup() {
        pickupArm = new DoubleSolenoid(RobotMap.PICKUP_PISTON_1,
                RobotMap.PICKUP_PISTON_2);
        pickupRoller = new Talon(RobotMap.PICKUP_ROLLER);
        reedSwitch = new DigitalInput(RobotMap.WINCH_REEDSWITCH);
    }

    /**
     * Makes the pickup mechanism move.
     *
     * @param up The direction of movement. True for upward, false for downward.
     */
    public void move(boolean up) {
        pickupArm.set(up ? DoubleSolenoid.Value.kReverse
                : DoubleSolenoid.Value.kForward);
    }

    public void toggle() {
        pickupArm.set(
                pickupArm.get() == DoubleSolenoid.Value.kForward ? DoubleSolenoid.Value.kReverse : DoubleSolenoid.Value.kForward);
    }

    /**
     * Spins the pickup roller.
     *
     * @param speed The speed for spinning.
     */
    public void spin(double speed) {

        pickupRoller.set(speed);
    }

    /**
     * Checks if the pickup arm is in the correct position for shooter usage.
     *
     * @return The value of the reed switch on the pickup arm.
     */
    public boolean getReedSwitch() {
        return reedSwitch.get();
    }

    /**
     * There is <b>no</b> default command.
     */
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }

    /**
     * Adds motor and sensor data to the SmartDashboard.
     *
     * @return The NetworkTable instance containing the shooter data.
     */
    public ITable getTable() {
        NetworkTable shooterTable;
        shooterTable = NetworkTable.getTable("Pickup");
        shooterTable.putBoolean("Reed Switch", getReedSwitch());
        shooterTable.putNumber("Roller Speed", pickupRoller.get());
        return shooterTable;
    }

    /**
     *
     * @return The name of the NamedSendable.
     */
    public String getName() {
        return "Pickup";
    }
}
