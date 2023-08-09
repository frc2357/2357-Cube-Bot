package com.team2357.frc2023.controls;

import edu.wpi.first.wpilibj.GenericHID.RumbleType;
import edu.wpi.first.wpilibj.XboxController;

public class OperatorControls implements RumbleInterface {
    
    private XboxController m_controller;

    public OperatorControls(int portNumber) {
        m_controller = new XboxController(portNumber);

        mapControls();
    }

    private void mapControls() {

    }

    public void setRumble(RumbleType type, double intensity) {
        m_controller.setRumble(type, intensity);
    }

}
