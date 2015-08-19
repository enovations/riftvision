package com.sourcegasm.riftvision.control;

import com.sourcegasm.riftvision.Main;

import java.awt.event.KeyEvent;
import java.io.IOException;

/**
 * Created by zigapk on 8/19/15.
 */
public class CustomKeyListener {
    public static void onKeyPressed(KeyEvent e) {
        DroneController controller = Main.controller;
        switch (e.getKeyCode()) {
            case KeyEvent.VK_W:
                try {
                    controller.getDrone().takeOff();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                break;
            case KeyEvent.VK_S:
                try {
                    controller.getDrone().land();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                break;
            case KeyEvent.VK_E:
                try {
                    controller.getDrone().sendEmergencySignal();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                break;
            case KeyEvent.VK_SPACE:
                try {
                    controller.getDrone().hover();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                break;
            case KeyEvent.VK_T:
                try {
                    controller.getDrone().trim();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                break;
        }
    }
}
