package com.sourcegasm.riftvision;

import java.io.IOException;

import com.sourcegasm.riftvision.control.DroneController;
import com.sourcegasm.riftvision.control.MainController;
import com.sourcegasm.riftvision.game.LapTimer;
import com.sourcegasm.riftvision.opengl.OpenGLWindow;
import com.sourcegasm.riftvision.render.RenderManager;
import com.sourcegasm.riftvision.sensors.JoyStickSensors;
import com.sourcegasm.riftvision.sensors.OculusSensors;

public class Main {

	public static void main(String[] args) throws IOException {
		
		final LapTimer timer = new LapTimer();

		final DroneController droneController = new DroneController();
		final MainController mainController = new MainController();

		final OculusSensors oculusSensors = new OculusSensors();
		oculusSensors.startReceiving();

		final JoyStickSensors joystickSensors = new JoyStickSensors(mainController, timer);
		joystickSensors.startReceiving();

		mainController.droneController = droneController;
		mainController.oculusSensors = oculusSensors;
		mainController.joyStickSensors = joystickSensors;

		final OpenGLWindow frame = new OpenGLWindow(mainController);

		new RenderManager(droneController, frame, mainController, timer);

		// locks untill ESC
		frame.start();

		System.exit(0);

	}
}