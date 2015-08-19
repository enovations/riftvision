package com.sourcegasm.riftvision;

import com.sourcegasm.riftvision.control.DroneController;
import com.sourcegasm.riftvision.render.RenderRiftWindow;
import com.sourcegasm.riftvision.render.RenderManager;
import com.sourcegasm.riftvision.sensors.OculusSensors;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        OculusSensors sensors = new OculusSensors();
        sensors.startReceiving();
    	DroneController controller = new DroneController();
        RenderRiftWindow frame = new RenderRiftWindow(controller);
        RenderManager manager = new RenderManager(controller.getDrone(), frame);

        while(true){
            controller.getDrone().move((float) (sensors.getSmoothedRool()/120.0), (float) (sensors.getSmoothedPitch()/120.0), 0, 0);
            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}