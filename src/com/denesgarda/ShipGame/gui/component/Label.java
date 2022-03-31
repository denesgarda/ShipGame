package com.denesgarda.ShipGame.gui.component;

import javax.swing.*;
import java.awt.*;

public class Label extends JLabel {
    public Label(String text, int size, boolean bold) {
        super(text);
        if (bold) {
            Font font = new Font(Font.MONOSPACED, Font.BOLD, size);
            this.setFont(font);
        } else {
            Font font = new Font(Font.MONOSPACED, Font.PLAIN, size);
            this.setFont(font);
        }
    }
}
