package com.sourcegasm.riftvision.render;

import com.codeminders.ardrone.ARDrone;
import com.codeminders.ardrone.util.BufferedImageVideoListener;
import com.sourcegasm.riftvision.control.DroneController;

import java.awt.image.BufferedImage;

public class RenderManager {
	
	public RenderManager(DroneController controller, final RenderRiftWindow frame){
		controller.getDrone().addImageListener(new BufferedImageVideoListener() {
            @Override
            public void imageReceived(BufferedImage image) {
            	frame.updateFrame(image, controller.getNavData());
            }
        });
	}

}
