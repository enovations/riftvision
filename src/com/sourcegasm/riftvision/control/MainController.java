package com.sourcegasm.riftvision.control;

import java.io.IOException;

import com.sourcegasm.riftvision.sensors.JoyStickSensors;
import com.sourcegasm.riftvision.sensors.OculusSensors;

public class MainController {
	
    public OculusSensors oculusSensors;
    public JoyStickSensors joyStickSensors;
    
    private Thread thread;
    public DroneController droneController;
    private YawController yawController = new YawController();
    boolean contiune = false;
    ControlModes controlMode = ControlModes.JoystickOnly;

    public void startController() {

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
            	
                thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        while (contiune) {
                        	oculusYawControllerLiteration();
                        }
                    }
                });
                thread.start();
            	
                break;
                
            case OculusYawPitch:
            	
                thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        while (contiune) {
                        	oculusYawPitchControllerLiteration();
                        }
                    }
                });
                thread.start();
            	
                break;
                
            case JoystickOnly:
            	
                thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        while (contiune) {
                        	joysitckOnlyControllerLiteration();
                        }
                    }
                });
                thread.start();
            	
                break;
        }
    }
    
    private void joysitckOnlyControllerLiteration() {
    	
            try {
                droneController.getDrone().move((float)joyStickSensors.getRawRool(), (float)joyStickSensors.getRawPitch(), (float)joyStickSensors.getRawHeight(), (float)joyStickSensors.getRawYaw());
            } catch (IOException e) {
                e.printStackTrace();
            }

        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    	
    }
    
    private void oculusYawControllerLiteration() {

    	double yaw = yawController.getYawMove(droneController, oculusSensors);
            try {
            	droneController.getDrone().move((float)joyStickSensors.getRawRool(), (float)joyStickSensors.getRawPitch(), (float)joyStickSensors.getRawHeight(), (float)yaw);
            } catch (IOException e) {
                e.printStackTrace();
            }

        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    
    private void oculusYawPitchControllerLiteration() {

        float pitch = (float) (ExpoController.getExpo(oculusSensors.getSmoothedPitch()) / 90.0);
        double yaw = yawController.getYawMove(droneController, oculusSensors);
        try {
        	droneController.getDrone().move((float)joyStickSensors.getRawRool(), (float)pitch, (float)joyStickSensors.getRawHeight(), (float)yaw);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    
    private void oculusOnlyControllerLiteration() {

        float roll = (float) (ExpoController.getExpo(oculusSensors.getSmoothedRool()) / 90.0);
        float pitch = (float) (ExpoController.getExpo(oculusSensors.getSmoothedPitch()) / 90.0);
        double yaw = yawController.getYawMove(droneController, oculusSensors);
            try {
                droneController.getDrone().move(roll, -pitch, (float)joyStickSensors.getRawHeight(), (float)yaw);
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
    }

    public DroneController getDroneController() {
        return droneController;
    }
    public YawController getYawController(){
        return yawController;
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
        startController();
    }
}
