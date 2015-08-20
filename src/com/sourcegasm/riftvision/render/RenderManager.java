package com.sourcegasm.riftvision.render;

import java.awt.image.BufferedImage;
import java.awt.image.RescaleOp;

import com.codeminders.ardrone.util.BufferedImageVideoListener;
import com.sourcegasm.riftvision.control.DroneController;
import com.sourcegasm.riftvision.opengl.OpenGLWindow;
import com.sourcegasm.riftvision.opengl.TextureLoader;

public class RenderManager {
	public RenderManager(DroneController controller, final OpenGLWindow frame) {
		controller.getDrone().addImageListener(new BufferedImageVideoListener() {
			@Override
			public void imageReceived(BufferedImage image) {
				RescaleOp rescaleOp = new RescaleOp(1.2f, 15, null);
				rescaleOp.filter(image, image);

				//frame.leftSide.textureID = TextureLoader.loadTexture(image);
				//frame.rightSide.textureID = TextureLoader.loadTexture(image);
				System.out.println("aa");


				//AdvancedRiftRenderingSystemTM.renderOculusVideoAndData(frame, getWidth(), getHeight(), navdata)
				//frame.updateFrame(image, controller.getNavData());
			}
		});
	}

}
