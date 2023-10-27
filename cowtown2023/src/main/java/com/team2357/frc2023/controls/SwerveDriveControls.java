package com.team2357.frc2023.controls;

import com.team2357.frc2023.Robot;
import com.team2357.frc2023.commands.intakeRoller.IntakeRollerEjectCubeCommand;
import com.team2357.frc2023.commands.intakeRoller.IntakeRollerIndexCubeCommand;
import com.team2357.frc2023.commands.intakeRoller.IntakeRollerPickupCubeCommand;
import com.team2357.frc2023.commands.intakeRoller.IntakeRollerRollCubeCommand;
import com.team2357.lib.triggers.AxisThresholdTrigger;
import com.team2357.frc2023.commands.shooter.ShootCubeCommandGroup;
import com.team2357.frc2023.commands.shooter.ShootCubeCommandGroup.SHOOTER_RPMS;
import com.team2357.lib.util.XboxRaw;

import edu.wpi.first.wpilibj.GenericHID.RumbleType;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;

public class SwerveDriveControls implements RumbleInterface {

    private XboxController m_controller;
    private double m_deadband;

    public static boolean isFlipped;

    private JoystickButton m_aButton;
    private JoystickButton m_bButton;
    private JoystickButton m_xButton;
    private JoystickButton m_yButton;

    private JoystickButton m_backButton;

    private AxisThresholdTrigger m_rightTrigger;
    private AxisThresholdTrigger m_leftTrigger;
    private JoystickButton m_rightBumper;
    private JoystickButton m_leftBumper;

    public static boolean isFlipped;

    public SwerveDriveControls(int portNumber, double deadband) {
        m_controller = new XboxController(portNumber);
        m_deadband = deadband;
        
        m_aButton = new JoystickButton(m_controller, XboxRaw.A.value);
        m_bButton = new JoystickButton(m_controller, XboxRaw.B.value);
        m_xButton = new JoystickButton(m_controller, XboxRaw.X.value);
        m_yButton = new JoystickButton(m_controller, XboxRaw.Y.value);

        m_backButton = new JoystickButton(m_controller, XboxRaw.Back.value);
        
        m_rightTrigger = new AxisThresholdTrigger(m_controller, XboxRaw.TriggerRight, 0.0);
        m_leftTrigger = new AxisThresholdTrigger(m_controller, XboxRaw.TriggerLeft, 0.0);
        m_rightBumper = new JoystickButton(m_controller, XboxRaw.BumperRight.value);
        m_leftBumper = new JoystickButton(m_controller, XboxRaw.BumperLeft.value);

        mapControls();
    }

    private void mapControls() {
        m_aButton.onTrue(new ShootCubeCommandGroup(SHOOTER_RPMS.LOW));
        m_xButton.onTrue(new ShootCubeCommandGroup(SHOOTER_RPMS.MID));
        m_yButton.onTrue(new ShootCubeCommandGroup(SHOOTER_RPMS.HIGH));
        m_bButton.onTrue(new ShootCubeCommandGroup(SHOOTER_RPMS.FAR));

        m_backButton.onTrue(new InstantCommand(() -> {
            Robot.drive.zeroGyro();
        }));

        m_rightTrigger.whileTrue(new IntakeRollerPickupCubeCommand());
        m_leftTrigger.whileTrue(new IntakeRollerEjectCubeCommand());
        m_rightBumper.whileTrue(new IntakeRollerRollCubeCommand());
        m_leftBumper.whileTrue(new IntakeRollerIndexCubeCommand());
    }

    public double getX() {
        if (isFlipped) {
            return modifyAxis(m_controller.getLeftX());
        }
        return -modifyAxis(m_controller.getLeftX());
    }

    public double getY() {
        if (isFlipped) {
            return modifyAxis(m_controller.getLeftY());
        }
        return -modifyAxis(m_controller.getLeftY());
    }

    public double getRotation() {
        return modifyAxis(m_controller.getRightX());
    }

    public double deadband(double value, double deadband) {
        if (Math.abs(value) > deadband) {
            if (value > 0.0) {
                return (value - deadband) / (1.0 - deadband);
            } else {
                return (value + deadband) / (1.0 - deadband);
            }
        }
        return 0.0;
    }

    public double modifyAxis(double value) {
        value = deadband(value, m_deadband);
        value = Math.copySign(value * value, value);
        return value;
    }

    public void setRumble(RumbleType type, double intensity) {
        m_controller.setRumble(type, intensity);
    }
    
}
