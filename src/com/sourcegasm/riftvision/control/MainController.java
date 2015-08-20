package com.sourcegasm.riftvision.control;

import com.sourcegasm.riftvision.helper.ControlModes;
import com.sourcegasm.riftvision.sensors.OculusSensors;

import java.io.IOException;

/**
 * Created by klemen on 19.8.2015. ^selfish
 */
public class MainController {
    public OculusSensors oculusSensors;
    private Thread thread;
    public DroneController droneController;
    private HeightController heightController = new HeightController();
    private YawController yawController = new YawController();
    boolean contiune = false;
    ControlModes controlMode = ControlModes.JoystickOnly;

    public void startController() {

    	heightController = new HeightController();
        yawController.setZero(droneController, oculusSensors);
        contiune = true;
    	
        switch (controlMode) {
            case OculusOnly:
            	
                thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        while (contiune) {
                            oculusOnlyControllerLiteration();
                        }
                    }
                });
                thread.start();
                
                break;
            case OculusYaw:
                break;
            case JoystickOnly:
                break;
        }
    }

    private void oculusOnlyControllerLiteration() {

        float roll = (float) (ExpoController.getExpo(oculusSensors.getSmoothedRool()) / 90.0);
        float pitch = (float) (ExpoController.getExpo(oculusSensors.getSmoothedPitch()) / 90.0);
        double yaw = yawController.getYawMove(droneController, oculusSensors);
            try {
                droneController.getDrone().move(roll, -pitch, (float) heightController.getHeightMove(), (float)yaw);
            } catch (IOException e) {
                e.printStackTrace();
            }

        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void stopController() {
        contiune = false;
        try {
            droneController.getDrone().move(0, 0, 0, 0);
        } catch (IOException e) {
            e.printStackTrace();
        }
        heightController.reset();
    }

    public DroneController getDroneController() {
        return droneController;
    }
    public YawController getYawController(){
        return yawController;
    }

    public HeightController getHeightController() {
        return heightController;
    }

    public ControlModes getNextMode() {
        switch (controlMode) {
            case JoystickOnly:
                return ControlModes.OculusYaw;
            case OculusYaw:
                return ControlModes.OculusYawPitch;
            case OculusYawPitch:
                return ControlModes.OculusOnly;
            case OculusOnly:
                return ControlModes.JoystickOnly;
        }
        return null;
    }

    public ControlModes getNextMode(ControlModes mode) {
        switch (mode) {
            case JoystickOnly:
                return ControlModes.OculusYaw;
            case OculusYaw:
                return ControlModes.OculusYawPitch;
            case OculusYawPitch:
                return ControlModes.OculusOnly;
            case OculusOnly:
                return ControlModes.JoystickOnly;
        }
        return null;
    }

    public ControlModes getPreviousMode() {
        switch (controlMode) {
            case OculusOnly:
                return ControlModes.OculusYawPitch;
            case OculusYawPitch:
                return ControlModes.OculusYaw;
            case OculusYaw:
                return ControlModes.JoystickOnly;
            case JoystickOnly:
                return ControlModes.OculusOnly;
        }
        return null;
    }

    public ControlModes getPreviousMode(ControlModes mode) {
        switch (controlMode) {
            case OculusOnly:
                return ControlModes.OculusYawPitch;
            case OculusYawPitch:
                return ControlModes.OculusYaw;
            case OculusYaw:
                return ControlModes.JoystickOnly;
            case JoystickOnly:
                return ControlModes.OculusOnly;
        }
        return null;
    }

    public void setControlMode(ControlModes mode) {
        stopController();
        controlMode = mode;
        System.out.println();
        startController();
    }
}
