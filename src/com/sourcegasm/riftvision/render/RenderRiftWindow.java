package com.sourcegasm.riftvision.render;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

public class RenderRiftWindow extends JFrame{

	private static final long serialVersionUID = 1L;
	
	BufferedImage frame = null;
	JPanel renderPanel = null;
	
	public RenderRiftWindow() {
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setUndecorated(true);
		GraphicsDevice device = GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices()[0];
		device.setFullScreenWindow(this);
		renderPanel = new JPanel(){
	    	
			private static final long serialVersionUID = 1L;

			@Override
	    	public void paintComponent(Graphics g){
	    		Graphics2D g2d = (Graphics2D) g;
	    		if(frame!=null)
	    			g2d.drawImage(AdvancedRiftRenderingSystemTM.doComputation(frame, getWidth(), getHeight()), 0, 0, null);
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
