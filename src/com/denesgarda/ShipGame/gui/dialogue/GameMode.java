package com.denesgarda.ShipGame.gui.dialogue;

import com.denesgarda.ShipGame.Game;
import com.denesgarda.ShipGame.Main;
import com.denesgarda.ShipGame.gui.panels.GamePanel;
import com.denesgarda.ShipGame.util.ImageManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameMode extends JFrame {
    public GameMode() {
        super("Select Game Mode");
        this.setResizable(false);
        this.setSize(250, 180);
        this.setIconImage(ImageManager.getImageIcon("/assets/image/icon.png").getImage());
        this.setLocationRelativeTo(null);
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        JButton easy = new JButton("Easy");
        easy.setAlignmentX(Component.CENTER_ALIGNMENT);
        easy.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GameMode.super.setVisible(false);
                Game game = new Game(150, 3000, 10000);
                GamePanel gamePanel = new GamePanel(Main.window, game);
                Main.window.setPanel(gamePanel);
            }
        });
        panel.add(easy);
        JButton medium = new JButton("Medium");
        medium.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(medium);
        JButton hard = new JButton("Hard");
        hard.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(hard);
        JButton impossible = new JButton("Impossible");
        impossible.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(impossible);
        JButton custom = new JButton("Custom");
        custom.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(custom);
        this.setVisible(true);
        this.setContentPane(panel);
        this.revalidate();
    }
}
