/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package frc.team4069.thirdyear.subsystems;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.team4069.thirdyear.RobotMap;

/**
 *
 * @author Almond
 */
public class Pickup extends Subsystem {

    DoubleSolenoid pickupArm;
    Talon pickupRoller;

    public Pickup() {
    pickupArm = new DoubleSolenoid(RobotMap.PICKUP_PISTON_1, RobotMap.PICKUP_PISTON_2);
    pickupRoller = new Talon(RobotMap.PICKUP_ROLLER);
    }
    
    /**
     * Makes the pickup mechanism move..
     *
     * @param speed The speed of movement. Positive for upwards, negative for
     * downwards.
     */
    public void move(boolean up) {
        pickupArm.set(up ? DoubleSolenoid.Value.kReverse :
                DoubleSolenoid.Value.kForward);
    }

    /**
     * Spins the pickup roller.
     *
     * @param speed The speed for spinning. Negative values ignored.
     */
    public void spin(double speed) {
        if (speed < 0) {
            speed = 0;
        }
        pickupRoller.set(speed);
    }

    /**
     * The default command is ReloadCommand. May be unwise - check with team?
     */
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
}
