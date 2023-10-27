// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package com.team2357.frc2023;

import com.revrobotics.CANSparkMax.IdleMode;

import edu.wpi.first.math.util.Units;

public final class Constants {
    public static final class CAN_ID {
        public static final int PIGEON_ID = 5;

        public static final int FRONT_LEFT_MODULE_DRIVE_MOTOR_ID = 11;
        public static final int FRONT_LEFT_MODULE_STEER_MOTOR_ID = 12;

        public static final int FRONT_RIGHT_MODULE_DRIVE_MOTOR_ID = 13;
        public static final int FRONT_RIGHT_MODULE_STEER_MOTOR_ID = 14;

        public static final int BACK_LEFT_MODULE_DRIVE_MOTOR_ID = 15;
        public static final int BACK_LEFT_MODULE_STEER_MOTOR_ID = 16;

        public static final int BACK_RIGHT_MODULE_DRIVE_MOTOR_ID = 17;
        public static final int BACK_RIGHT_MODULE_STEER_MOTOR_ID = 18;

        public static final int BOTTOM_INTAKE_ROLLER_MOTOR_ID = 19;
        public static final int TOP_INTAKE_ROLLER_MOTOR_ID = 20;

        public static final int MASTER_INTAKE_SLIDE_MOTOR_ID = 21;
        public static final int FOLLOWER_INTAKE_SLIDE_MOTOR_ID = 22;

        public static final int TOP_SHOOTER_MOTOR_ID = 23;
        public static final int BOTTOM_SHOOTER_MOTOR_ID = 24;
    }

    public static final class INTAKE_ROLLER {
        // Configuration
        public static final boolean TOP_MOTOR_INVERTED = false;
        public static final boolean BOTTOM_MOTOR_INVERTED = true;
        
        public static final double TOP_AXIS_MAX_SPEED = 1;
        public static final double BOTTOM_AXIS_MAX_SPEED = 1;

        public static final int TOP_MOTOR_STALL_LIMIT_AMPS = 30;
        public static final int TOP_MOTOR_FREE_LIMIT_AMPS = 30;

        public static final int BOTTOM_MOTOR_STALL_LIMIT_AMPS = 30;
        public static final int BOTTOM_MOTOR_FREE_LIMIT_AMPS = 30;

        public static final int REVERSE_INDEX_RPM_DROP = 100;

        public static final IdleMode MOTOR_IDLE_MODE = IdleMode.kBrake;

        public static final double TOP_MOTOR_P = 0.0;
        public static final double TOP_MOTOR_I = 0.0;
        public static final double TOP_MOTOR_D = 0.0;
        public static final double TOP_MOTOR_FF = 0.000193;

        public static final double BOTTOM_MOTOR_P = 0.0;
        public static final double BOTTOM_MOTOR_I = 0.0;
        public static final double BOTTOM_MOTOR_D = 0.0;
        public static final double BOTTOM_MOTOR_FF = 0.000189;

        // Motor speeds
        public static final double TOP_MOTOR_INTAKE_RPMS = +2500;
        public static final double BOTTOM_MOTOR_INTAKE_RPMS = +5000;
        
        public static final double TOP_MOTOR_EJECT_RPMS = -2500;
        public static final double BOTTOM_MOTOR_EJECT_RPMS = +5000;
        
        public static final double TOP_MOTOR_INDEX_RPMS = +3000;
        public static final double BOTTOM_MOTOR_INDEX_RPMS = -3000;

        public static final double TOP_MOTOR_ROLL_RPMS = +2000;
        public static final double BOTTOM_MOTOR_ROLL_RPMS = +4000;

        public static final double TOP_MOTOR_REVERSE_INDEX_RPMS = -1000;
        public static final double BOTTOM_MOTOR_REVERSE_INDEX_RPMS = 1000;
    }

    public static final class SHOOTER {
        public static final boolean TOP_MOTOR_INVERTED = false;
        public static final boolean BOTTOM_MOTOR_INVERTED = false;

        public static final int TOP_MOTOR_STALL_LIMIT_AMPS = 40;
        public static final int TOP_MOTOR_FREE_LIMIT_AMPS = 40;

        public static final int BOTTOM_MOTOR_STALL_LIMIT_AMPS = 40;
        public static final int BOTTOM_MOTOR_FREE_LIMIT_AMPS = 40;

        public static final IdleMode MOTOR_IDLE_MODE = IdleMode.kCoast;

        public static final double TOP_MOTOR_P = 0.0;
        public static final double TOP_MOTOR_I = 0.0;
        public static final double TOP_MOTOR_D = 0.0;
        public static final double TOP_MOTOR_FF = 0.00018;

