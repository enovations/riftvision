package com.sourcegasm.riftvision.control;

import com.sourcegasm.riftvision.sensors.OculusSensors;

import java.io.IOException;

/**
 * Created by klemen on 19.8.2015. ^selfish
 */
public class MainController {
    public OculusSensors sensors;
    private Thread thread;
    public DroneController droneController;
    private HeightController heightController = new HeightController();
    boolean contiune = false;

    public void startController() {
        heightController = new HeightController();
        YawController yawController = new YawController();
        contiune = true;
        thread = new Thread(() -> {
            while (contiune) {
                float roll = (float) (ExpoController.getExpo(sensors.getSmoothedRool()) / 90.0);
                float pitch = (float) (ExpoController.getExpo(sensors.getSmoothedPitch()) / 90.0);
                /*float roll = (float) (ExpoController.getExpo(sensors.getRawRool()) / 90.0);
                float pitch = (float) (ExpoController.getExpo(sensors.getRawPitch()) / 90.0);*/
                try {
                    /*double oculusYaw = sensors.getSmoothedYaw();
                    double droneYaw = droneController.getNavData().getYaw();
                    double yawMove = yawController.getYawMove(droneController, sensors);
                    System.out.println(oculusYaw + ", " + droneYaw + ", " + yawMove);*/
                    droneController.getDrone().move(roll, -pitch, (float) heightController.getHeightMove(), 0);
                    System.out.println(roll+", "+pitch);

                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    Thread.currentThread().sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
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

    public HeightController getHeightController() {
        return heightController;
    }
}
