package com.team2357.frc2023.controls;

import edu.wpi.first.wpilibj.GenericHID.RumbleType;
import edu.wpi.first.wpilibj.XboxController;

public class OperatorControls implements RumbleInterface {
    
    private XboxController m_controller;

    public OperatorControls(XboxController controller) {
        m_controller = controller;

        mapControls();
    }

    private void mapControls() {

    }

    public void setRumble(RumbleType type, double intensity) {
        m_controller.setRumble(type, intensity);
    }

}
