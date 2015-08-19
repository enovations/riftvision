package com.sourcegasm.riftvision.render;

import java.awt.image.BufferedImage;
import java.awt.image.RescaleOp;

import com.codeminders.ardrone.ARDrone;
import com.codeminders.ardrone.util.BufferedImageVideoListener;

public class RenderManager {
	
	public RenderManager(ARDrone drone, final RenderRiftWindow frame){
		drone.addImageListener(new BufferedImageVideoListener() {
            @Override
            public void imageReceived(BufferedImage image) {
            	System.out.println("got frame");
            	frame.updateFrame(image);
            }
        });
	}

}
