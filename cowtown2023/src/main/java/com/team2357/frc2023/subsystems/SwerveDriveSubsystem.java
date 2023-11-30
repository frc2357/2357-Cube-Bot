package com.team2357.frc2023.subsystems;

import java.io.File;
import java.util.function.Consumer;
import java.util.function.Supplier;

import com.ctre.phoenix.Util;
import com.pathplanner.lib.PathPlanner;
import com.pathplanner.lib.PathPlannerTrajectory;
import com.team2357.frc2023.Constants;
import com.team2357.lib.util.Utility;
import com.team2357.log.lib.Utils;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Rotation3d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.math.trajectory.Trajectory;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.util.WPIUtilJNI;
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
            m_swerve = new SwerveParser(directory).createSwerveDrive(Constants.SWERVE.MAX_VELOCITY_METERS_PER_SECOND);

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
        return m_swerve.swerveController.getTargetSpeeds(x, y, rotation.getRadians(), getYaw().getRadians(), Constants.SWERVE.MAX_VELOCITY_METERS_PER_SECOND);
        
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

    public void postTrajectory(Trajectory trajectory) {
        m_swerve.postTrajectory(trajectory);
    }

    public void updatePoseEstimator() {
        m_swerve.updateOdometry();
    }

    public void rotateToGamepiece(double error) {
        double rotation = Constants.SWERVE.ROTATION_PID_CONTROLLER.calculate(error);
        System.out.print("RotationError: ");
        System.out.println(error);
        System.out.print("Rotation: ");
        System.out.println(rotation);
        drive(new Translation2d(), -rotation * Constants.SWERVE.MAX_ANGULAR_VELOCITY_RADIANS_PER_SECOND, false, false);
    }

    public void translateToGamepiece(double error) {
        System.out.print("TranslationError: ");
        System.out.println(error);
        drive(new Translation2d(0, Constants.SWERVE.GAMEPIECE_TRACKING_TRANSLATION_SPEED * Constants.SWERVE.MAX_VELOCITY_METERS_PER_SECOND), 0, false, false);
    }

    public void translateAndRotateToGamepiece(double rotationError, double translationError, double translationSpeed, boolean rotate){
        System.out.print("TranslationError: ");
        System.out.println(translationError);
        double rotation = 0;
        if(rotate){
            rotation = Constants.SWERVE.ROTATION_PID_CONTROLLER.calculate(rotationError);
        }
        System.out.print("RotationError: ");
        System.out.println(rotationError);
        System.out.print("Rotation: ");
        System.out.println(rotation);
        System.out.print("Rotating: ");
        System.out.println(rotate);
        drive(new Translation2d(0, translationSpeed * Constants.SWERVE.MAX_VELOCITY_METERS_PER_SECOND), -rotation * Constants.SWERVE.MAX_ANGULAR_VELOCITY_RADIANS_PER_SECOND, false, false);
    }

    public void trackGamepieceDriverContrallable(double rotationError, double translationXSpeed, double translationYSpeed, boolean rotate){
        double rotation = 0;
        if(rotate){
            rotation = Constants.SWERVE.ROTATION_PID_CONTROLLER.calculate(rotationError);
        }
        System.out.print("RotationError: ");
        System.out.println(rotationError);
        System.out.print("Rotation: ");
        System.out.println(rotation);
        drive(new Translation2d(translationXSpeed, translationYSpeed * Constants.SWERVE.MAX_VELOCITY_METERS_PER_SECOND), -rotation * Constants.SWERVE.MAX_ANGULAR_VELOCITY_RADIANS_PER_SECOND, false, false);
    
    }

    public void setChassisSpeeds(ChassisSpeeds chassisSpeeds){
        m_swerve.setChassisSpeeds(chassisSpeeds);
    }

    public Consumer<SwerveModuleState[]> setSwerveStatesConsumer(){
        return new Consumer<SwerveModuleState[]>() {

            @Override
            public void accept(SwerveModuleState[] desiredStates) {
                m_swerve.setModuleStates(desiredStates, true);
            }
            
        };
    }

    public Supplier<Pose2d> getPoseSupplier(){
        return new Supplier<Pose2d>() {

            @Override
            public Pose2d get() {
                return getPose();
            }
        };
    }

}