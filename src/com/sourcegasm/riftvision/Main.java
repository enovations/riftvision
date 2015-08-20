package com.sourcegasm.riftvision;

import com.sourcegasm.riftvision.control.DroneController;
import com.sourcegasm.riftvision.control.MainController;
import com.sourcegasm.riftvision.render.RenderManager;
import com.sourcegasm.riftvision.render.RenderRiftWindow;
import com.sourcegasm.riftvision.sensors.JoyStickSensors;
import com.sourcegasm.riftvision.sensors.OculusSensors;
import com.sourcegasm.riftvision.sensors.SensorPythonGateway;

import java.io.IOException;

public class Main {

	public static void main(String[] args) throws IOException {
		SensorPythonGateway.startPythonScripts();
		OculusSensors oculus_sensors = new OculusSensors();
		oculus_sensors.startReceiving();
		JoyStickSensors joystick_sensors = new JoyStickSensors();
		joystick_sensors.startReceiving();
		DroneController droneController = new DroneController();
		MainController mainController = new MainController();
		mainController.droneController = droneController;
		mainController.oculusSensors = oculus_sensors;
        mainController.startController();

		RenderRiftWindow frame = new RenderRiftWindow(mainController);
		new RenderManager(droneController, frame);
	}
}