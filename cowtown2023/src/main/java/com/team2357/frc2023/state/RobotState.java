package com.team2357.frc2023.state;

import org.littletonrobotics.junction.Logger;

import edu.wpi.first.wpilibj.DriverStation.Alliance;

public class RobotState {
    private static final RobotState s_instance = new RobotState();

    public static enum DriveControlState {
        COMPETITION_MODE,
        DEMO_MODE
    }

    public static Alliance getAlliance() {
        return s_instance.m_alliance;
    }

    public static boolean isZeroed() {
        return s_instance.m_zeroed;
    }

    public static void setRobotZeroed(boolean zeroed) {
        s_instance.setZeroed(zeroed);
    }

    public static void onDriverAllianceSelect(Alliance alliance) {
        s_instance.setAlliance(alliance);
    }

    public static DriveControlState getDriveControlState() {
        return s_instance.m_currentDriveControlState;
    }

    public static void toggleDriveControlState() {
        switch (getDriveControlState()) {
            case COMPETITION_MODE:
                setDriveControlState(DriveControlState.DEMO_MODE);
                break;
            case DEMO_MODE:
                setDriveControlState(DriveControlState.COMPETITION_MODE);
                break;
        }
    }

    public static void setDriveControlState(DriveControlState driveControlState) {
        s_instance.setCurrentDriveControlState(driveControlState);
    }

    private Alliance m_alliance;
    private DriveControlState m_currentDriveControlState;
    private boolean m_zeroed;

    private RobotState() {
        m_alliance = Alliance.Invalid;
        m_currentDriveControlState = DriveControlState.COMPETITION_MODE;
        m_zeroed = false;
    }

    private void setAlliance(Alliance alliance) {
        Logger.getInstance().recordOutput("Driver Set Alliance", alliance.name());

        m_alliance = alliance;
    }

    private void setZeroed(boolean zeroed) {
        m_zeroed = zeroed;
    }

    private void setCurrentDriveControlState(DriveControlState driveControlState) {
        Logger.getInstance().recordOutput("Robot Drive Control State", driveControlState.name());
        m_currentDriveControlState = driveControlState;
    }
}
