package com.sourcegasm.riftvision.render;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

public class RenderFrame extends JFrame{

	private static final long serialVersionUID = 1L;
	
	BufferedImage frame = null;
	JPanel renderPanel = null;
	
	public RenderFrame() {
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setUndecorated(true);
		GraphicsDevice device = GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices()[0];
		device.setFullScreenWindow(this);
		renderPanel = new JPanel(){
	    	
			private static final long serialVersionUID = 1L;

			@Override
	    	public void paintComponent(Graphics g){
	    		long time = System.currentTimeMillis();
	    		Graphics2D g2d = (Graphics2D) g;
	    		g2d.setColor(Color.black);
	    		
	    		g2d.drawImage(frame, 0, 0, null);
	    		
	    		time = System.currentTimeMillis() - time;
	    		System.out.println(1000.0/time);
	    	}
	    	
	    };
	    add(renderPanel);
	    setVisible(true);
	}
	
	public void updateFrame(BufferedImage frame){
		this.frame = frame;
		renderPanel.repaint();
	}
	
}
