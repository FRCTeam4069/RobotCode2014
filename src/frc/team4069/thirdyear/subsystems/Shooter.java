package frc.team4069.thirdyear.subsystems;

import edu.wpi.first.wpilibj.AnalogChannel;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.tables.ITable;
import frc.team4069.thirdyear.RobotMap;

/**
 * Shooter subsystem incorporating winch, transmission, and safety features.
 *
 * @author Edmund
 */
public class Shooter extends Subsystem {
    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    DoubleSolenoid transmissionSolenoid;
    Talon winchTalon;
    AnalogChannel armPotentiometer;
    Compressor compressor;
    private static final double MAX_ANGLE = 273;//272.5;
    private static final double MIN_ANGLE = 180;
    private static final double SHOOTING_ANGLE = 273;//263.3;//264.0;//271.4;//264.5;//271;
    private static final double WINCH_P = 0.17;

    public Shooter() {
        transmissionSolenoid = new DoubleSolenoid(RobotMap.WINCH_PISTON_1,
                RobotMap.WINCH_PISTON_2);
        winchTalon = new Talon(RobotMap.WINCH_MOTOR);
        armPotentiometer = new AnalogChannel(RobotMap.ARM_POTENTIOMETER);
        armPotentiometer.setOversampleBits(6);
        compressor = new Compressor(RobotMap.PRESSURE_SWITCH,
                RobotMap.COMPRESSOR);
        compressor.start();
    }

    /**
     * Makes the shooty thing go pow.
     *
     * @param forward If true, disengages the transmission and fires. If false,
     * re-engages the transmission.
     */
    public void fireSolenoid(boolean forward) {
        transmissionSolenoid.set(forward ? DoubleSolenoid.Value.kReverse
                : DoubleSolenoid.Value.kForward);
    }

    /**
     * By convention, negative speed tightens the winch, positive speed releases
     * it. Don't do anything else.
     *
     * @param speed
     */
    public void spinWinch(double speed) {
        speed = Math.min(Math.max(speed, -1), 1);
        speed *= 0.8;
        if ((getPotentiometerAngle() < MAX_ANGLE && speed < 0)
                || (getPotentiometerAngle() > MIN_ANGLE && speed > 0)) {
            winchTalon.set(-speed);
        } else {
            winchTalon.set(0);
        }
//        if (!reedSwitch.get()) {
//            winchTalon.set(speed);
//        } else {
//            winchTalon.set(0);
//        }
    }
    private static final double POT_VALUE_AT_ZERO_VOLTS = -547.0336620966 + 60.0;
    private static final double POT_VALUE_SLOPE = 366.3256030533;

    /**
     * Gets the angle of the shooter arm potentiometer from the vertical. Scales
     * the voltage according to the constants above. DO NOT change these
     * constants; angular constants such as {@link Shooter#SHOOTING_ANGLE }
     * depend on them.
     *
     * @return The angle in degrees of the shooter arm.
     */
    public double getPotentiometerAngle() {
        double voltage = armPotentiometer.getAverageVoltage();
        double value = POT_VALUE_AT_ZERO_VOLTS + voltage * POT_VALUE_SLOPE;
        return value;
    }

    /**
     * The default command is ReloadCommand. May be unwise - check with team?
     */
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }

    /**
     * Checks if the shooter arm is below or at the maximum angle.
     *
     * @return If the arm is ready to receive a ball.
     */
    public boolean isArmReady() {
        return getPotentiometerAngle() >= MAX_ANGLE;
    }

    /**
     * Moves the winch to the shooting angle using proportional control. Makes
     * use of {@link Shooter#moveToAngle(double)}.
     */
    public boolean moveToShootingAngle() {
//        boolean atTarget = Math.abs(getPotentiometerAngle() - SHOOTING_ANGLE) < 1;
//        if (atTarget) {
           return moveToAngle(SHOOTING_ANGLE);
//        }
//        return atTarget;

    }

    /**
     * Moves the winch to the desired angle using proportional control. Uses
     * {@link Shooter#getPotentiometerAngle()}.
     *
     * @param angle The desired angle measured from the vertical.
     */
    public boolean moveToAngle(double angle) {
        if (Math.abs(getPotentiometerAngle() - angle) < 0.5) {
            return true;
        }

        spinWinch(-WINCH_P * (-angle + getPotentiometerAngle()));
        return false;
    }

    /**
     * Adds motor and sensor data to the SmartDashboard.
     *
     * @return The NetworkTable instance containing the shooter data.
     */
    public ITable getTable() {
        NetworkTable shooterTable;
        shooterTable = NetworkTable.getTable("Shooter");
        shooterTable.putNumber("Shooter Angle", getPotentiometerAngle());
        shooterTable.putNumber("Winch Speed", winchTalon.get());
        shooterTable.putBoolean("Solenoid Status", transmissionSolenoid.get()
                == DoubleSolenoid.Value.kForward);
        shooterTable.putBoolean("Compressor Pressure Switch",
                compressor.getPressureSwitchValue());
        return shooterTable;
    }

}
