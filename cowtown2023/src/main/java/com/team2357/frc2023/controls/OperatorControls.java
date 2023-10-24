package com.team2357.frc2023.controls;

import com.team2357.frc2023.commands.human.panic.ShooterAxisCommand;
import com.team2357.lib.triggers.AxisThresholdTrigger;
import com.team2357.lib.util.XboxRaw;

import edu.wpi.first.wpilibj.GenericHID.RumbleType;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.XboxController.Axis;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.POVButton;
import edu.wpi.first.wpilibj2.command.button.Trigger;

public class OperatorControls implements RumbleInterface {
    
    private XboxController m_controller;

    public AxisThresholdTrigger m_rightTrigger;

    public Trigger m_aButton;
    public Trigger m_bButton;
    public Trigger m_xButton;
    public Trigger m_yButton;

    public POVButton m_upDPad;
    
    public OperatorControls(int portNumber) {
        m_controller = new XboxController(portNumber);

        m_rightTrigger = new AxisThresholdTrigger(m_controller, Axis.kRightTrigger, .1);

        m_aButton = new JoystickButton(m_controller, XboxRaw.A.value);
        m_bButton = new JoystickButton(m_controller, XboxRaw.B.value);
        m_xButton = new JoystickButton(m_controller, XboxRaw.X.value);
        m_yButton = new JoystickButton(m_controller, XboxRaw.Y.value);

        m_upDPad = new POVButton(m_controller, 0);
        
        mapControls();
    }

    public double getRightTriggerAxis() {
        return m_controller.getRightTriggerAxis();
    }

    private void mapControls() {
        AxisInterface rightTriggerAxis = () -> {
            return m_controller.getRightTriggerAxis();
        };

        Trigger noLetterButtons = m_aButton.or(m_bButton).or(m_xButton).or(m_yButton).negate();
        Trigger upDPadOnly = m_upDPad.and(noLetterButtons);


        upDPadOnly.whileTrue(new ShooterAxisCommand(rightTriggerAxis));

    }

    public void setRumble(RumbleType type, double intensity) {
        m_controller.setRumble(type, intensity);
    }

}
