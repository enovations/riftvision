package com.sourcegasm.riftvision.render;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;

import com.codeminders.ardrone.NavData;

public class AdvancedRiftRenderingSystemTM {
	
	private final static int CALIBRATION_X = 62;
	private final static int CALIBRATION_Y = 68;
	
	private final static double CALIBRATION2 = -0.068731;
	
	private final static int LAYER1_OFFSET = 0;
	private final static int LAYER2_OFFSET = 3;
	
	private final static int LAYER2_RESOLUTION = 3;
	
	public static BufferedImage renderOculusVideoAndData(BufferedImage bufImg, int w, int h, NavData navdata){
		
		BufferedImage img = new BufferedImage(bufImg.getWidth(), bufImg.getHeight(), BufferedImage.TYPE_INT_RGB);
        img.getGraphics().drawImage(bufImg, 0, 0, null);
        int[] bytes = (int[]) img.getData().getDataElements(0, 0, img.getWidth(), img.getHeight(), null);
		
		int[] old = new int[bytes.length];
		System.arraycopy( bytes, 0, old, 0, bytes.length);
		
		int[] endPixels = computeRGBArray(bytes, img.getWidth(), img.getHeight());
		
		BufferedImage layer1 = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_RGB);
        layer1.getWritableTile(0, 0).setDataElements(0, 0, img.getWidth(), img.getHeight(), endPixels);
        layer1.getGraphics().drawImage(new ImageIcon("./res/mask.png").getImage(), 0, 0, null);
        
        BufferedImage layer2 = new BufferedImage(img.getWidth()*LAYER2_RESOLUTION, img.getHeight()*LAYER2_RESOLUTION, BufferedImage.TYPE_INT_ARGB);
        if(navdata!=null){
        	int wosd = img.getWidth()*LAYER2_RESOLUTION;
        	int hosd = img.getHeight()*LAYER2_RESOLUTION;
        	Graphics g = layer2.getGraphics();
        	g.setColor(new Color(0,0,0,120));
        	g.fillRect(0, hosd-220, wosd, 40);
        	g.setColor(new Color(255,255,255,255));
        	g.setFont(g.getFont().deriveFont(36.0f));
        	g.drawImage(new ImageIcon("./res/batt_"+((navdata.getBattery()<15)?"off":"on")+".png").getImage(), 110, hosd-218, null);
        	g.drawString(navdata.getBattery()+"%", 186, hosd-220+34);
        }
        
        BufferedImage layer2_bent = bendForOculus(layer2, img.getWidth()*LAYER2_RESOLUTION, img.getHeight()*LAYER2_RESOLUTION);
        	
        	
        BufferedImage render = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
        
        render.getGraphics().drawImage(layer1, CALIBRATION_X, CALIBRATION_Y-10, w/2-CALIBRATION_X, h-CALIBRATION_Y*2, null);
        render.getGraphics().drawImage(layer1, w/2, CALIBRATION_Y-10, w/2-CALIBRATION_X, h-CALIBRATION_Y*2, null);
        
        render.getGraphics().drawImage(layer2_bent, CALIBRATION_X+LAYER2_OFFSET, CALIBRATION_Y-10, w/2-CALIBRATION_X, h-CALIBRATION_Y*2, null);
        render.getGraphics().drawImage(layer2_bent, w/2-LAYER2_OFFSET, CALIBRATION_Y-10, w/2-CALIBRATION_X, h-CALIBRATION_Y*2, null);
        
		return render;
		
	}
	
	public static BufferedImage bendForOculus(BufferedImage bufImg, int w, int h){
		
		BufferedImage img = new BufferedImage(bufImg.getWidth(), bufImg.getHeight(), BufferedImage.TYPE_INT_ARGB);
        img.getGraphics().drawImage(bufImg, 0, 0, null);
        int[] bytes = (int[]) img.getData().getDataElements(0, 0, img.getWidth(), img.getHeight(), null);
		
		int[] old = new int[bytes.length];
		System.arraycopy( bytes, 0, old, 0, bytes.length);
		
		int[] endPixels = computeRGBArray(bytes, img.getWidth(), img.getHeight());
		
		BufferedImage edgesImage = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_ARGB);
        edgesImage.getWritableTile(0, 0).setDataElements(0, 0, img.getWidth(), img.getHeight(), endPixels);
				
		return edgesImage;
		
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
