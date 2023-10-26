package com.team2357.lib.triggers;

import com.team2357.lib.util.ControllerAxis;
import com.team2357.lib.util.XboxRaw;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.button.Trigger;

public class AxisThresholdTrigger extends Trigger {

  private double triggerThreshold;
  private ControllerAxis controllerAxis;

  public AxisThresholdTrigger(
      XboxController controller,
      XboxRaw triggerright,
      double triggerThreshold) {
    this.triggerThreshold = triggerThreshold;
    this.controllerAxis = () -> {
      switch (triggerright) {
        case StickLeftX:
          return controller.getLeftX();
        case StickLeftY:
          return controller.getLeftY();
        case TriggerLeft:
          return controller.getLeftTriggerAxis();
        case TriggerRight:
          return controller.getRightTriggerAxis();
        case StickRightX:
          return controller.getRightX();
        case StickRightY:
          return controller.getRightY();
      }
      return 0;
    };
  }

  @Override
  public boolean getAsBoolean() {
    return (controllerAxis.getAxisValue() > triggerThreshold);
  }
}
