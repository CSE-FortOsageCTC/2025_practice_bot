package frc.robot.subsystems;

import com.ctre.phoenix6.hardware.TalonFX;

//These are a maybe, don't know if build is using the sensor or not.
// import com.reduxrobotics.sensors.canandcolor.Canandcolor;
// import com.reduxrobotics.sensors.canandcolor.CanandcolorSettings;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.math.trajectory.TrapezoidProfile;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

//don't have these values yet
//import frc.robot.Constants.ArmPosition;

public class ManipulatorSubsystem extends SubsystemBase{

    private static TalonFX manipulatorIntake;
    private static TalonFX manipulatorWrist;

    //private Canandcolor canandcolor;

    private static ManipulatorSubsystem manipulatorSubsystem;

    //need values
    //private static ArmPosition lastPosition;

    private static ProfiledPIDController pidController;

    //private CanandcolorSettings settings = new CanandcolorSettings();

    private ManipulatorSubsystem() {

        //We still need IDs
        manipulatorIntake = new TalonFX(13);
        manipulatorWrist = new TalonFX(9);

        pidController = new ProfiledPIDController(0, 0, 0, new TrapezoidProfile.Constraints(70, 70));
        pidController.setTolerance(0.1);

        //canandcolor = new Canandcolor(30);
        
        //LEDs
        //settings.setLampLEDBrightness(0.1);
        
        //canandcolor.setSettings(settings);
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

    //Need values
    // public void setPosition(ArmPosition position){
    //     if (position != lastPosition) {
    //         pidController.reset(getWristEncoder());
    //     }
        
    //     double calculation = MathUtil.clamp(pidController.calculate(getWristEncoder(), position.telescope), -1, 1);
    //     manipulatorWrist.set(calculation);
    //     SmartDashboard.putNumber("PID Output", calculation);
    //     lastPosition = position;
    // }

    private double getWristEncoder(){
        //this value is initially a "StatusSignal<Angle>" so got it as a double
        return manipulatorWrist.getPosition().getValueAsDouble();
    }

    // public ArmPosition getArmPosition() {
    //     return lastPosition;
    // }


    //EVERYTHING BELOW HERE IS CANANDCOLOR STUFF

    // public double getProximity() {
    //     return canandcolor.getProximity();
    // }

    // public Double getHSVHue() {
    //     return canandcolor.getHSVHue();
    // }

    // public Double getHSVSaturation() {
    //     return canandcolor.getHSVSaturation();
    // }

    // public Double getHSVValue() {
    //     return canandcolor.getHSVValue();
    // }

    // @Override
    // public void periodic(){
    //     SmartDashboard.putNumber("CAC Proximity", getProximity());
    //     SmartDashboard.putNumber("CAC Hue", getHSVHue());
    //     SmartDashboard.putNumber("CAC Saturation", getHSVSaturation());
    //     SmartDashboard.putNumber("CAC Value", getHSVValue());

    //     pidController.setP(0.2);
    // }
}