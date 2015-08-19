package com.sourcegasm.riftvision;

import com.sourcegasm.riftvision.control.DroneController;
import com.sourcegasm.riftvision.render.RenderFrame;
import com.sourcegasm.riftvision.render.RenderManager;
import com.sourcegasm.riftvision.sensors.OculusSensors;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        //OculusSensors.startReceiving();
    	RenderFrame frame = new RenderFrame();
        DroneController controller = new DroneController();
        RenderManager manager = new RenderManager(controller.getDrone(), frame);
        //controller.
    }
}