// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package com.team2357.frc2023;

import com.revrobotics.CANSparkMax.IdleMode;

public final class Constants {
    public static final class CAN_ID {
        public static final int PIGEON_ID = 5;

        public static final int FRONT_LEFT_MODULE_DRIVE_MOTOR_ID = 11;
        public static final int FRONT_LEFT_MODULE_STEER_MOTOR_ID = 12;
        public static final int FRONT_LEFT_MODULE_STEER_ENCODER_ID = 19;

        public static final int FRONT_RIGHT_MODULE_DRIVE_MOTOR_ID = 13;
        public static final int FRONT_RIGHT_MODULE_STEER_MOTOR_ID = 14;
        public static final int FRONT_RIGHT_MODULE_STEER_ENCODER_ID = 20;

        public static final int BACK_LEFT_MODULE_DRIVE_MOTOR_ID = 15;
        public static final int BACK_LEFT_MODULE_STEER_MOTOR_ID = 16;
        public static final int BACK_LEFT_MODULE_STEER_ENCODER_ID = 21;

        public static final int BACK_RIGHT_MODULE_DRIVE_MOTOR_ID = 17;
        public static final int BACK_RIGHT_MODULE_STEER_MOTOR_ID = 18;
        public static final int BACK_RIGHT_MODULE_STEER_ENCODER_ID = 22;

        public static final int TOP_SHOOTER_MOTOR_ID = -1;
        public static final int BOTTOM_SHOOTER_MOTOR_ID = -1;

        public static final int MASTER_SLIDE_MOTOR_ID = -1;
        public static final int FOLLOWER_SLIDE_MOTOR_ID = -1;
    }

    public static final class SHOOTER {
        public static final boolean TOP_MOTOR_INVERTED = false;
        public static final boolean BOTTOM_MOTOR_INVERTED = false;

        public static final double TOP_MOTOR_HIGH_PERCENT_OUTPUT = 0.0;
        public static final double BOTTOM_MOTOR_HIGH_PERCENT_OUTPUT = 0.0;
        
        public static final double TOP_MOTOR_MID_PERCENT_OUTPUT = 0.0;
        public static final double BOTTOM_MOTOR_MID_PERCENT_OUTPUT = 0.0;
        
        public static final double TOP_MOTOR_LOW_PERCENT_OUTPUT = 0.0;
        public static final double BOTTOM_MOTOR_LOW_PERCENT_OUTPUT = 0.0;

        public static final int TOP_MOTOR_LIMIT_AMPS = 10;
        public static final int BOTTOM_MOTOR_LIMIT_AMPS = 10;

        public static final double TOP_MOTOR_PID_P = 0;
        public static final double TOP_MOTOR_PID_I = 0;
        public static final double TOP_MOTOR_PID_D = 0;

        public static final double BOTTOM_MOTOR_PID_P = 0;
        public static final double BOTTOM_MOTOR_PID_I = 0;
        public static final double BOTTOM_MOTOR_PID_D = 0;

        public static final int NEO_MAXIMUM_RPM = 5676;
    }

    public static final class INTAKE_ROLLER {
        public static final boolean TOP_MOTOR_INVERTED = false;
        public static final boolean BOTTOM_MOTOR_INVERTED = false;

        public static final double TOP_MOTOR_INTAKE_PERCENT_OUTPUT = 0.0;
        public static final double BOTTOM_MOTOR_INTAKE_PERCENT_OUTPUT = 0.0;
        
        public static final double TOP_MOTOR_EJECT_PERCENT_OUTPUT = 0.0;
        public static final double BOTTOM_MOTOR_EJECT_PERCENT_OUTPUT = 0.0;
        
        public static final double TOP_MOTOR_INDEX_PERCENT_OUTPUT = 0.0;
        public static final double BOTTOM_MOTOR_INDEX_PERCENT_OUTPUT = 0.0;

        public static final int TOP_MOTOR_LIMIT_AMPS = 10;
        public static final int BOTTOM_MOTOR_LIMIT_AMPS = 10;
    }

    public static final class INTAKE_SLIDE {
        public static final boolean MASTER_MOTOR_INVERTED = false;
        public static final boolean FOLLOWER_MOTOR_INVERTED = false;

        public static final IdleMode IDLE_MODE = IdleMode.kBrake;
        public static final int MOTOR_STALL_LIMIT_AMPS = 10;
        public static final int MOTOR_FREE_LIMIT_AMPS = 10;

        public static final double SLIDE_P = 0.0;
        public static final double SLIDE_I = 0.0;
        public static final double SLIDE_D = 0.0;
        public static final double SLIDE_IZONE = 0.0;
        public static final double SLIDE_FF = 0.0;

        public static final double PID_MIN_OUTPUT = 0.0;
        public static final double PID_MAX_OUTPUT = 0.0;
        public static final double SMART_MOTION_MAX_VEL_RPM = 0.0;
        public static final double SMART_MOTION_MIN_VEL_RPM = 0.0;
        public static final double SMART_MOTION_MAX_ACC_RPM = 0.0;
        public static final double SMART_MOTION_ALLOWED_ERROR = 0.0;

        public static final double SLIDE_AXIS_MAX_SPEED = 0.05;

        public static final double SLIDE_EXTENDED_ROTATIONS = 0;
        public static final double SLIDE_RETRACTED_ROTATIONS = 0;

    }

    public static final class SWERVE {

    }

    public static final class CONTROLLER {
        public static final int DRIVE_CONTROLLER_PORT = 0;
        public static final int OPERATOR_CONTROLLER_PORT = 1;

        public static final double DRIVE_CONTROLLER_DEADBAND = 0.05;

        public static final double RUMBLE_INTENSITY = 0.5;
        public static final double RUMBLE_TIMEOUT_SECONDS_ON_TELEOP_AUTO = 1;
    }

}
