package com.sourcegasm.riftvision;

import com.sourcegasm.riftvision.control.DroneController;
import com.sourcegasm.riftvision.render.RenderRiftWindow;
import com.sourcegasm.riftvision.render.RenderManager;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        //OculusSensors.startReceiving();
    	RenderRiftWindow frame = new RenderRiftWindow();
        DroneController controller = new DroneController();
        RenderManager manager = new RenderManager(controller.getDrone(), frame);
        //controller.
    }
}