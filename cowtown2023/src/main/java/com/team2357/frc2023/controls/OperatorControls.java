package com.team2357.frc2023.controls;

import com.team2357.frc2023.commands.human.panic.IntakeRollerAxisCommand;
import com.team2357.lib.triggers.AxisThresholdTrigger;
import com.team2357.frc2023.commands.human.panic.ShooterAxisCommand;
import com.team2357.lib.triggers.AxisThresholdTrigger;
import com.team2357.frc2023.Constants;
import com.team2357.frc2023.commands.human.panic.IntakeSlideAxisCommand;
import com.team2357.frc2023.commands.human.panic.IntakeSlideToggleCommand;
import com.team2357.lib.triggers.AxisThresholdTrigger;
import com.team2357.lib.util.Utility;
import com.team2357.lib.util.XboxRaw;

import edu.wpi.first.wpilibj.GenericHID.RumbleType;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.XboxController.Axis;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.POVButton;
import edu.wpi.first.wpilibj2.command.button.Trigger;

public class OperatorControls implements RumbleInterface {

    private XboxController m_controller;

    // Triggers
    public AxisThresholdTrigger m_leftTrigger;
    public AxisThresholdTrigger m_rightTrigger;

    // Buttons
    public JoystickButton m_leftStickButton;
    public JoystickButton m_backButton;
    public JoystickButton m_startButton;
    public JoystickButton m_leftBumper;
    public JoystickButton m_rightBumper;
    public Trigger m_aButton;
    public Trigger m_bButton;
    public Trigger m_xButton;
    public Trigger m_yButton;

    // Dpad
    public POVButton m_upDPad;
    public POVButton m_rightDPad;
    public POVButton m_downDPad;
    public POVButton m_leftDPad;

    public OperatorControls(int portNumber) {
        m_controller = new XboxController(portNumber);

        // Triggers
        m_leftTrigger = new AxisThresholdTrigger(m_controller, Axis.kRightTrigger, .1);
        m_rightTrigger = new AxisThresholdTrigger(m_controller, Axis.kLeftTrigger, .1);

        // Buttons
        m_leftStickButton = new JoystickButton(m_controller, XboxRaw.StickPressLeft.value);
        m_backButton = new JoystickButton(m_controller, XboxRaw.Back.value);
        m_startButton = new JoystickButton(m_controller, XboxRaw.Start.value);
        m_leftBumper = new JoystickButton(m_controller, XboxRaw.BumperLeft.value);
        m_rightBumper = new JoystickButton(m_controller, XboxRaw.BumperRight.value);
        m_aButton = new JoystickButton(m_controller, XboxRaw.A.value);
        m_bButton = new JoystickButton(m_controller, XboxRaw.B.value);
        m_xButton = new JoystickButton(m_controller, XboxRaw.X.value);
        m_yButton = new JoystickButton(m_controller, XboxRaw.Y.value);

        m_upDPad = new POVButton(m_controller, 0);
        m_rightDPad = new POVButton(m_controller, 90);
        m_downDPad = new POVButton(m_controller, 180);
        m_leftDPad = new POVButton(m_controller, 270);

        mapControls();
    }

    public double getTopIntakeRollerAxis() {
        double value = m_controller.getRightTriggerAxis();
        boolean reverse = m_rightBumper.getAsBoolean();
        return -((reverse ? -1 : 1) * value);
    }

    public double getBottomIntakeRollerAxis() {
        double value = m_controller.getLeftTriggerAxis();
        boolean reverse = m_leftBumper.getAsBoolean();
        return (reverse ? -1 : 1) * value;
    }

    public double getRightYAxis() {
        double value = m_controller.getRightY();
        return Utility.deadband(value, Constants.CONTROLLER.OPERATOR_CONTROLLER_DEADBAND);
    }

    public double getRightTriggerAxis() {
        return m_controller.getRightTriggerAxis();
    }

