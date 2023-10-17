package com.team2357.frc2023.controls;

import com.team2357.frc2023.commands.intakeRoller.IntakeRollerEjectCubeCommand;
import com.team2357.frc2023.commands.intakeRoller.IntakeRollerPickupCubeCommand;
import com.team2357.lib.triggers.AxisThresholdTrigger;

import edu.wpi.first.wpilibj.GenericHID.RumbleType;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.XboxController.Axis;

public class SwerveDriveControls implements RumbleInterface {

    private XboxController m_controller;
    private double m_deadband;

    public static boolean isFlipped;

    private AxisThresholdTrigger m_rightTrigger;
    private AxisThresholdTrigger m_leftTrigger;

    public SwerveDriveControls(int portNumber, double deadband) {
        m_controller = new XboxController(portNumber);
        m_deadband = deadband;
        
        m_rightTrigger = new AxisThresholdTrigger(m_controller, Axis.kRightTrigger, 0.0);
        m_leftTrigger = new AxisThresholdTrigger(m_controller, Axis.kLeftTrigger, 0.0);
        
        mapControls();
    }

    private void mapControls() {
        m_rightTrigger.whileTrue(new IntakeRollerPickupCubeCommand());
        m_leftTrigger.whileTrue(new IntakeRollerEjectCubeCommand());
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
