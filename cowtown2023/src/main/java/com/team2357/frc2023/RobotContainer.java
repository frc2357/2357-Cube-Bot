// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package com.team2357.frc2023;

import com.team2357.frc2023.commands.DefaultDriveCommand;
import com.team2357.frc2023.controls.OperatorControls;
import com.team2357.frc2023.controls.SwerveDriveControls;

import edu.wpi.first.wpilibj2.command.Command;

/**
 * This class is where the bulk of the robot should be declared. Since
 * Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in
 * the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of
 * the robot (including
 * subsystems, commands, and trigger mappings) should be declared here.
 */
public class RobotContainer {
  private AutoCommandChooser m_autoCommandChooser;

  public RobotContainer() {

    SwerveDriveControls driveControls = new SwerveDriveControls(Constants.CONTROLLER.DRIVE_CONTROLLER_PORT, Constants.CONTROLLER.DRIVE_CONTROLLER_DEADBAND);
    OperatorControls operatorControls = new OperatorControls(Constants.CONTROLLER.OPERATOR_CONTROLLER_PORT);
    Robot.drive.setDefaultCommand(new DefaultDriveCommand(Robot.drive, driveControls));

    m_autoCommandChooser = new AutoCommandChooser();
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    return m_autoCommandChooser.getSelectedAutoCommand();
  }
}
