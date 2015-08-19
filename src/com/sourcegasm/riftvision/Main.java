package com.sourcegasm.riftvision;

import com.sourcegasm.riftvision.control.DroneController;
import com.sourcegasm.riftvision.render.RenderRiftWindow;
import com.sourcegasm.riftvision.render.RenderManager;
import com.sourcegasm.riftvision.sensors.OculusSensors;

import java.io.IOException;

public class Main {

    public static OculusSensors sensors;
    public static DroneController controller;

    public static void main(String[] args) throws IOException {
        sensors = new OculusSensors();
        sensors.startReceiving();
        RenderRiftWindow frame = new RenderRiftWindow();
        controller = new DroneController();
        RenderManager manager = new RenderManager(controller.getDrone(), frame);
    }
}