package com.sourcegasm.riftvision;

import com.sourcegasm.riftvision.opengl.OpenGLWindow;
import com.sourcegasm.riftvision.render.RenderManager;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Main {

	public static void main(String[] args) throws IOException {

		/*DroneController droneController = new DroneController();
        MainController mainController = new MainController();
		OculusSensors oculusSensors = new OculusSensors();
		oculusSensors.startReceiving();
		JoyStickSensors joystickSensors = new JoyStickSensors(mainController);
		joystickSensors.startReceiving();
		mainController.droneController = droneController;
		mainController.oculusSensors = oculusSensors;
		mainController.joyStickSensors = joystickSensors;*/

		OpenGLWindow frame = new OpenGLWindow();
        //RenderManager renderManager = new RenderManager(droneController, frame);
        RenderManager renderManager = new RenderManager();
        BufferedImage gridImage = ImageIO.read(new File("res/grid.png"));
        renderManager.rightImage = gridImage;
        renderManager.leftImage = gridImage;

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                BufferedImage asdf = null;
                try {
                    asdf = ImageIO.read(new File("res/image.png"));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                renderManager.rightImage = asdf;
                renderManager.leftImage = asdf;
            }
        }).start();

        frame.start(renderManager);
    }
}