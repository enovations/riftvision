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
                            oculusOnlyControllerLiteration(false);
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

    private void oculusOnlyControllerLiteration(boolean debug) {

        float roll = (float) (ExpoController.getExpo(oculusSensors.getSmoothedRool()) / 90.0);
        float pitch = (float) (ExpoController.getExpo(oculusSensors.getSmoothedPitch()) / 90.0);

        if(debug){
            double oculusYaw = oculusSensors.getSmoothedYaw();
            double droneYaw = droneController.getNavData().getYaw();
            double yawMove = yawController.getYawMove(droneController, oculusSensors);
            System.out.println(oculusYaw + ", " + droneYaw + ", " + yawMove);
        }else {
            try {
                double yawMove = yawController.getYawMove(droneController, oculusSensors);
                //System.out.println(yawMove);
                droneController.getDrone().move(roll, -pitch, (float) heightController.getHeightMove(), (float)yawMove);
                //droneController.getDrone().move(0, -pitch, (float) heightController.getHeightMove(), (float)yawMove);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try {
            int wait = 50;
            if(debug) wait = 150;
            Thread.currentThread().sleep(wait);
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

    public void setControlMode(ControlModes mode) {
        stopController();
        controlMode = mode;
        startController();
    }
}
