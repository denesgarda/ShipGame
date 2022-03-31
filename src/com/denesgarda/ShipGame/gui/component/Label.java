package com.denesgarda.ShipGame.gui.component;

import javax.swing.*;
import java.awt.*;

public class Label extends JLabel {
    public Label(String text, int size) {
        super(text);
        Font font = new Font(Font.MONOSPACED, Font.BOLD, size);
        this.setFont(font);
    }
}
