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
		OculusSensors oculusSensors = new OculusSensors();
		oculusSensors.startReceiving();
		JoyStickSensors joystickSensors = new JoyStickSensors();
		joystickSensors.startReceiving();
		DroneController droneController = new DroneController();
		MainController mainController = new MainController();
		mainController.droneController = droneController;
		mainController.oculusSensors = oculusSensors;

		RenderRiftWindow frame = new RenderRiftWindow(mainController);
		//frame.showFrame();
		new RenderManager(droneController, frame);
	}
}