package com.sourcegasm.advancedriftrenderingsystem;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.WritableRaster;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

public class Example1 {

	static GraphicsDevice device = GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices()[0];

	static BufferedImage img = null;
	static int[] bytes;
	static BufferedImage bufImg;
	
	static int CALIBRATION = 60;
	static double CALIBRATION2 = -0.082731;
	
	public static void main(String[] args) {

	    final JFrame frame = new JFrame("Display Mode");
	    frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	    frame.setUndecorated(true);
	    device.setFullScreenWindow(frame);
	    
	    
	    try {
	    	bufImg = ImageIO.read(new File("sample.png"));
	        
	    }catch(IOException e){
			System.out.println(e.getMessage());
		}
	    
	    
	    JPanel pne = new JPanel(){
	    	
	    	@Override
	    	public void paintComponent(Graphics g){
	    		long time = System.currentTimeMillis();
	    		Graphics2D g2d = (Graphics2D) g;
	    		g2d.setColor(Color.black);
	    		
	    		g2d.drawImage(AdvancedRiftRenderingSystemTM.doComputation(bufImg, getWidth(), getHeight()), 0, 0, null);
	    		
	    		time = System.currentTimeMillis() - time;
	    		System.out.println(1000.0/time);
	    	}
	    	
	    };
	    
	    frame.add(pne);

	    frame.setVisible(true);
	    
	    new Thread(){
	    	
	    	public void run(){
	    		while(true){
	    			pne.repaint();
	    		}
	    	}
	    	
	    }.start();

	}
	
}
