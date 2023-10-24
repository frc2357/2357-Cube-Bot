package com.team2357.frc2023.controls;

import com.team2357.frc2023.commands.human.panic.IntakeRollerAxisCommand;
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

    public Trigger m_aButton;
    public Trigger m_bButton;
    public Trigger m_xButton;
    public Trigger m_yButton;

    public POVButton m_downDPad;


    public OperatorControls(int portNumber) {
        m_controller = new XboxController(portNumber);

        m_aButton = new JoystickButton(m_controller, XboxRaw.A.value);
        m_bButton = new JoystickButton(m_controller, XboxRaw.B.value);
        m_xButton = new JoystickButton(m_controller, XboxRaw.X.value);
        m_yButton = new JoystickButton(m_controller, XboxRaw.Y.value);

        m_downDPad = new POVButton(m_controller, 180);
        
        mapControls();
    }

    public double getTopIntakeRollerAxis() {
        double value = m_controller.getRightTriggerAxis();
        boolean reverse = m_controller.getRightBumperPressed();
        return (reverse ? -1 : 1) * value;
    }

    public double getBottomIntakeRollerAxis() {
        double value = m_controller.getLeftTriggerAxis();
        boolean reverse = m_controller.getLeftBumperPressed();
        return (reverse ? -1 : 1) * value;
    }

    private void mapControls() {
        AxisInterface topIntakeRollerAxis = () -> {
            return getTopIntakeRollerAxis();
        };

        AxisInterface bottomIntakeRollerAxis = () -> {
            return getBottomIntakeRollerAxis();
        };

        Trigger noLetterButtons = m_aButton.or(m_bButton).or(m_xButton).or(m_yButton).negate();
        Trigger downDPadOnly = m_downDPad.and(noLetterButtons);

        downDPadOnly.whileTrue(new IntakeRollerAxisCommand(topIntakeRollerAxis, bottomIntakeRollerAxis));
    }

    public void setRumble(RumbleType type, double intensity) {
        m_controller.setRumble(type, intensity);
    }

}
