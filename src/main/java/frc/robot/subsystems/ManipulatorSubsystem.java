package frc.robot.subsystems;

import com.ctre.phoenix6.Orchestra;
import com.ctre.phoenix6.configs.AudioConfigs;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.NeutralModeValue;

//These are a maybe, don't know if build is using the sensor or not.
// import com.reduxrobotics.sensors.canandcolor.Canandcolor;
// import com.reduxrobotics.sensors.canandcolor.CanandcolorSettings;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.math.trajectory.TrapezoidProfile;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.Constants.ArmPosition;

//don't have these values yet
//import frc.robot.Constants.ArmPosition;

public class ManipulatorSubsystem extends SubsystemBase{

    private static TalonFX manipulatorIntake;
    private static TalonFX manipulatorWrist;

    //private Canandcolor canandcolor;

    private static ManipulatorSubsystem manipulatorSubsystem;

    //need values
    private static ArmPosition lastPosition;

    private double manualSetpoint;

    private double setpoint;

    private static ProfiledPIDController pidController;

    //private CanandcolorSettings settings = new CanandcolorSettings();

    private ManipulatorSubsystem() {

        
        manipulatorIntake = new TalonFX(13);
        manipulatorWrist = new TalonFX(9);

        pidController = new ProfiledPIDController(.2, 0, 0, new TrapezoidProfile.Constraints(200, 200));
        pidController.setTolerance(0.1);

        AudioConfigs audioConfigs = new AudioConfigs().withAllowMusicDurDisable(true);
        manipulatorWrist.getConfigurator().apply(audioConfigs);

        manipulatorWrist.setNeutralMode(NeutralModeValue.Brake);

        manipulatorWrist.setPosition(0);

        manualSetpoint = Constants.wristLowerLimit;
        setpoint = Constants.wristLowerLimit;
    }

    public static ManipulatorSubsystem getInstance(){
        if (manipulatorSubsystem == null) {
            manipulatorSubsystem = new ManipulatorSubsystem();
        }

        return manipulatorSubsystem;
    }

    public void setWristSpeed(double speed){
        manipulatorWrist.set(speed);
        
        //Need value
        //lastPosition = ArmPosition.Manual;
    }
    
    public void setIntakeSpeed(double speed){
        manipulatorIntake.set(speed);
    }

    public void addInstruments(Orchestra orchestra){
        orchestra.addInstrument(manipulatorWrist);

    }

    public void setSetpoint(double setpoint) {
        ArmPosition.setPosition(ArmPosition.Manual);
        lastPosition = ArmPosition.Manual;
        manualSetpoint = MathUtil.clamp(setpoint, Constants.wristLowerLimit, Constants.wristUpperLimit);
        //setPosition();
    }

    private void privSetSpeed(double speed) {

        boolean isPositive = speed > 0;

        if (atLimit(isPositive)) {
            speed = 0;
        }

        manipulatorWrist.set(speed);
    }

    public void setPosition() {
        if (ArmPosition.getPosition() != lastPosition) {
            pidController.reset(getWristEncoder());
        }

        setpoint = ArmPosition.getPosition().manipulator;

        if (setpoint == -1) {
            setpoint = manualSetpoint;
        } else {
            manualSetpoint = getWristEncoder();
        }

        // boolean isDown = extensionSubsystem.getExtensionEncoder() - ArmPosition.getPosition().extension >= 0;

        // if (!ExtensionSubsystem.nearSetpoint() && isDown) {
        //     setpoint = ArmPosition.getPosition().manipulator;
        // } else if (!ExtensionSubsystem.nearSetpoint() && !isDown && ArmPosition.getPosition().manipulator == -1) {
        //     setpoint = manualSetpoint;
        // }

        // if (ExtensionSubsystem.atPosition() || (!ExtensionSubsystem.atPosition() && isDown)) {
        //     double calculation = MathUtil.clamp(pidController.calculate(getWristEncoder(), setpoint), -1, 1);
        //     privSetSpeed(calculation);
        // } else {
        //     privSetSpeed(0);
        // }

        double calculation = MathUtil.clamp(pidController.calculate(getWristEncoder(), setpoint), -.1, .1);
        privSetSpeed(calculation);

        SmartDashboard.putNumber("Wrist PID", calculation);
        SmartDashboard.putNumber("Wrist Set Point", setpoint);
        lastPosition = ArmPosition.getPosition();
    }

    private boolean atLimit(boolean positive) {
        double encoder = getWristEncoder();
        return (positive && encoder >= ( Constants.wristUpperLimit)
            || (!positive && encoder <= Constants.wristLowerLimit));
    }

    public static boolean nearSetpoint() {
        double encoder = manipulatorSubsystem.getWristEncoder();
        return Math.abs(encoder - ArmPosition.getPosition().manipulator) <= 1;
    }

    public static boolean atPosition() {
        return pidController.atSetpoint();
    }

    public double getWristEncoder() {
        return manipulatorWrist.getPosition().getValueAsDouble();
    }

    public ArmPosition getArmPosition() {
        return lastPosition;
    }

    public double getManualSetpoint() {
        return manualSetpoint;
    }

    @Override
    public void periodic() {
        // SmartDashboard.putNumber("Wrist Kraken Encoder", getWristEncoder());
        SmartDashboard.putNumber("Wrist Absolute Encoder", getWristEncoder());
        // SmartDashboard.putNumber("HSV CanAndColor", getHSVHue());
        // pidController.setP(0.3);

        // SmartDashboard.putNumber("Proximity", getProximity());
    }
}