package com.team2357.frc2023.controls;

import com.team2357.frc2023.Robot;
import com.team2357.frc2023.commands.IntakeDeployCommandGroup;
import com.team2357.frc2023.commands.IntakeStowCommandGroup;
import com.team2357.frc2023.commands.intakeRoller.IntakeRollerStopMotorsCommand;
import com.team2357.frc2023.commands.shooter.ShootCubeCommandGroup;
import com.team2357.frc2023.commands.shooter.ShootCubeCommandGroup.SHOOTER_RPMS;
import com.team2357.frc2023.commands.shooter.ShooterStopMotorsCommand;
import com.team2357.lib.triggers.AxisThresholdTrigger;
import com.team2357.lib.util.XboxRaw;

import edu.wpi.first.wpilibj.GenericHID.RumbleType;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.XboxController.Axis;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;

public class SwerveDriveControls implements RumbleInterface {

    private XboxController m_controller;
    private double m_deadband;

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
        
        m_rightTrigger = new AxisThresholdTrigger(m_controller, Axis.kRightTrigger, 0.1);
        m_leftTrigger = new AxisThresholdTrigger(m_controller, Axis.kLeftTrigger, 0.1);
        m_rightBumper = new JoystickButton(m_controller, XboxRaw.BumperRight.value);
        m_leftBumper = new JoystickButton(m_controller, XboxRaw.BumperLeft.value);

        mapControls();
    }

    private void mapControls() {
        m_aButton.whileTrue(new ShootCubeCommandGroup(SHOOTER_RPMS.LOW));
        m_aButton.onFalse(new ParallelCommandGroup(
            new ShooterStopMotorsCommand(),
            new IntakeRollerStopMotorsCommand()
        ));
        m_xButton.whileTrue(new ShootCubeCommandGroup(SHOOTER_RPMS.MID));
        m_xButton.onFalse(new ParallelCommandGroup(
            new ShooterStopMotorsCommand(),
            new IntakeRollerStopMotorsCommand()
        ));
        m_yButton.whileTrue(new ShootCubeCommandGroup(SHOOTER_RPMS.HIGH));
        m_yButton.onFalse(new ParallelCommandGroup(
            new ShooterStopMotorsCommand(),
            new IntakeRollerStopMotorsCommand()
        ));
        m_bButton.whileTrue(new ShootCubeCommandGroup(SHOOTER_RPMS.FAR));
        m_bButton.onFalse(new ParallelCommandGroup(
            new ShooterStopMotorsCommand(),
            new IntakeRollerStopMotorsCommand()
        ));

        m_backButton.onTrue(new InstantCommand(() -> {
            Robot.drive.zeroGyro();
        }));

        // m_rightTrigger.whileTrue(new IntakeSlideFinishExtendCommand());
        m_rightTrigger.whileTrue(new IntakeDeployCommandGroup());
        m_rightTrigger.onFalse(new IntakeStowCommandGroup());
        // m_rightBumper.whileTrue(new IntakeRollerEjectCubeCommand());
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
        return -modifyAxis(m_controller.getRightX());
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
