package com.team2357.frc2023.commands.shooter;

import com.team2357.frc2023.Constants;
import com.team2357.frc2023.commands.intakeRoller.IntakeRollerIndexCubeCommand;
import com.team2357.frc2023.commands.intakeRoller.IntakeRollerStopMotorsCommand;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class ShootCubeCommandGroup extends SequentialCommandGroup {

    public enum SHOOTER_RPMS {
        LOW(
            Constants.SHOOTER.SMART_DASHBOARD_SHOOT_LOW_TOP,
            Constants.SHOOTER.SMART_DASHBOARD_SHOOT_LOW_BOTTOM,
            Constants.SHOOTER.TOP_MOTOR_LOW_RPMS,
            Constants.SHOOTER.BOTTOM_MOTOR_LOW_RPMS
        ),
        MID(
            Constants.SHOOTER.SMART_DASHBOARD_SHOOT_MID_TOP,
            Constants.SHOOTER.SMART_DASHBOARD_SHOOT_MID_BOTTOM,
            Constants.SHOOTER.TOP_MOTOR_MID_RPMS,
            Constants.SHOOTER.BOTTOM_MOTOR_MID_RPMS
        ),
        HIGH(
            Constants.SHOOTER.SMART_DASHBOARD_SHOOT_HIGH_TOP,
            Constants.SHOOTER.SMART_DASHBOARD_SHOOT_HIGH_BOTTOM,
            Constants.SHOOTER.TOP_MOTOR_HIGH_RPMS,
            Constants.SHOOTER.BOTTOM_MOTOR_HIGH_RPMS
        ),
        FAR(
            Constants.SHOOTER.SMART_DASHBOARD_SHOOT_FAR_TOP,
            Constants.SHOOTER.SMART_DASHBOARD_SHOOT_FAR_BOTTOM,
            Constants.SHOOTER.TOP_MOTOR_FAR_RPMS,
            Constants.SHOOTER.BOTTOM_MOTOR_FAR_RPMS
        );

        private double m_topDefaultRpms;
        private double m_bottomDefaultRpms;
        private String m_topRpmsName;
        private String m_bottomRpmsName;

        SHOOTER_RPMS(String topRpmsName, String bottomRpmsName, double topDefaultRpms, double bottomDefaultRpms) {
            this.m_topRpmsName = topRpmsName;
            this.m_topDefaultRpms = topDefaultRpms;
            this.m_bottomRpmsName = bottomRpmsName;
            this.m_bottomDefaultRpms = bottomDefaultRpms;
            SmartDashboard.putNumber(m_topRpmsName, m_topDefaultRpms);
            SmartDashboard.putNumber(m_bottomRpmsName, m_bottomDefaultRpms);
        }

        public double getTopRpms() {
            return SmartDashboard.getNumber(m_topRpmsName, m_topDefaultRpms);
        }

        public double getBottomRpms() {
            return SmartDashboard.getNumber(m_bottomRpmsName, m_bottomDefaultRpms);
        }
    }

    public ShootCubeCommandGroup(SHOOTER_RPMS position) {
        super(
            new SequentialCommandGroup(
                new ShooterSetRPMsCommand(rpms.top, rpms.bottom),
                new IntakeRollerIndexCubeCommand()
            ).finallyDo((interrupted) -> {
                new ParallelCommandGroup(
                    new ShooterStopMotorsCommand(),
                    new IntakeRollerStopMotorsCommand()
                ).schedule();
            })
        );
    }

}
