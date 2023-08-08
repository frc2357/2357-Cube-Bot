package com.team2357.frc2023.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.SparkMaxPIDController;
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
        m_masterSlideMotor = new CANSparkMax(Constants.CAN_ID.MASTER_SLIDE_MOTOR_ID, null);
        m_followerSlideMotor = new CANSparkMax(Constants.CAN_ID.FOLLOWER_SLIDE_MOTOR_ID, null);

        configure();
    }

    private void configure() {
        m_masterSlideMotor.setInverted(Constants.INTAKE_SLIDE.MASTER_MOTOR_INVERTED);
        m_followerSlideMotor.setInverted(Constants.INTAKE_SLIDE.FOLLOWER_MOTOR_INVERTED);

        m_followerSlideMotor.follow(m_masterSlideMotor);

        configureSlideMotor(m_masterSlideMotor);
        configureSlideMotor(m_followerSlideMotor);

        m_slidePIDController = m_masterSlideMotor.getPIDController();

        configureSlidePID(m_slidePIDController);
    }

    private void configureSlideMotor(CANSparkMax motor) {
        motor.setIdleMode(Constants.INTAKE_SLIDE.IDLE_MODE);
        motor.setSmartCurrentLimit(Constants.INTAKE_SLIDE.MOTOR_STALL_LIMIT_AMPS,
                Constants.INTAKE_SLIDE.MOTOR_FREE_LIMIT_AMPS);
        motor.enableVoltageCompensation(12);
    }

    private void configureSlidePID(SparkMaxPIDController pidController) {
        pidController.setP(Constants.INTAKE_SLIDE.SLIDE_P, 0);
        pidController.setI(Constants.INTAKE_SLIDE.SLIDE_I, 0);
        pidController.setD(Constants.INTAKE_SLIDE.SLIDE_D, 0);
        pidController.setIZone(Constants.INTAKE_SLIDE.SLIDE_IZONE, 0);
        pidController.setFF(Constants.INTAKE_SLIDE.SLIDE_FF, 0);

        // smart motion
        pidController.setOutputRange(Constants.INTAKE_SLIDE.PID_MIN_OUTPUT, Constants.INTAKE_SLIDE.PID_MAX_OUTPUT, 0);
        pidController.setSmartMotionMaxVelocity(Constants.INTAKE_SLIDE.SMART_MOTION_MAX_VEL_RPM, 0);
        pidController.setSmartMotionMinOutputVelocity(Constants.INTAKE_SLIDE.SMART_MOTION_MIN_VEL_RPM, 0);
        pidController.setSmartMotionMaxAccel(Constants.INTAKE_SLIDE.SMART_MOTION_MAX_ACC_RPM, 0);
        pidController.setSmartMotionAllowedClosedLoopError(Constants.INTAKE_SLIDE.SMART_MOTION_ALLOWED_ERROR, 0);
    }

    public void setSlideRotations(double rotations) {
        setClosedLoopEnabled(true);
        m_targetRotations = rotations;
        m_slidePIDController.setReference(m_targetRotations, CANSparkMax.ControlType.kSmartMotion);
    }

    public void setSlideAxisSpeed(double axisSpeed) {
        setClosedLoopEnabled(false);
        double motorSpeed = (-axisSpeed) * Constants.INTAKE_SLIDE.SLIDE_AXIS_MAX_SPEED;
        m_masterSlideMotor.set(motorSpeed);
    }

    public void stopWinchMotor() {
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

        if (isClosedLoopEnabled() && isSlideAtRotations()) {
            setClosedLoopEnabled(false);
        }
    }

    private void extendPeriodic() {
        if (!isSlideAtRotations()) {
        } else if (isSlideAtRotations()) {
            m_currentState = SlideState.Extended;
        }
    }

    private void retractPeriodic() {
        if (!isSlideAtRotations()) {
        } else if (isSlideAtRotations()) {
            m_currentState = SlideState.Retracted;
        }
    }

}
