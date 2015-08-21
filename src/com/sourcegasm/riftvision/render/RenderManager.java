package com.sourcegasm.riftvision.render;

import com.codeminders.ardrone.util.BufferedImageVideoListener;
import com.sourcegasm.riftvision.control.DroneController;
import com.sourcegasm.riftvision.opengl.OpenGLWindow;

import java.awt.image.BufferedImage;
import java.awt.image.RescaleOp;

public class RenderManager {
    public BufferedImage rightImage;
    public BufferedImage leftImage;

	public RenderManager(DroneController controller, final OpenGLWindow frame) {
		controller.getDrone().addImageListener(new BufferedImageVideoListener() {
			@Override
			public void imageReceived(BufferedImage image) {
				RescaleOp rescaleOp = new RescaleOp(1.2f, 15, null);
				rescaleOp.filter(image, image);
                rightImage = image;
            }
		});
	}

    public RenderManager() {

    }

}
