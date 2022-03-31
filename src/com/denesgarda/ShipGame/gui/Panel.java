package com.denesgarda.ShipGame.gui;

import javax.swing.*;

public class Panel extends JPanel {
    public final JFrame parent;
    public final SpringLayout layout;

    public Panel(JFrame parent) {
        this.parent = parent;
        layout = new SpringLayout();
        this.setLayout(layout);
    }
}
