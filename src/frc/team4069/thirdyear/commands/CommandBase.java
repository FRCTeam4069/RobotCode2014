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

    public static OI oi;
    public static DriveTrain drivetrain;
    public static Shooter shooter;
    public static Pickup pickup;

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

        // Show what command your subsystems are running on the SmartDashboard
        SmartDashboard.putData(drivetrain);
        SmartDashboard.putData(shooter);
    }

    public CommandBase(String name) {
        super(name);
    }

    public CommandBase() {
        super();
    }
}
