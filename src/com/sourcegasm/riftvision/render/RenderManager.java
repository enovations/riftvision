package com.sourcegasm.riftvision.render;

import com.sourcegasm.riftvision.control.DroneController;

public class RenderManager {

	public RenderManager(DroneController controller, final RenderRiftWindow frame) {
		/*controller.getDrone().addImageListener(new BufferedImageVideoListener() {
			@Override
			public void imageReceived(BufferedImage image) {
				RescaleOp rescaleOp = new RescaleOp(1.2f, 15, null);
				rescaleOp.filter(image, image);
				frame.updateFrame(image, controller.getNavData());
			}
		});*/
	}

}
