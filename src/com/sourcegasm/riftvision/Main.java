package com.sourcegasm.riftvision;

import java.io.IOException;

import com.sourcegasm.riftvision.control.DroneController;
import com.sourcegasm.riftvision.control.MainController;
import com.sourcegasm.riftvision.render.RenderManager;
import com.sourcegasm.riftvision.render.RenderRiftWindow;
import com.sourcegasm.riftvision.sensors.OculusSensors;

public class Main {

	public static void main(String[] args) throws IOException {
		OculusSensors sensors = new OculusSensors();
		sensors.startReceiving();
		DroneController droneController = new DroneController();
		MainController mainController = new MainController();
		mainController.droneController = droneController;
		mainController.sensors = sensors;
		RenderRiftWindow frame = new RenderRiftWindow(mainController);
		new RenderManager(droneController, frame);
	}
}