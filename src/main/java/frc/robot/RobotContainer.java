// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.XboxController.Button;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.Constants.ElevatorConstants;
import frc.robot.Constants.OIConstants;
import edu.wpi.first.wpilibj.Joystick;
import frc.robot.commands.JoystickCommand;
import frc.robot.commands.auto.SCurve;
import frc.robot.subsystems.Elevator;
import frc.robot.subsystems.SwerveSubsystem;

/**
 * This class is where the bulk of the robot should be declared. Since
 * Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in
 * the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of
 * the robot (including
 * subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {

  private final SwerveSubsystem swerveSubsystem = new SwerveSubsystem();
  private final Elevator elevator = new Elevator();
  private final Joystick driverJoystick = new Joystick(OIConstants.kDriverControllerPort);
  private final Joystick operatorJoystick = new Joystick(OIConstants.kOperatorControllerPort);
  boolean fieldRelative = true;

  // The robot's subsystems and commands are defined here...

  /**
   * The container for the robot. Contains subsystems, OI devices, and commands.
   */
  public RobotContainer() {
    swerveSubsystem.setDefaultCommand(new JoystickCommand(
        swerveSubsystem,
        () -> -driverJoystick.getRawAxis(OIConstants.kDriverYAxis),
        () -> driverJoystick.getRawAxis(OIConstants.kDriverXAxis),
        () -> driverJoystick.getRawAxis(OIConstants.kDriverRotAxis), 
        fieldRelative));

    configureButtonBindings();
  }

  /**
   * Use this method to define your button->command mappings. Buttons can be
   * created by
   * instantiating a {@link GenericHID} or one of its subclasses ({@link
   * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing
   * it to a {@link
   * edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  private void configureButtonBindings() {
    new JoystickButton(driverJoystick, Button.kRightBumper.value)
    .whenHeld(new InstantCommand(() -> fieldRelative = false))
    .whenReleased(new InstantCommand(() -> fieldRelative = true));

    new JoystickButton(operatorJoystick, Button.kA.value)
    .whenHeld(elevator.setPosition(ElevatorConstants.kLevelOnePosition));

    new JoystickButton(operatorJoystick, Button.kB.value)
    .whenHeld(elevator.setPosition(ElevatorConstants.kLevelTwoPosition));

    new JoystickButton(operatorJoystick, Button.kX.value)
    .whenHeld(elevator.setPosition(ElevatorConstants.kLevelThreePosition));

    new JoystickButton(operatorJoystick, Button.kY.value)
    .whenHeld(elevator.setPosition(ElevatorConstants.kCargoShipPosition));

}
  

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getSCurveAuto() {
    return new SCurve(swerveSubsystem);
  }
}
