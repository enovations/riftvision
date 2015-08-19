package com.sourcegasm.riftvision.render;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class AdvancedRiftRenderingSystemTM {
	
	private final static int CALIBRATION_X = 62;
	private final static int CALIBRATION_Y = 68;
	private final static double CALIBRATION2 = -0.068731;
	
	public static BufferedImage renderOculusVideoAndData(BufferedImage bufImg, int w, int h){
		
		BufferedImage img = new BufferedImage(bufImg.getWidth(), bufImg.getHeight(), BufferedImage.TYPE_INT_RGB);
        img.getGraphics().drawImage(bufImg, 0, 0, null);
        int[] bytes = (int[]) img.getData().getDataElements(0, 0, img.getWidth(), img.getHeight(), null);
		
		int[] old = new int[bytes.length];
		System.arraycopy( bytes, 0, old, 0, bytes.length);
		
		int[] endPixels = computeRGBArray(bytes, img.getWidth(), img.getHeight());
		
		BufferedImage edgesImage = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_RGB);
        edgesImage.getWritableTile(0, 0).setDataElements(0, 0, img.getWidth(), img.getHeight(), endPixels);
	    
        BufferedImage render = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = (Graphics2D) render.getGraphics();
        
		g2d.drawImage(edgesImage, CALIBRATION_X, CALIBRATION_Y-10, w/2-CALIBRATION_X, h-CALIBRATION_Y*2, null);
		g2d.drawImage(edgesImage, w/2, CALIBRATION_Y-10, w/2-CALIBRATION_X, h-CALIBRATION_Y*2, null);
				
		return render;
		
	}
	
	protected static int[] computeRGBArray(int[] pixels, int width, int height) {
	    int[] pixelsCopy = pixels.clone();
	    double paramA = -0.007715;
	    double paramC = 0.0;
	    double paramD = 1.0 - paramA - CALIBRATION2 - paramC;
	    for (int x = 0; x < width; x++) {
	        for (int y = 0; y < height; y++) {
	            int d = Math.min(width, height) / 2;
	            double centerX = (width - 1) / 2.0;
	            double centerY = (height - 1) / 2.0;
	            double deltaX = (x - centerX) / d;
	            double deltaY = (y - centerY) / d;
	            double dstR = Math.sqrt(deltaX * deltaX + deltaY * deltaY);
	            double srcR = (paramA * dstR * dstR * dstR + CALIBRATION2 * dstR * dstR + paramC * dstR + paramD) * dstR;
	            double factor = Math.abs(dstR / srcR);
	            double srcXd = centerX + (deltaX * factor * d);
	            double srcYd = centerY + (deltaY * factor * d);
	            int srcX = (int) srcXd;
	            int srcY = (int) srcYd;
	            if (srcX >= 0 && srcY >= 0 && srcX < width && srcY < height) {
	                int dstPos = y * width + x;
	                pixels[dstPos] = pixelsCopy[srcY * width + srcX];
	            }
	        }
	    }
	    return pixels;
	}

}