    public double getLeftTriggerAxis() {
        return m_controller.getLeftTriggerAxis();
    }

    private void mapControls() {
        // ONLY UNCOMMENT IF IT IS BEING USED

        AxisInterface axisRightStickY = () -> {
            return getRightYAxis();
        };

        AxisInterface rightTriggerAxis = () -> {
            return m_controller.getRightTriggerAxis();
        };

        AxisInterface topIntakeRollerAxis = () -> {
            return getTopIntakeRollerAxis();
        };

        AxisInterface bottomIntakeRollerAxis = () -> {
            return getBottomIntakeRollerAxis();
        };

        // AxisInterface intakeRollerForwardAxis = () -> {
        //     return getRightTriggerAxis();
        // };

        // AxisInterface intakeRollerReverseAxis = () -> {
        //     return -getLeftTriggerAxis();
        // };

        // Trigger noDPad = new Trigger(() -> m_upDPad.getAsBoolean() || m_rightDPad.getAsBoolean()
        //         || m_downDPad.getAsBoolean() || m_leftDPad.getAsBoolean()).negate();

        Trigger noLetterButtons = m_aButton.or(m_bButton).or(m_xButton).or(m_yButton).negate();
        Trigger upDPadOnly = m_upDPad.and(noLetterButtons);
        Trigger downDPadOnly = m_downDPad.and(noLetterButtons);
        // Trigger leftDPadOnly = m_leftDPad.and(noLetterButtons);
        Trigger rightDPadOnly = m_rightDPad.and(noLetterButtons);

        // Trigger upDPadAndA = m_upDPad.and(m_aButton);
        // Trigger upDPadAndX = m_upDPad.and(m_xButton);
        // Trigger upDPadAndY = m_upDPad.and(m_yButton);
        // Trigger upDPadAndB = m_upDPad.and(m_bButton);

        // Trigger downDPadAndA = m_downDPad.and(m_aButton);
        // Trigger downDPadAndX = m_downDPad.and(m_xButton);
        // Trigger downDPadAndY = m_downDPad.and(m_yButton);
        // Trigger downDPadAndB = m_downDPad.and(m_bButton);

        // Trigger leftDPadAndA = m_leftDPad.and(m_aButton);
        // Trigger leftDPadAndX = m_leftDPad.and(m_xButton);
        // Trigger leftDPadAndY = m_leftDPad.and(m_yButton);
        // Trigger leftDPadAndB = m_leftDPad.and(m_bButton);

        Trigger rightDPadAndA = m_rightDPad.and(m_aButton);
        // Trigger rightDPadAndX = m_rightDPad.and(m_xButton);
        // Trigger rightDPadAndY = m_rightDPad.and(m_yButton);
        // Trigger rightDPadAndB = m_rightDPad.and(m_bButton);
        // Trigger rightDPadAndRightTrigger = m_rightDPad.and(m_rightTrigger);
        // Trigger rightDPadAndLeftTrigger = m_rightDPad.and(m_leftTrigger);

        // Trigger downDPadAndRightTrigger = m_downDPad.and(m_rightTrigger);
        // Trigger downDPadAndLeftTrigger = m_downDPad.and(m_leftTrigger);

        // Trigger aButton = m_aButton.and(noDPad);
        // Trigger bButton = m_bButton.and(noDPad);
        // Trigger yButton = m_yButton.and(noDPad);
        // Trigger xButton = m_xButton.and(noDPad);

        rightDPadOnly.whileTrue(new IntakeSlideAxisCommand(axisRightStickY));
        rightDPadAndA.onTrue(new IntakeSlideToggleCommand());
      
        upDPadOnly.whileTrue(new ShooterAxisCommand(rightTriggerAxis));

        downDPadOnly.whileTrue(new IntakeRollerAxisCommand(topIntakeRollerAxis, bottomIntakeRollerAxis));
    
    }

    public void setRumble(RumbleType type, double intensity) {
        m_controller.setRumble(type, intensity);
    }

}
