// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.math.util.Units;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide numerical or boolean
 * constants. This class should not be used for any other purpose. All constants should be declared
 * globally (i.e. public static). Do not put anything functional in this class.
 *
 * <p>It is advised to statically import this class (or one of its inner classes) wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants {

  public static final double scoringDx = Units.inchesToMeters(7);
  public static final double scoringDy = Units.inchesToMeters(18);
  public static final double redCoralDy = Units.inchesToMeters(18);
  public static final double blueCoralDy = Units.inchesToMeters(18);
  public static final double centerDy = Units.inchesToMeters(19);
  public static final double lOneDx = Units.inchesToMeters(1);
  public static final double lOneDy = Units.inchesToMeters(21);
  public static final int blueScoringTagStart = 17;
  public static final int redScoringTagStart = 6;
  public static final int blueHumanPTagStart = 12;
  public static final int redHumanPTagStart = 1;
  public enum ArmPosition {
    // Travel(-1, -.5, -1),
    // L1 (-29.418701171875, -0.8544921875, 21.56298828125),
    // L2(-29.869384765625, -0.8447265625, 1.619140625),
    // L3(-42.489501953125, -6.85302734375, 5.236328125),
    // L4(-52.29443359375, -20.2578125, 13.787109375), // Original: (-51.948974609375, -21.0, 12.2900390625) different rn because the new pvc
    // HighAlgae(-41.728271484375, -5.177734375, 17.84521484375),
    // LowAlgae(-33.37744140625, -0.037109375, 17.7041015625),
    // Ground(0.134521484375, 0, 17.20068359375),
    // GroundP(-26.415283203125, -0.00146484375, 17.28955078125),
    // HumanP(-34.74365234375, 0.0029296875, 12.40283203125),
    // Climb1(-45.785400390625, -4.8828125E-4, 13.1533203125),
    // Climb2(-10.561767578125, -0.00244140625, 17.3291015625),
    // Net(-59.96142578125, -21.3369140625, 21.56298828125),
    // NetP(-59.97314453125, -2.77880859375, 21.56494140625),
    // StartingConfig(-38.552490234375, 0.0068359375, 0.0),
     Manual(-1, -1, -1);

    // public double pivot;
    public double extension;
    public double manipulator;

    public static ArmPosition currentPosition = ArmPosition.Manual;

    ArmPosition(double pivot, double extension, double manipulator) {
        // this.pivot = pivot;
        this.extension = extension;
        this.manipulator = manipulator;
    }

    public static ArmPosition getPosition() {
        return currentPosition;
    }

    public static void setPosition(ArmPosition position) {
        // If trying to go to Ground and previous setpoint was anything near the reef, set it to StartingConfig instead
        // if (Ground.equals(position) && (L1.equals(currentPosition) || L2.equals(currentPosition) || L3.equals(currentPosition) 
        //  || L4.equals(currentPosition) || HighAlgae.equals(currentPosition) || LowAlgae.equals(currentPosition))) {

            // currentPosition = StartingConfig;
            // return;                
        //}

        // if (Net.equals(position) && !NetP.equals(currentPosition)) {

        //     currentPosition = NetP;
        //     return;                
        // } else if (Net.equals(position) && NetP.equals(currentPosition)) {
        //     currentPosition = Net;
        //     return;
        // }

        // if (ArmPosition.Ground.equals(position) && (ArmPosition.L4.equals(currentPosition) || ArmPosition.L3.equals(currentPosition) || ArmPosition.L2.equals(currentPosition) || ArmPosition.L1.equals(currentPosition))) {
        //     return;
        // }

        currentPosition = position;
    }
}

  public static class OperatorConstants {
    public static final int kDriverControllerPort = 0;
  }
}
