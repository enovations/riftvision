package com.sourcegasm.riftvision.control;

/**
 * Created by zigapk on 8/20/15.
 */
public enum ControlModes {
	JoystickOnly, OculusYaw, OculusYawPitch, OculusOnly;

	@Override
	public String toString() {
		switch (this) {
		case JoystickOnly:
			return "Joystick";
		case OculusYaw:
			return "Oculus Y";
		case OculusYawPitch:
			return "Oculus Y&P";
		case OculusOnly:
			return "Oculus Only";
		}
		return super.toString();
	}
}
