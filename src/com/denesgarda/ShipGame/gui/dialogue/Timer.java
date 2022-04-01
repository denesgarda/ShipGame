package com.denesgarda.ShipGame.gui.dialogue;

import com.denesgarda.ShipGame.Game;
import com.denesgarda.ShipGame.Main;
import com.denesgarda.ShipGame.gui.component.Label;
import com.denesgarda.ShipGame.gui.panels.GamePanel;
import com.denesgarda.ShipGame.util.ImageManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Timer extends Dialogue {
    public Timer(String title, String message, Runnable runnable) {
        super(title);
        this.setResizable(false);
        this.setSize(250, 80);
        this.setIconImage(ImageManager.getImageIcon("/assets/image/icon.png").getImage());
        this.setLocationRelativeTo(null);
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JProgressBar progressBar = new JProgressBar();
        progressBar.setMinimum(0);
        progressBar.setMaximum(300);
        panel.add(progressBar);

        Label label = new Label(message, 12, false);
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(label);

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while (true) {
                        Thread.sleep(1);
                        progressBar.setValue(progressBar.getValue() + 1);
                        if (progressBar.getValue() == 300) {
                            Timer.super.setVisible(false);
                            break;
                        }
                    }
                    runnable.run();
                } catch (Exception ignored) {}
            }
        }).start();

        this.setVisible(true);
        this.setContentPane(panel);
        this.revalidate();
    }
}
