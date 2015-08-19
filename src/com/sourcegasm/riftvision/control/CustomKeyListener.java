package com.sourcegasm.riftvision.control;

import java.awt.event.KeyEvent;
import java.io.IOException;

/**
 * Created by zigapk on 8/19/15.
 */
public class CustomKeyListener {
    public static void onKeyPressed(KeyEvent e, MainController mainController) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP:
                try {
                    mainController.getDroneController().getDrone().takeOff();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                mainController.startController(mainController.getDroneController());
                break;
            case KeyEvent.VK_DOWN:
                mainController.stopController();
                try {
                    mainController.getDroneController().getDrone().land();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                break;
            case KeyEvent.VK_SPACE:
                try {
                    mainController.getDroneController().getDrone().sendEmergencySignal();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                mainController.stopController();
                break;
            case KeyEvent.VK_E:
                try {
                    mainController.getDroneController().getDrone().clearEmergencySignal();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                break;
            case KeyEvent.VK_T:
                try {
                    mainController.getDroneController().getDrone().trim();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
        }
    }
}
