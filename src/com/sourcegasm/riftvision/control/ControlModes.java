package com.sourcegasm.riftvision.control;

/**
 * Created by zigapk on 8/20/15.
 */
public enum ControlModes {
    JoystickOnly, OculusYaw, OculusYawPitch, OculusOnly;

    @Override
    public String toString() {
        switch (this){
            case JoystickOnly:
                return "JoystickOnly";
            case OculusYaw:
                return "OculusYaw";
            case OculusYawPitch:
                return "OculusYawPitch";
            case OculusOnly:
                return "OculusOnly";
        }
        return super.toString();
    }
}
