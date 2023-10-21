package com.team2357.frc2023.subsystems;

import java.io.File;

import com.pathplanner.lib.PathPlannerTrajectory;
import com.team2357.frc2023.Constants;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Rotation3d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import swervelib.SwerveDrive;
import swervelib.SwerveModule;
import swervelib.parser.SwerveParser;
import swervelib.telemetry.SwerveDriveTelemetry;
import swervelib.telemetry.SwerveDriveTelemetry.TelemetryVerbosity;

public class SwerveDriveSubsystem extends SubsystemBase {

    private SwerveDrive m_swerve;

    private PathPlannerTrajectory m_currentTrajectory;
    private double m_trajectoryStartSeconds;

    public SwerveDriveSubsystem(File directory) {
        SwerveDriveTelemetry.verbosity = TelemetryVerbosity.HIGH;
        try {
            m_swerve = new SwerveParser(directory).createSwerveDrive();

            SwerveModule[] modules = m_swerve.getModules();
            for (SwerveModule module : modules) {
                module.getAngleMotor().configureIntegratedEncoder(Constants.SWERVE.ABSOLUTE_ENCODER_CONVERSION_FACTOR);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void drive(Translation2d translation, double rotation, boolean fieldRelative, boolean isOpenLoop) {
        m_swerve.drive(translation.rotateBy(Rotation2d.fromDegrees(-90)), rotation, fieldRelative, isOpenLoop);
    }

    public void setChassisSpeeds(ChassisSpeeds chassisSpeeds) {
        m_swerve.setChassisSpeeds(chassisSpeeds);
    }

    public ChassisSpeeds getTargetSpeeds(double x, double y, Rotation2d rotation) {
        return m_swerve.swerveController.getTargetSpeeds(x, y, rotation.getRadians(),
                getYaw().getRadians());

    }

    @Override
    public void periodic() {
    }

    public void setBrakeMode(boolean brake) {
        m_swerve.setMotorIdleMode(brake);
    }

    public ChassisSpeeds getFieldVelocity() {
        return m_swerve.getFieldVelocity();
    }

    public SwerveDriveKinematics getKinematics() {
        return m_swerve.kinematics;
    }

    public void zeroGyro() {
        m_swerve.zeroGyro();
    }

    public void setGyro(double degrees) {
        m_swerve.setGyro(new Rotation3d(0, 0, degrees));
    }

    public Rotation2d getYaw() {
        return m_swerve.getYaw();
    }

    public Rotation2d getRoll() {
        return m_swerve.getRoll();
    }

    public Rotation2d getPitch() {
        return m_swerve.getPitch();
    }

    public Pose2d getPose() {
        return m_swerve.getPose();
    }

    public void resetPoseEstimator(Pose2d pose) {
        m_swerve.resetOdometry(pose);
    }

    public void updatePoseEstimator() {
        m_swerve.updateOdometry();
    }

    // Trajectory
    public void setCurrentTrajectory(PathPlannerTrajectory trajectory) {
        m_currentTrajectory = trajectory;
        m_trajectoryStartSeconds = Timer.getFPGATimestamp();
    }

    public void endTrajectory() {
        m_currentTrajectory = null;
        m_trajectoryStartSeconds = 0;
    }

}
