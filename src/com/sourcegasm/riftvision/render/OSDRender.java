package com.sourcegasm.riftvision.render;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;

import com.codeminders.ardrone.NavData;

public class OSDRender {
	
	public static BufferedImage renderOSD(BufferedImage droneSource, NavData navData){
		int imageW = droneSource.getWidth()*3;
		int imageH = droneSource.getHeight()*3;
		
		BufferedImage osd = new BufferedImage(imageW, imageH, BufferedImage.TYPE_INT_ARGB);
		
		if (navData != null) {
			Graphics2D g = (Graphics2D) osd.getGraphics();
			
			g.setColor(new Color(navData.isEmergency()?255:0, 0, 0, 50));
			g.fillRect(0, imageH - 220, imageW, 40);
			g.setColor(new Color(255, 255, 255, 150));
			g.drawLine(0, imageH - 220-1, imageW, imageH - 220-1);
			g.drawLine(0, imageH - 220+41, imageW, imageH - 220+41);
			//g.drawLine(0, imageH - 220-2, imageW, imageH - 220-2);
			//g.drawLine(0, imageH - 220+42, imageW, imageH - 220+42);
			g.setColor(new Color(255, 255, 255, 255));
			g.setFont(g.getFont().deriveFont(36.0f));
			g.drawImage(new ImageIcon("./res/batt_" + ((navData.getBattery() < 25) ? "off" : "on") + ".png").getImage(),120, imageH - 218, null);
			g.drawString(navData.getBattery() + "%", 196, imageH - 220 + 34);
			g.drawImage(new ImageIcon("./res/h.png").getImage(), 312, imageH - 218, null);
			g.drawString(((int) (navData.getAltitude()*100.0f)) + "cm", 358, imageH - 220 + 34);
			g.drawImage(new ImageIcon("./res/" + ((navData.isFlying()) ? "on" : "off") + ".png").getImage(),490, imageH - 218, null);
			g.drawImage(new ImageIcon("./res/roll.png").getImage(),560, imageH - 218, null);
			g.drawString((int) (navData.getRoll()) + "° # " + ((int)navData.getPitch()) + "°", 610, imageH - 220 + 34);
			
			g.drawImage(new ImageIcon("./res/logo.png").getImage(),410, imageH - 150, null);
			
			g.setFont(g.getFont().deriveFont(42.0f));
			
			if(!navData.isFlying())
				g.drawString("Press \"LEFT\" key to take off", 190, 370);
			
			g.setColor(new Color(0, 0, 0, 90));
			g.fillRect(imageW-320+50, 130, imageW-(imageW-320+50), 100);		
			g.rotate(Math.toRadians(navData.getYaw()), imageW-320+50, 130+50);
			g.drawImage(new ImageIcon("./res/rotate.png").getImage(), imageW-320, 130, null);
		}
		
		
		return osd;
	}

}