        public static final double BOTTOM_MOTOR_P = 0.0;
        public static final double BOTTOM_MOTOR_I = 0.0;
        public static final double BOTTOM_MOTOR_D = 0.0;
        public static final double BOTTOM_MOTOR_FF = 0.00018075;

        public static final double TOP_MOTOR_LOW_RPMS = 500;
        public static final double BOTTOM_MOTOR_LOW_RPMS = 500;
        public static final String SMART_DASHBOARD_SHOOT_LOW_TOP = "ShootLowTop";
        public static final String SMART_DASHBOARD_SHOOT_LOW_BOTTOM = "ShootLowBottom";

        public static final double TOP_MOTOR_MID_RPMS = 850;
        public static final double BOTTOM_MOTOR_MID_RPMS = 825;
        public static final String SMART_DASHBOARD_SHOOT_MID_TOP = "ShootMidTop";
        public static final String SMART_DASHBOARD_SHOOT_MID_BOTTOM = "ShootMidBottom";

        public static final double TOP_MOTOR_HIGH_RPMS = 1050;
        public static final double BOTTOM_MOTOR_HIGH_RPMS = 1500;
        public static final String SMART_DASHBOARD_SHOOT_HIGH_TOP = "ShootHighTop";
        public static final String SMART_DASHBOARD_SHOOT_HIGH_BOTTOM = "ShootHighBottom";

        public static final double TOP_MOTOR_FAR_RPMS = 2000;
        public static final double BOTTOM_MOTOR_FAR_RPMS = 500;

        public static final double TOP_MOTOR_INTAKE_RPMS = -1500;
        public static final double BOTTOM_MOTOR_INTAKE_RPMS = -1500;
        public static final String SMART_DASHBOARD_SHOOT_FAR_TOP = "ShootFarTop";
        public static final String SMART_DASHBOARD_SHOOT_FAR_BOTTOM = "ShootFarBottom";
    }

    public static final class INTAKE_SLIDE {
        public static final boolean MASTER_MOTOR_INVERTED = true;
        public static final boolean FOLLOWER_MOTOR_INVERTED = true;

        public static final IdleMode IDLE_MODE = IdleMode.kBrake;

        public static final int MOTOR_STALL_LIMIT_AMPS = 15;
        public static final int MOTOR_FREE_LIMIT_AMPS = 15;

        public static final double AXIS_MAX_SPEED = 0.5;

        public static final double SLIDE_EXTENDED_ROTATIONS = 44;
        public static final double SLIDE_RETRACTED_ROTATIONS = 3;
        public static final double SLIDE_PARTIAL_EXTEND_ROTATIONS = 45;
        public static final double SLIDE_PARTIAL_RETRACT_ROTATIONS = 2;

        public static final double MAX_AMPS = 20;

        // PID
        public static final double SLIDE_P = 0.00001;
        public static final double SLIDE_I = 0;
        public static final double SLIDE_D = 0;
        public static final double SLIDE_IZONE = 0;
        public static final double SLIDE_FF = 0.00025;

        // Smart motion
        public static final double PID_MIN_OUTPUT = -1;
        public static final double PID_MAX_OUTPUT = 1;
        public static final double SMART_MOTION_MAX_VEL_RPM = 4600;
        public static final double SMART_MOTION_MIN_VEL_RPM = 0;
        public static final double SMART_MOTION_MAX_ACC_RPM = 4600 * 2;
        public static final double SMART_MOTION_ALLOWED_ERROR = 0.25;
    }


    public static final class SWERVE {
        public static final double TRACKWIDTH_METERS = Units.inchesToMeters(18.75);
        public static final double WHEELBASE_METERS = Units.inchesToMeters(18.75);

        public static final double ABSOLUTE_ENCODER_CONVERSION_FACTOR = 360;

        // TODO: get some drive practice and tune these accordingly
        public static final double MAX_VELOCITY_METERS_PER_SECOND = 14.5;
        public static final double MAX_ANGULAR_VELOCITY_RADIANS_PER_SECOND = 4;

        public static final double TIME_TO_COAST_SECONDS = 5000;
    }

    public static final class CONTROLLER {
        public static final int DRIVE_CONTROLLER_PORT = 0;
        public static final int OPERATOR_CONTROLLER_PORT = 1;

        public static final double DRIVE_CONTROLLER_DEADBAND = 0.05;
        public static final double OPERATOR_CONTROLLER_DEADBAND = 0.1;

        public static final double RUMBLE_INTENSITY = 0.5;
        public static final double RUMBLE_TIMEOUT_SECONDS_ON_TELEOP_AUTO = 1;
    }

}
