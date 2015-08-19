package com.sourcegasm.riftvision.render;

import com.sourcegasm.riftvision.control.CustomKeyListener;
import com.sourcegasm.riftvision.control.DroneController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;

public class RenderRiftWindow extends JFrame{

	private static final long serialVersionUID = 1L;
	
	BufferedImage frame = null;
	JPanel renderPanel = null;
	
	public RenderRiftWindow(final DroneController controller) {
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
	    			g2d.drawImage(AdvancedRiftRenderingSystemTM.renderOculusVideoAndData(frame, getWidth(), getHeight()), 0, 0, null);
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
                CustomKeyListener.onKeyPressed(e, controller);
            }

            @Override
            public void keyReleased(KeyEvent e) {
            }
        });
        add(renderPanel);
	    setVisible(true);
	}
	
	public void updateFrame(BufferedImage frame){
		this.frame = frame;
		renderPanel.repaint();
	}
}