package frc.team4069.thirdyear.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.team4069.thirdyear.OI;
import frc.team4069.thirdyear.subsystems.DriveTrain;
import frc.team4069.thirdyear.subsystems.Pickup;
import frc.team4069.thirdyear.subsystems.Shooter;

/**
 * The base for all commands. All atomic commands should subclass CommandBase.
 * CommandBase stores creates and stores each control system. To access a
 * subsystem elsewhere in your code in your code use
 * CommandBase.exampleSubsystem
 *
 * @author Author
 */
public abstract class CommandBase extends Command {

    /**
     * Operator interface instance.
     */
    public static OI oi;
    /**
     * Drivetrain instance.
     */
    public static DriveTrain drivetrain;
    /**
     * Shooter instance.
     */
    public static Shooter shooter;
    /**
     * Pickup instance.
     */
    public static Pickup pickup;

    /**
     * Initializes subsystems. Call first during robotInit().
     */
    public static void init() {
        drivetrain = new DriveTrain();
        shooter = new Shooter();
        pickup = new Pickup();
        // This MUST be here. If the OI creates Commands (which it very likely
        // will), constructing it during the construction of CommandBase (from
        // which commands extend), subsystems are not guaranteed to be
        // yet. Thus, their requires() statements may grab null pointers. Bad
        // news. Don't move it.
        oi = new OI();
    }

    public CommandBase(String name) {
        super(name);
    }

    public CommandBase() {
        super();
    }
}
