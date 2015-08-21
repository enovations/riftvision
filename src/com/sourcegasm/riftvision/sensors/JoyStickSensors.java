package com.sourcegasm.riftvision.sensors;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

import com.sourcegasm.riftvision.control.ExpoController;
import com.sourcegasm.riftvision.control.MainController;

/**
 * Created by klemen on 18.8.2015.
 */
public class JoyStickSensors {

	private double rawRoll;
	private double rawPitch;
	private double rawYaw;
	private double rawHeight;

	private Thread recieverThread = new Thread();
	private Thread recieverThread2 = new Thread();

	private MainController droneController;
	
	public JoyStickSensors(MainController droneController) {
		this.droneController = droneController;
	}
	
	public void startReceiving() {

		recieverThread = new Thread(new Runnable() {
			@Override
			public void run() {

				try {
					DatagramSocket dsocket = new DatagramSocket(1235);

					while (true) {
						
						DatagramPacket packet = new DatagramPacket(new byte[1024], 1024);
						dsocket.receive(packet);

						String[] mami_array = new String(packet.getData()).split("\\;");
						
						if (mami_array.length == 4) {
							rawPitch = (float) (ExpoController.getJoyStickExpo(Integer.parseInt(mami_array[3].trim())));
							rawRoll = (float) (ExpoController.getJoyStickExpo(Integer.parseInt(mami_array[2].trim())));
							rawYaw = (float) (ExpoController.getJoyStickExpo((Integer.parseInt(mami_array[0].trim()))));
                            rawHeight = (float) ((Integer.parseInt(mami_array[1].trim()))) / -170.0;
                        }
												
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		recieverThread.start();
		
		recieverThread2 = new Thread(new Runnable() {
			@Override
			public void run() {

				try {
					DatagramSocket dsocket = new DatagramSocket(1236);

					while (true) {
						
						DatagramPacket packet = new DatagramPacket(new byte[1024], 1024);
						dsocket.receive(packet);

                        //System.out.println(new String(packet.getData()));

						
                        String[] mami_array = new String(packet.getData())
								.split("\\;");

						if (mami_array.length > 1) {

							if (Integer.parseInt(mami_array[6].trim()) == 1) {
								try {
									droneController.getDroneController().getDrone().takeOff();
				                } catch (IOException e1) {
				                    e1.printStackTrace();
				                }
								droneController.startController();
							}else if (Integer.parseInt(mami_array[7].trim()) == 1) {
								droneController.stopController();
				                try {
				                	droneController.getDroneController().getDrone().land();
				                } catch (IOException e1) {
				                    e1.printStackTrace();
				                }
							}else if (Integer.parseInt(mami_array[1].trim()) == 1) {
								try {
									droneController.getDroneController().getDrone().trim();
				                } catch (IOException e1) {
				                    e1.printStackTrace();
				                }
							}else if (Integer.parseInt(mami_array[2].trim()) == 1) {
								try {
									droneController.getDroneController().getDrone().clearEmergencySignal();
				                } catch (IOException e1) {
				                    e1.printStackTrace();
				                }
							}else if (Integer.parseInt(mami_array[5].trim()) == 1) {
								droneController.setControlMode(droneController.getNextMode());
							}else if (Integer.parseInt(mami_array[4].trim()) == 1) {
								droneController.setControlMode(droneController.getPreviousMode());
							}
							
						}
						 
												
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		recieverThread2.start();
	}

	public double getRawRool() {
		return rawRoll;
	}

	public double getRawPitch() {
		return rawPitch;
	}

	public double getRawYaw() {
		return rawYaw;
	}
	
	public double getRawHeight(){
		return rawHeight;
	}

}
