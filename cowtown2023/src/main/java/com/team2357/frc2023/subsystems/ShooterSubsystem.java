package com.team2357.frc2023.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.SparkMaxPIDController;
import com.revrobotics.CANSparkMax.ControlType;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.team2357.frc2023.Constants;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class ShooterSubsystem extends SubsystemBase {

    private CANSparkMax m_topShooterMotor;
    private CANSparkMax m_bottomShooterMotor;

    private SparkMaxPIDController m_topPIDController;
    private SparkMaxPIDController m_bottomPIDController;

    public ShooterSubsystem() {
        m_topShooterMotor = new CANSparkMax(Constants.CAN_ID.TOP_SHOOTER_MOTOR_ID, MotorType.kBrushless);
        m_bottomShooterMotor = new CANSparkMax(Constants.CAN_ID.BOTTOM_SHOOTER_MOTOR_ID, MotorType.kBrushless);
        configure();
    }

    public void configure() {
        m_topShooterMotor.setInverted(Constants.SHOOTER.TOP_MOTOR_INVERTED);
        m_bottomShooterMotor.setInverted(Constants.SHOOTER.BOTTOM_MOTOR_INVERTED);

        m_topShooterMotor.enableVoltageCompensation(12);
        m_topShooterMotor.setIdleMode(Constants.SHOOTER.MOTOR_IDLE_MODE);
        m_topShooterMotor.setSmartCurrentLimit(Constants.SHOOTER.TOP_MOTOR_STALL_LIMIT_AMPS,
                Constants.SHOOTER.TOP_MOTOR_FREE_LIMIT_AMPS);

        m_bottomShooterMotor.enableVoltageCompensation(12);
        m_bottomShooterMotor.setIdleMode(Constants.SHOOTER.MOTOR_IDLE_MODE);
        m_bottomShooterMotor.setSmartCurrentLimit(Constants.SHOOTER.BOTTOM_MOTOR_STALL_LIMIT_AMPS,
                Constants.SHOOTER.BOTTOM_MOTOR_FREE_LIMIT_AMPS);


        m_topPIDController = m_topShooterMotor.getPIDController();
        m_bottomPIDController = m_bottomShooterMotor.getPIDController();

        m_topPIDController.setP(Constants.SHOOTER.TOP_MOTOR_P);
        m_topPIDController.setI(Constants.SHOOTER.TOP_MOTOR_I);
        m_topPIDController.setD(Constants.SHOOTER.TOP_MOTOR_D);
        m_topPIDController.setFF(Constants.SHOOTER.TOP_MOTOR_FF);

        m_bottomPIDController.setP(Constants.SHOOTER.BOTTOM_MOTOR_P);
        m_bottomPIDController.setI(Constants.SHOOTER.BOTTOM_MOTOR_I);
        m_bottomPIDController.setD(Constants.SHOOTER.BOTTOM_MOTOR_D);
        m_bottomPIDController.setFF(Constants.SHOOTER.BOTTOM_MOTOR_FF);

    }

    public void setRPMs(double topRPMs, double bottomRPMs) {
        m_topPIDController.setReference(topRPMs, ControlType.kVelocity);
        m_bottomPIDController.setReference(bottomRPMs, ControlType.kVelocity);
    }

    public void set(double topPercentOutput, double bottomPercentOutput) {
        m_topShooterMotor.set(topPercentOutput);
        m_bottomShooterMotor.set(bottomPercentOutput);
    }

    public void stopShooterMotors() {
        m_topShooterMotor.set(0.0);
        m_bottomShooterMotor.set(0.0);
    }

    public double getTopVelocity() {
        return m_topShooterMotor.getEncoder().getVelocity();
    }

    public double getBottomVelocity() {
        return m_bottomShooterMotor.getEncoder().getVelocity();
    }

    public boolean isAtRPMs(double topRPMs, double bottomRPMs) {
        return getTopVelocity() == topRPMs && getBottomVelocity() == bottomRPMs;
    }

}