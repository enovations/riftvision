package com.sourcegasm.riftvision;

import com.sourcegasm.riftvision.control.DroneController;
import com.sourcegasm.riftvision.control.MainController;
import com.sourcegasm.riftvision.opengl.OpenGLWindow;
import com.sourcegasm.riftvision.render.RenderManager;
import com.sourcegasm.riftvision.render.RenderRiftWindow;
import com.sourcegasm.riftvision.sensors.JoyStickSensors;
import com.sourcegasm.riftvision.sensors.OculusSensors;

import java.io.IOException;

public class Main {

	public static void main(String[] args) throws IOException {
		DroneController droneController = new DroneController();
		MainController mainController = new MainController();
		OculusSensors oculusSensors = new OculusSensors();
		oculusSensors.startReceiving();
		JoyStickSensors joystickSensors = new JoyStickSensors(mainController);
		joystickSensors.startReceiving();
		mainController.droneController = droneController;
		mainController.oculusSensors = oculusSensors;
		mainController.joyStickSensors = joystickSensors;

		OpenGLWindow frame = new OpenGLWindow();
		frame.start();
				
		new RenderManager(droneController, frame);
		
	}
}