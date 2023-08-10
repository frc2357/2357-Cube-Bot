package com.team2357.frc2023.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.team2357.frc2023.Constants;
import com.team2357.frc2023.Constants.SHOOTER;
import com.team2357.lib.subsystems.LimelightSubsystem;
import com.team2357.lib.util.RobotMath;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class ShooterSubsystem extends SubsystemBase {

    private CANSparkMax m_topShooterMotor;
    private CANSparkMax m_bottomShooterMotor;
    private SHOOTER m_config;
    private boolean m_isClosedLoopEnabled;
    private static final int m_neoMaxRPM = 5676;
    
    public ShooterSubsystem() {
        m_topShooterMotor = new CANSparkMax(Constants.CAN_ID.TOP_SHOOTER_MOTOR_ID, MotorType.kBrushless);
        m_bottomShooterMotor = new CANSparkMax(Constants.CAN_ID.BOTTOM_SHOOTER_MOTOR_ID, MotorType.kBrushless);
        m_isClosedLoopEnabled = false;
    }

    public void configure(SHOOTER config){
        m_config = config;

        m_topShooterMotor.setInverted(m_config.TOP_MOTOR_INVERTED);
        m_bottomShooterMotor.setInverted(m_config.BOTTOM_MOTOR_INVERTED);

        m_topShooterMotor.setSmartCurrentLimit(m_config.TOP_MOTOR_LIMIT_AMPS);
        m_bottomShooterMotor.setSmartCurrentLimit(m_config.BOTTOM_MOTOR_LIMIT_AMPS);

        m_topShooterMotor.getPIDController().setP(m_config.TOP_MOTOR_PID_P);
        m_topShooterMotor.getPIDController().setI(m_config.TOP_MOTOR_PID_I);
        m_topShooterMotor.getPIDController().setD(m_config.TOP_MOTOR_PID_D);

        m_bottomShooterMotor.getPIDController().setP(m_config.BOTTOM_MOTOR_PID_P);
        m_bottomShooterMotor.getPIDController().setI(m_config.BOTTOM_MOTOR_PID_I);
        m_bottomShooterMotor.getPIDController().setD(m_config.BOTTOM_MOTOR_PID_D);
    }

    public void runShooter(double topPercentOutput, double bottomPercentOutput) {
        m_topShooterMotor.set(topPercentOutput);
        m_bottomShooterMotor.set(bottomPercentOutput);
    }

    public void stopShooter() {
        m_topShooterMotor.set(0.0);
        m_bottomShooterMotor.set(0.0);
        m_isClosedLoopEnabled = false;
    }

    public void setClosedLoopEnabled(){
        m_isClosedLoopEnabled = true;
    }

     // {degrees, top shooter RPM, bottom shooter RPM}
    private static final double[][] degreesToRPM = {
        { 20, 0, 0 },
        { 15, 0, 0 },
        { 10, 0, 0 },
        { 5, 0, 0 },
        { 0, 0, 0 },
        { -5.0, 0, 0 },
        { -10, 0, 0 },
        { -15, 0, 0 },
        { -20, 0, 0 }
    };

    @Override
    public void periodic(){
        if(m_isClosedLoopEnabled){

        }

    }

    /**
     * Supposed to set the shooter motors output based on a vision targets angle
     * @param angle The angle of the target
     */
    private void setShooterRPMVision(double angle){
        int curveSegmentIndex = RobotMath.getCurveSegmentIndex(degreesToRPM, angle);
        if (curveSegmentIndex == -1) {
            System.err.println("----- Curve segment index out of bounds -----");
            return;
        }

        double[] pointA = degreesToRPM[curveSegmentIndex];
        double[] pointB = degreesToRPM[curveSegmentIndex + 1];

        double highAngle = pointA[0];
        double lowAngle = pointB[0];
        double highBottomRPMs = pointA[1];
        double lowBottomRPMs = pointB[1];
        double highTopRPMs = pointA[2];
        double lowTopRPMs = pointB[2];

        double bottomRpms = RobotMath.lineralyInterpolate(highBottomRPMs, lowBottomRPMs, highAngle, lowAngle, angle);

        double topRpms = RobotMath.lineralyInterpolate(highTopRPMs, lowTopRPMs, highAngle, lowAngle, angle);

        if (Double.isNaN(bottomRpms) || Double.isNaN(topRpms)) {
            System.err.println("----- Invalid shooter rpms -----");
            return;
        }

        //the RPM is divided by the empirical neo max RPM to get the percentage output
        runShooter(topRpms/m_neoMaxRPM, bottomRpms/m_neoMaxRPM);
    }
    

}
