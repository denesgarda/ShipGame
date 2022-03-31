package com.denesgarda.ShipGame.gui;

import com.denesgarda.ShipGame.Main;
import com.denesgarda.ShipGame.util.ImageManager;

import javax.swing.*;

public class Window extends JFrame {
    public Window() {
        super(Main.Variables.name + " v" + Main.Variables.version);
        this.setSize(512, 512);
        this.setResizable(false);
        this.setIconImage(ImageManager.getImageIcon("/assets/image/icon.png").getImage());
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    public void setPanel(Panel panel) {
        this.setContentPane(panel);
        this.revalidate();
    }
}
