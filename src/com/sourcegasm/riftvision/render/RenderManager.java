package com.sourcegasm.riftvision.render;

import com.codeminders.ardrone.ARDrone;
import com.codeminders.ardrone.util.BufferedImageVideoListener;

import java.awt.image.BufferedImage;

public class RenderManager {
	
	public RenderManager(ARDrone drone, final RenderRiftWindow frame){
		drone.addImageListener(new BufferedImageVideoListener() {
            @Override
            public void imageReceived(BufferedImage image) {
            	frame.updateFrame(image);
            }
        });
	}

}
