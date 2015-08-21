package com.sourcegasm.riftvision.render;

import java.awt.image.BufferedImage;

public class OculusLayerRender {

	public static SBSBufferedImage renderAllLayersSBS(BufferedImage droneSource, BufferedImage osdRender){
		
		int imageW = droneSource.getWidth()*3;
		int imageH = droneSource.getHeight()*3;
		
		BufferedImage leftEye = new BufferedImage(imageW, imageH, BufferedImage.TYPE_INT_RGB);
		BufferedImage rightEye = new BufferedImage(imageW, imageH, BufferedImage.TYPE_INT_RGB);
		
		leftEye.getGraphics().drawImage(droneSource, 0, 0, imageW, imageH, null);
		rightEye.getGraphics().drawImage(droneSource, 0, 0, imageW, imageH, null);
		
		leftEye.getGraphics().drawImage(osdRender, 18, 0, imageW, imageH, null);
		rightEye.getGraphics().drawImage(osdRender, -18, 0, imageW, imageH, null);
		
		SBSBufferedImage sbsimage = new SBSBufferedImage(rightEye, leftEye);
		
		return sbsimage;
	}

}
