package com.team2357.frc2023.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.team2357.frc2023.Constants;
import com.team2357.frc2023.Constants.SHOOTER;
import com.team2357.lib.subsystems.ClosedLoopSubsystem;
import com.team2357.lib.subsystems.LimelightSubsystem;
import com.team2357.lib.util.RobotMath;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class ShooterSubsystem extends ClosedLoopSubsystem{

    private CANSparkMax m_topShooterMotor;
    private CANSparkMax m_bottomShooterMotor;
    
    public ShooterSubsystem() {
        m_topShooterMotor = new CANSparkMax(Constants.CAN_ID.TOP_SHOOTER_MOTOR_ID, MotorType.kBrushless);
        m_bottomShooterMotor = new CANSparkMax(Constants.CAN_ID.BOTTOM_SHOOTER_MOTOR_ID, MotorType.kBrushless);
    }

    public void configure(){

        m_topShooterMotor.setInverted(Constants.SHOOTER.TOP_MOTOR_INVERTED);
        m_bottomShooterMotor.setInverted(Constants.SHOOTER.BOTTOM_MOTOR_INVERTED);

        m_topShooterMotor.setSmartCurrentLimit(Constants.SHOOTER.TOP_MOTOR_LIMIT_AMPS);
        m_bottomShooterMotor.setSmartCurrentLimit(Constants.SHOOTER.BOTTOM_MOTOR_LIMIT_AMPS);

        m_topShooterMotor.getPIDController().setP(Constants.SHOOTER.TOP_MOTOR_PID_P);
        m_topShooterMotor.getPIDController().setI(Constants.SHOOTER.TOP_MOTOR_PID_I);
        m_topShooterMotor.getPIDController().setD(Constants.SHOOTER.TOP_MOTOR_PID_D);

        m_bottomShooterMotor.getPIDController().setP(Constants.SHOOTER.BOTTOM_MOTOR_PID_P);
        m_bottomShooterMotor.getPIDController().setI(Constants.SHOOTER.BOTTOM_MOTOR_PID_I);
        m_bottomShooterMotor.getPIDController().setD(Constants.SHOOTER.BOTTOM_MOTOR_PID_D);
    }

    public void runShooter(double topPercentOutput, double bottomPercentOutput) {
        m_topShooterMotor.set(topPercentOutput);
        m_bottomShooterMotor.set(bottomPercentOutput);
    }

    public void stopShooter() {
        m_topShooterMotor.set(0.0);
        m_bottomShooterMotor.set(0.0);
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
        if(isClosedLoopEnabled()){

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

        double bottomRpms = RobotMath.linearlyInterpolate(highBottomRPMs, lowBottomRPMs, highAngle, lowAngle, angle);

        double topRpms = RobotMath.linearlyInterpolate(highTopRPMs, lowTopRPMs, highAngle, lowAngle, angle);

        if (Double.isNaN(bottomRpms) || Double.isNaN(topRpms)) {
            System.err.println("----- Invalid shooter rpms -----");
            return;
        }

        //the RPM is divided by the empirical neo max RPM to get the percentage output
        runShooter(topRpms/Constants.SHOOTER.NEO_MAXIMUM_RPM, bottomRpms/Constants.SHOOTER.NEO_MAXIMUM_RPM);
    }
    

}
