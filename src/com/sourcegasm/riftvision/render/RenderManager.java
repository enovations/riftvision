package com.sourcegasm.riftvision.render;

import com.codeminders.ardrone.util.BufferedImageVideoListener;
import com.sourcegasm.riftvision.control.DroneController;
import com.sourcegasm.riftvision.opengl.OpenGLWindow;

import java.awt.image.BufferedImage;
import java.awt.image.RescaleOp;

public class RenderManager {

	public RenderManager(DroneController controller, final OpenGLWindow frame) {
		controller.getDrone().addImageListener(new BufferedImageVideoListener() {
			@Override
			public void imageReceived(BufferedImage image) {
								
				//increase brightness
				RescaleOp rescaleOp = new RescaleOp(1.2f, 15, null);
				rescaleOp.filter(image, image);
				
				//render OSD
				BufferedImage osd = OSDRender.renderOSD(image, controller.getNavData());
				
				//get sbs and apply it to the gl render
				SBSBufferedImage sbsimage = OculusLayerRender.renderAllLayersSBS(image, osd);
				frame.rightImage = sbsimage.getRightImage();
				frame.leftImage = sbsimage.getLeftImage();
                
            }
		});
	}

}
