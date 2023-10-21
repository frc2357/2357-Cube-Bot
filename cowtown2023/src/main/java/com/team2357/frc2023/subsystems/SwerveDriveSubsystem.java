package com.team2357.frc2023.subsystems;

import java.io.File;

import com.swervelib.SwerveDrive;
import com.swervelib.SwerveModule;
import com.swervelib.parser.SwerveParser;
import com.swervelib.telemetry.SwerveDriveTelemetry;
import com.swervelib.telemetry.SwerveDriveTelemetry.TelemetryVerbosity;
import com.team2357.frc2023.Constants;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Rotation3d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import edu.wpi.first.math.trajectory.Trajectory;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class SwerveDriveSubsystem extends SubsystemBase {

    private SwerveDrive m_swerve;

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

    public void resetOdometry(Pose2d pose) {
        m_swerve.resetOdometry(pose);
    }

    public void postTrajectory(Trajectory trajectory) {
        m_swerve.postTrajectory(trajectory);
    }

    public void updatePoseEstimator() {

        // LimelightSubsystem leftLL = Robot.limelights.getLimelight(LIMELIGHT.LEFT);
        // LimelightSubsystem rightLL = Robot.limelights.getLimelight(LIMELIGHT.RIGHT);

        // Pose2d leftPose = leftLL.getCurrentAllianceLimelightPose();
        // Pose2d rightPose = rightLL.getCurrentAllianceLimelightPose();

        // double leftTime = leftLL.getCurrentAllianceBotposeTimestamp();
        // double rightTime = rightLL.getCurrentAllianceBotposeTimestamp();

        // if (leftPose != null) {
        // m_swerve.addVisionMeasurement(leftPose, leftTime, false, null);
        // }

        // if (rightPose != null) {
        // m_swerve.addVisionMeasurement(rightPose, rightTime, false, null);
        // }

        m_swerve.updateOdometry();
        // m_poseEstimator.update(getGyroscopeRotation(),
        // new SwerveModulePosition[] { m_frontLeftModule.getPosition(),
        // m_frontRightModule.getPosition(),
        // m_backLeftModule.getPosition(), m_backRightModule.getPosition() });
    }

}
