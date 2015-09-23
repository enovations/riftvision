package com.sourcegasm.riftvision.render;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.swing.ImageIcon;

import com.codeminders.ardrone.NavData;
import com.sourcegasm.riftvision.control.MainController;
import com.sourcegasm.riftvision.game.LapTimer;

public class OSDRender {

	private static Font font;

	static {
		try {
			font = Font.createFont(Font.TRUETYPE_FONT, new File("./res/font.ttf"));
		} catch (final FontFormatException e) {
			e.printStackTrace();
		} catch (final IOException e) {
			e.printStackTrace();
		}
	}

	public static BufferedImage renderOSD(BufferedImage droneSource, NavData navData, MainController mainController,
			LapTimer timer) {
		final int imageW = droneSource.getWidth() * 3;
		final int imageH = droneSource.getHeight() * 3;

		final BufferedImage osd = new BufferedImage(imageW, imageH, BufferedImage.TYPE_INT_ARGB);

		if (navData != null) {
			final Graphics2D g = (Graphics2D) osd.getGraphics();

			g.setColor(new Color(navData.isEmergency() ? 255 : 0, 0, 0, 50));
			g.fillRect(0, imageH - 220, imageW, 40);
			g.setColor(new Color(255, 255, 255, 150));
			g.drawLine(0, imageH - 220 - 1, imageW, imageH - 220 - 1);
			g.drawLine(0, imageH - 220 + 41, imageW, imageH - 220 + 41);
			g.setColor(new Color(255, 255, 255, 255));
			g.setFont(g.getFont().deriveFont(36.0f));
			g.drawImage(new ImageIcon("./res/batt_" + ((navData.getBattery() < 25) ? "off" : "on") + ".png").getImage(),
					120, imageH - 218, null);
			g.drawString(navData.getBattery() + "%", 196, imageH - 220 + 34);
			g.drawImage(new ImageIcon("./res/h.png").getImage(), 312, imageH - 218, null);
			g.drawString(((int) (navData.getAltitude() * 100.0f)) + "cm", 358, imageH - 220 + 34);
			g.drawImage(new ImageIcon("./res/" + ((navData.isFlying()) ? "on" : "off") + ".png").getImage(), 490,
					imageH - 218, null);
			g.drawImage(new ImageIcon("./res/roll.png").getImage(), 560, imageH - 218, null);
			g.drawString((int) (navData.getRoll()) + "° # " + ((int) navData.getPitch()) + "°", 610, imageH - 220 + 34);

			g.setColor(new Color(255, 255, 255, 255));
			g.drawString(mainController.controlMode.toString(), 170, 200);

			g.drawImage(new ImageIcon("./res/logo.png").getImage(), 410, imageH - 150, null);

			g.setFont(g.getFont().deriveFont(42.0f));

			if (!navData.isFlying() && !timer.show)
				g.drawString("Press L2 button to take off", 205, 370);
			else if (timer.show) {
				g.setFont(font.deriveFont(68.0f));
				g.drawString(String.format("%.2f", timer.timer), 400, 300);
			}

			g.setColor(new Color(0, 0, 0, 90));
			g.fillRect(imageW - 320 + 50, 130, imageW - (imageW - 320 + 50), 100);
			g.rotate(Math.toRadians(navData.getYaw()), imageW - 320 + 50, 130 + 50);
			g.drawImage(new ImageIcon("./res/rotate.png").getImage(), imageW - 320, 130, null);

		}

		return osd;
	}

}
