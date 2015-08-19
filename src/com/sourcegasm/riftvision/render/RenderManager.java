package com.sourcegasm.riftvision.render;

import java.awt.image.BufferedImage;

import com.codeminders.ardrone.util.BufferedImageVideoListener;
import com.sourcegasm.riftvision.control.DroneController;

public class RenderManager {

	public RenderManager(DroneController controller, final RenderRiftWindow frame) {
		controller.getDrone().addImageListener(new BufferedImageVideoListener() {
			@Override
			public void imageReceived(BufferedImage image) {
				frame.updateFrame(image, controller.getNavData());
			}
		});
	}

}
