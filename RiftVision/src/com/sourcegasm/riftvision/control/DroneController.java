package com.sourcegasm.riftvision.control;

import com.codeminders.ardrone.ARDrone;
import com.codeminders.ardrone.NavData;
import com.codeminders.ardrone.NavDataListener;
import com.codeminders.ardrone.util.BufferedImageVideoListener;

import java.awt.image.BufferedImage;
import java.awt.image.RescaleOp;
import java.io.IOException;
import java.net.UnknownHostException;

//Controlling the quad copter based on enabled mode

public class DroneController {

    ARDrone drone = null;
    NavData data = null;

    BufferedImage quadImage = null;

    public DroneController() {
        try {

            drone = new ARDrone();
            drone.connect();

            drone.addImageListener(new BufferedImageVideoListener() {

                @Override
                public void imageReceived(BufferedImage image) {
                    RescaleOp rescaleOp = new RescaleOp(1.4f, 50, null);
                    rescaleOp.filter(image, image);
                    quadImage = image;
                }
            });

            drone.addNavDataListener(new NavDataListener() {

                @Override
                public void navDataReceived(NavData fdata) {
                    data = fdata;
                }
            });

        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ARDrone getDrone() {
        return drone;
    }

}