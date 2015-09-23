package com.sourcegasm.riftvision.render;

import java.awt.image.BufferedImage;
import java.awt.image.RescaleOp;

import com.codeminders.ardrone.util.BufferedImageVideoListener;
import com.sourcegasm.riftvision.control.DroneController;
import com.sourcegasm.riftvision.control.MainController;
import com.sourcegasm.riftvision.game.LapTimer;
import com.sourcegasm.riftvision.opengl.OpenGLWindow;

public class RenderManager {

	public RenderManager(DroneController controller, final OpenGLWindow frame, MainController mainController,
			LapTimer timer) {
		controller.getDrone().addImageListener(new BufferedImageVideoListener() {
			@Override
			public void imageReceived(BufferedImage image) {

				// increase brightness
				final RescaleOp rescaleOp = new RescaleOp(1.2f, 15, null);
				rescaleOp.filter(image, image);

				// render OSD
				final BufferedImage osd = OSDRender.renderOSD(image, controller.getNavData(), mainController, timer);

				// get sbs and apply it to the gl render
				final SBSBufferedImage sbsimage = OculusLayerRender.renderAllLayersSBS(image, osd);
				frame.rightImage = sbsimage.getRightImage();
				frame.leftImage = sbsimage.getLeftImage();

			}
		});
	}

}
