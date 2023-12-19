package com.team2357.frc2023.subsystems;

import java.io.File;
import java.util.function.Consumer;
import java.util.function.Supplier;

import org.littletonrobotics.junction.Logger;
import com.team2357.frc2023.Constants;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Rotation3d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.math.trajectory.Trajectory;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import swervelib.SwerveDrive;
import swervelib.SwerveModule;
import swervelib.parser.SwerveParser;
import swervelib.telemetry.SwerveDriveTelemetry;
import swervelib.telemetry.SwerveDriveTelemetry.TelemetryVerbosity;


public class SwerveDriveSubsystem extends SubsystemBase {

    private SwerveDrive m_swerve;

    public SwerveDriveSubsystem(File directory) {
        SwerveDriveTelemetry.verbosity = TelemetryVerbosity.NONE;
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
        m_swerve.drive(translation, rotation, fieldRelative, isOpenLoop);
    } 
    
    public ChassisSpeeds getTargetSpeeds(double x, double y, Rotation2d rotation) {
        return m_swerve.swerveController.getTargetSpeeds(x, y, rotation.getRadians(), getYaw().getRadians());
        
    }

    @Override
    public void 
    periodic() {
        Logger.getInstance().recordOutput("Swerve States", m_swerve.getStates());
        Logger.getInstance().recordOutput("Robot Pose", getPose());
        // System.out.println("ROBOT POSE X: " + getPose().getX());
        // System.out.println("ROBOT POSE Y: " + getPose().getY());
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

    public void postTrajectory(Trajectory trajectory) {
        m_swerve.postTrajectory(trajectory);
    }

    public void updatePoseEstimator() {
        m_swerve.updateOdometry();
    }

    public void translateAndRotateToGamepiece(double rotationError, double translationSpeed, boolean rotate){
        double rotation = rotate ? Constants.SWERVE.ROTATION_PID_CONTROLLER.calculate(rotationError) : 0;
        System.out.println("RotationError: " + rotationError);
        System.out.println("Rotation: " + rotation);
        System.out.println("Rotating: " + rotate);
        drive(new Translation2d(0, translationSpeed * Constants.SWERVE.MAX_VELOCITY_METERS_PER_SECOND), -rotation * Constants.SWERVE.MAX_ANGULAR_VELOCITY_RADIANS_PER_SECOND, false, false);
    }

    public void trackGamepieceDriverControllable(double rotationError, double translationXSpeed, double translationYSpeed, boolean rotate){
        double rotation = rotate ? Constants.SWERVE.ROTATION_PID_CONTROLLER.calculate(rotationError) : 0;
        System.out.println("RotationError: " + rotationError);
        System.out.println("Rotation: " + rotation);
        System.out.println("Translation X Speed : " + translationXSpeed);
        System.out.println("Translation Y Speed : " + translationYSpeed);
        drive(new Translation2d(translationXSpeed, translationYSpeed * Constants.SWERVE.MAX_VELOCITY_METERS_PER_SECOND), -rotation * Constants.SWERVE.MAX_ANGULAR_VELOCITY_RADIANS_PER_SECOND, false, false);
    
    }

    public Consumer<ChassisSpeeds> getChassisSpeedsConsumer(){
        return new Consumer<ChassisSpeeds>() {

            @Override
            public void accept(ChassisSpeeds speeds) {
                drive(new Translation2d(speeds.vxMetersPerSecond, speeds.vyMetersPerSecond),speeds.omegaRadiansPerSecond, false, true);
            } 
            
        };
    }

    public Supplier<Pose2d> getChoreoPoseSupplier(){
        return new Supplier<Pose2d>() {

            @Override
            public Pose2d get() {
                return getPose();
            }
        };
    }

}