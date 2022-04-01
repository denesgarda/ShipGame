package com.denesgarda.ShipGame.util;

import javax.swing.*;

public class Popup {
    public static void error(String title, String message, boolean exit) {
        JOptionPane.showMessageDialog(null, message, title, JOptionPane.ERROR_MESSAGE, ImageManager.getImageIcon("/assets/image/error.png"));
        if (exit) {
            System.exit(-1);
        }
    }

    public static int play() {
        return JOptionPane.showOptionDialog(null, "New Game or Load Game", "Play", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, ImageManager.getImageIcon("/assets/image/question.png"), new String[]{"New Game", "Load Game"}, "New Game");
    }

    public static int quit() {
        return JOptionPane.showOptionDialog(null, "Are you sure you want to quit?", "Quit?", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, ImageManager.getImageIcon("/assets/image/question.png"), new String[]{"No", "Yes"}, "No");
    }
}
