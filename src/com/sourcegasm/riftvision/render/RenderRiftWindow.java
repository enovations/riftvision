package com.sourcegasm.riftvision.render;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import com.codeminders.ardrone.NavData;
import com.sourcegasm.riftvision.control.CustomKeyListener;
import com.sourcegasm.riftvision.control.MainController;

public class RenderRiftWindow extends JFrame {

	private static final long serialVersionUID = 1L;

	BufferedImage frame = null;
	JPanel renderPanel = null;
	NavData navdata = null;

	public RenderRiftWindow(final MainController mainController) {
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setUndecorated(true);
		GraphicsDevice device = GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices()[0];
		device.setFullScreenWindow(this);
		renderPanel = new JPanel() {

			private static final long serialVersionUID = 1L;

			@Override
			public void paintComponent(Graphics g) {
				Graphics2D g2d = (Graphics2D) g;
				if (frame != null)
					g2d.drawImage(AdvancedRiftRenderingSystemTM.renderOculusVideoAndData(frame, getWidth(), getHeight(),
							navdata), 0, 0, null);
			}

		};
		renderPanel.setFocusable(true);
		renderPanel.requestFocus();
		renderPanel.addKeyListener(new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e) {
			}

			@Override
			public void keyPressed(KeyEvent e) {
				CustomKeyListener.onKeyPressed(e, mainController);
			}

			@Override
			public void keyReleased(KeyEvent e) {
			}
		});
		add(renderPanel);
		setVisible(true);
	}

	public void updateFrame(BufferedImage frame, NavData navdata) {
		this.frame = frame;
		this.navdata = navdata;
		renderPanel.repaint();
	}
}