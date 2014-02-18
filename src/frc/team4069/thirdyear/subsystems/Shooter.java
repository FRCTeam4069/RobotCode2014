package frc.team4069.thirdyear.subsystems;

import edu.wpi.first.wpilibj.AnalogChannel;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.team4069.thirdyear.RobotMap;
import frc.team4069.thirdyear.commands.ReloadCommand;

/**
 * No idea how to implement this yet. Stay tuned.
 *
 * @author Edmund
 */
public class Shooter extends Subsystem {
    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    DoubleSolenoid transmissionSolenoid;
    Talon winchTalon;
    AnalogChannel armPotentiometer;
    DigitalInput reedSwitch;
    Compressor compressor;
    private static final double SHOOTING_ANGLE = 127.73;
    
    public Shooter() {
        transmissionSolenoid = new DoubleSolenoid(RobotMap.WINCH_PISTON_1,
                RobotMap.WINCH_PISTON_2);
        winchTalon = new Talon(RobotMap.WINCH_MOTOR);
        armPotentiometer = new AnalogChannel(RobotMap.ARM_POTENTIOMETER);
        armPotentiometer.setOversampleBits(5);
        compressor = new Compressor(RobotMap.PRESSURE_SWITCH,
                RobotMap.COMPRESSOR);
        compressor.start();
        reedSwitch = new DigitalInput(RobotMap.WINCH_REEDSWITCH);
    }

    /**
     * Makes the shooty thing go pow.
     *
     * @param forward If true, disengages the transmission and fires. If false,
     *                re-engages the transmission.
     */
    public void fireSolenoid(boolean forward) {
        transmissionSolenoid.set(forward ? DoubleSolenoid.Value.kForward :
                DoubleSolenoid.Value.kReverse);
    }
    /**
     * By convention, positive speed tightens the winch,
     * negative speed releases it.
     * Don't do anything else.
     *
     * @param speed
     */
    private static final double MAX_ANGLE = 135;
    private static final double MIN_ANGLE = 96;
    
    public void spinWinch(double speed) {
        if ((getPotentiometerAngle() < MAX_ANGLE && speed >= 0)
                || (getPotentiometerAngle() > MIN_ANGLE && speed <= 0)) {
            winchTalon.set(speed);
        } else {
            winchTalon.set(0);
        }
//        if (!reedSwitch.get()) {
//            winchTalon.set(speed);
//        } else {
//            winchTalon.set(0);
//        }
    }
    private static final double POT_VALUE_AT_ZERO_VOLTS = -547.0336620966;
    private static final double POT_VALUE_SLOPE = 366.3256030533;
    
    public double getPotentiometerAngle() {
        double voltage = armPotentiometer.getAverageVoltage();
        double value = POT_VALUE_AT_ZERO_VOLTS + voltage * POT_VALUE_SLOPE;
        return value;
    }

    /**
     * The default command is ReloadCommand. May be unwise - check with team?
     */
    public void initDefaultCommand() {
        setDefaultCommand(new ReloadCommand());
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
    
    public boolean getReedSwitch() {
        return reedSwitch.get();
    }
    
    public boolean isArmReady() {
        return getPotentiometerAngle() >= MAX_ANGLE;
    }
    
    public void moveToShootingAngle() {
        winchTalon.set(0.08 * (-getPotentiometerAngle() + SHOOTING_ANGLE));
    }
}
