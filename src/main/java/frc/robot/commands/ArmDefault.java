package frc.robot.commands;

import edu.wpi.first.math.filter.SlewRateLimiter;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants;
import frc.robot.Constants.ArmPosition;
import frc.robot.subsystems.ElevatorSubsystem;
import frc.robot.subsystems.ManipulatorSubsystem;

public class ArmDefault extends Command {
    public final Joystick operator;
    private final Joystick driver;
    public ElevatorSubsystem elevatorsubsystem;
    //public PivotSubsystem pivotSubsystem;
    private ManipulatorSubsystem manipulatorSubsystem;
    // private SlewRateLimiter elevatorManualSlewRateLimiterUp = new SlewRateLimiter(0.05);
    // private SlewRateLimiter elevatorManualSlewRateLimiterDown = new SlewRateLimiter(0.05);

    public ArmDefault(Joystick driver, Joystick operator) {
        this.driver = driver;
        this.operator = operator;
        this.elevatorsubsystem = ElevatorSubsystem.getInstance();
        // this.pivotSubsystem = PivotSubsystem.getInstance();
        this.manipulatorSubsystem = ManipulatorSubsystem.getInstance();

        addRequirements(elevatorsubsystem);//, manipulatorSubsystem);
    }

    @Override
    public void execute() {

        if (operator.getRawAxis(XboxController.Axis.kRightTrigger.value) > Constants.stickDeadband) {
            elevatorsubsystem.setSetpoint(elevatorsubsystem.getManualSetpoint() - 0.1);
        } else if (operator.getRawAxis(XboxController.Axis.kLeftTrigger.value) > Constants.stickDeadband) {
            elevatorsubsystem.setSetpoint(elevatorsubsystem.getManualSetpoint() + 0.1);
        }

        if (driver.getRawButton(XboxController.Button.kRightBumper.value)) {
            manipulatorSubsystem.setIntakeSpeed(0.2);
        }
        else if (driver.getRawButton(XboxController.Button.kLeftBumper.value)) {
            manipulatorSubsystem.setIntakeSpeed(-0.2);
        }
        else {
            manipulatorSubsystem.setIntakeSpeed(0);
        }

        if (operator.getRawAxis(XboxController.Axis.kRightX.value) > Constants.stickDeadband) {
            manipulatorSubsystem.setSetpoint(manipulatorSubsystem.getManualSetpoint() - .1);
        } else if (operator.getRawAxis(XboxController.Axis.kRightX.value) < -Constants.stickDeadband) {
            manipulatorSubsystem.setSetpoint(manipulatorSubsystem.getManualSetpoint() + .1);
        }

        if (operator.getRawButton(XboxController.Button.kBack.value)) {
            System.out.println("(" + elevatorsubsystem.getExtensionEncoder()
                    + ", " + manipulatorSubsystem.getWristEncoder() + ")");
        }

        // if (operator.getRawAxis(XboxController.Axis.kLeftY.value) < -Constants.stickDeadband) {
        //     pivotSubsystem.setSetpoint(pivotSubsystem.getManualSetpoint() - 0.2);
        // } else if (operator.getRawAxis(XboxController.Axis.kLeftY.value) > Constants.stickDeadband) {
        //     pivotSubsystem.setSetpoint(pivotSubsystem.getManualSetpoint() + 0.2);
        // }

        // if (PivotSubsystem.getStartingDelay() >= 50) {
        //     pivotSubsystem.setPosition();
        // } else {
        //     // ArmPosition.setPosition(ArmPosition.StartingConfig);
        // }

        //pivotSubsystem.setPosition();

        elevatorsubsystem.setPosition();

        manipulatorSubsystem.setPosition();

        // if (!ArmPosition.Manual.equals(manipulatorSubsystem.getArmPosition())) {
        // manipulatorSubsystem.setPosition(ArmPosition.Travel);
        // }
    }
}

// }
// }
