
package frc.team4069.thirdyear.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import frc.team4069.thirdyear.commands.ReloadCommand;

    /**
     * No idea how to implement this yet.
     * Stay tuned.
     * @author Edmund
     */
public class Shooter extends Subsystem {
    // Put methods for controlling this subsystem
    // here. Call these from Commands.
    
    /**
     * Makes the shooty thing go pow.
     * @param speed The speed of pow. Will almost definitely not be applicable.
     */
    private void shoot(double speed) {
        
    }
    
    /**
     * The default command is ReloadCommand.
     * May be unwise - check with team?
     */
    public void initDefaultCommand() {
        setDefaultCommand(new ReloadCommand());
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
}

