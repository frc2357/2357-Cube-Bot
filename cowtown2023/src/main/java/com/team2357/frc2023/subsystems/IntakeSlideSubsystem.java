package com.team2357.frc2023.subsystems;

import org.ejml.data.MatrixType;

import com.revrobotics.CANSparkMax;
import com.revrobotics.SparkMaxPIDController;
import com.revrobotics.CANSparkMax.ControlType;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.team2357.frc2023.Constants;
import com.team2357.lib.subsystems.ClosedLoopSubsystem;
import com.team2357.lib.util.Utility;

public class IntakeSlideSubsystem extends ClosedLoopSubsystem {

    private CANSparkMax m_masterSlideMotor;
    private CANSparkMax m_followerSlideMotor;
    private SparkMaxPIDController m_slidePIDController;

    private double m_targetRotations;

    private enum SlideState {
        Unknown, Extended, Retracted
    };

    private SlideState m_currentState;
    private SlideState m_desiredState;

    public IntakeSlideSubsystem() {
        m_masterSlideMotor = new CANSparkMax(Constants.CAN_ID.MASTER_INTAKE_SLIDE_MOTOR_ID, MotorType.kBrushless);
        m_followerSlideMotor = new CANSparkMax(Constants.CAN_ID.FOLLOWER_INTAKE_SLIDE_MOTOR_ID, MotorType.kBrushless);

        configure();
    }

    private void configure() {
        m_masterSlideMotor.setInverted(Constants.INTAKE_SLIDE.MASTER_MOTOR_INVERTED);
        m_followerSlideMotor.setInverted(Constants.INTAKE_SLIDE.FOLLOWER_MOTOR_INVERTED);

        m_followerSlideMotor.follow(m_masterSlideMotor, true);

        m_masterSlideMotor.setIdleMode(Constants.INTAKE_SLIDE.IDLE_MODE);
        m_masterSlideMotor.setSmartCurrentLimit(Constants.INTAKE_SLIDE.MOTOR_STALL_LIMIT_AMPS,
                Constants.INTAKE_SLIDE.MOTOR_FREE_LIMIT_AMPS);
        m_masterSlideMotor.enableVoltageCompensation(12);

        m_slidePIDController = m_masterSlideMotor.getPIDController();

        m_slidePIDController.setP(Constants.INTAKE_SLIDE.SLIDE_P, 0);
        m_slidePIDController.setI(Constants.INTAKE_SLIDE.SLIDE_I, 0);
        m_slidePIDController.setD(Constants.INTAKE_SLIDE.SLIDE_D, 0);
        m_slidePIDController.setIZone(Constants.INTAKE_SLIDE.SLIDE_IZONE, 0);
        m_slidePIDController.setFF(Constants.INTAKE_SLIDE.SLIDE_FF, 0);

        m_slidePIDController.setOutputRange(Constants.INTAKE_SLIDE.PID_MIN_OUTPUT,
                Constants.INTAKE_SLIDE.PID_MAX_OUTPUT, 0);
        m_slidePIDController.setSmartMotionMaxVelocity(Constants.INTAKE_SLIDE.SMART_MOTION_MAX_VEL_RPM, 0);
        m_slidePIDController.setSmartMotionMinOutputVelocity(Constants.INTAKE_SLIDE.SMART_MOTION_MIN_VEL_RPM, 0);
        m_slidePIDController.setSmartMotionMaxAccel(Constants.INTAKE_SLIDE.SMART_MOTION_MAX_ACC_RPM, 0);
        m_slidePIDController.setSmartMotionAllowedClosedLoopError(Constants.INTAKE_SLIDE.SMART_MOTION_ALLOWED_ERROR, 0);
    }

    public void setSlideRotations(double rotations) {
        setClosedLoopEnabled(true);
        m_targetRotations = rotations;
        m_slidePIDController.setReference(m_targetRotations, ControlType.kSmartMotion);
    }

    public void setSlideAxisSpeed(double axisSpeed) {
        setClosedLoopEnabled(false);
        double motorSpeed = (-axisSpeed) * Constants.INTAKE_SLIDE.AXIS_MAX_SPEED;
        m_masterSlideMotor.set(motorSpeed);
    }

    public void stopSlideMotors() {
        setClosedLoopEnabled(false);
        m_masterSlideMotor.set(0);
    }

    public void resetEncoders() {
        m_masterSlideMotor.getEncoder().setPosition(0);
        m_followerSlideMotor.getEncoder().setPosition(0);
        m_targetRotations = 0;
    }

    public double getSlideRotations() {
        return m_masterSlideMotor.getEncoder().getPosition();
    }

    public boolean isSlideAtRotations() {
        return Utility.isWithinTolerance(getSlideRotations(), m_targetRotations,
                Constants.INTAKE_SLIDE.SMART_MOTION_ALLOWED_ERROR);
    }

    public boolean isExtending() {
        return (m_desiredState == SlideState.Extended && m_currentState != SlideState.Extended);
    }

    public boolean isExtended() {
        return (m_desiredState == SlideState.Extended && m_currentState == SlideState.Extended);
    }

    public boolean isRetracting() {
        return (m_desiredState == SlideState.Retracted && m_currentState != SlideState.Retracted);
    }

    public boolean isRetracted() {
        return (m_desiredState == SlideState.Retracted && m_currentState == SlideState.Retracted);
    }

    public void extend() {
        m_currentState = SlideState.Unknown;
        m_desiredState = SlideState.Extended;
        setSlideRotations(Constants.INTAKE_SLIDE.SLIDE_EXTENDED_ROTATIONS);
    }

    public void retract() {
        m_currentState = SlideState.Unknown;
        m_desiredState = SlideState.Retracted;
        setSlideRotations(Constants.INTAKE_SLIDE.SLIDE_RETRACTED_ROTATIONS);
    }

    public boolean isAtMaxAmps() {
        return m_masterSlideMotor.getOutputCurrent() >= Constants.INTAKE_SLIDE.MAX_AMPS;
    }

    @Override
    public void periodic() {
        if (m_currentState != m_desiredState) {
            switch (m_desiredState) {
                case Extended:
                    extendPeriodic();
                    break;
                case Retracted:
                    retractPeriodic();
                    break;
                default:
                    break;
            }
        }

        if (isClosedLoopEnabled() && isSlideAtRotations() || isAtMaxAmps()) {
            setClosedLoopEnabled(false);
        }
    }

    private void extendPeriodic() {
        if (!isSlideAtRotations()) {
        } else {
            m_currentState = SlideState.Extended;
        }
    }

    private void retractPeriodic() {
        if (!isSlideAtRotations()) {
        } else {
            m_currentState = SlideState.Retracted;
        }
    }

}