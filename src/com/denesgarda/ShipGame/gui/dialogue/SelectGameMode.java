package com.denesgarda.ShipGame.gui.dialogue;

import com.denesgarda.ShipGame.Game;
import com.denesgarda.ShipGame.Main;
import com.denesgarda.ShipGame.gui.panels.GamePanel;
import com.denesgarda.ShipGame.util.ImageManager;
import com.denesgarda.ShipGame.util.Popup;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SelectGameMode extends JFrame {
    public SelectGameMode() {
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
                SelectGameMode.super.setVisible(false);
                Game game = new Game(Main.Variables.version, 150, 3000, 10000, Game.GameMode.EASY);
                GamePanel gamePanel = new GamePanel(Main.window, game);
                Main.window.setPanel(gamePanel);
            }
        });
        panel.add(easy);
        JButton medium = new JButton("Medium");
        medium.setAlignmentX(Component.CENTER_ALIGNMENT);
        medium.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SelectGameMode.super.setVisible(false);
                Game game = new Game(Main.Variables.version, 125, 2500, 12500, Game.GameMode.EASY);
                GamePanel gamePanel = new GamePanel(Main.window, game);
                Main.window.setPanel(gamePanel);
            }
        });
        panel.add(medium);
        JButton hard = new JButton("Hard");
        hard.setAlignmentX(Component.CENTER_ALIGNMENT);
        hard.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SelectGameMode.super.setVisible(false);
                Game game = new Game(Main.Variables.version, 100, 2000, 15000, Game.GameMode.EASY);
                GamePanel gamePanel = new GamePanel(Main.window, game);
                Main.window.setPanel(gamePanel);
            }
        });
        panel.add(hard);
        JButton impossible = new JButton("Impossible");
        impossible.setAlignmentX(Component.CENTER_ALIGNMENT);
        impossible.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SelectGameMode.super.setVisible(false);
                Game game = new Game(Main.Variables.version, 75, 1500, 30000, Game.GameMode.EASY);
                GamePanel gamePanel = new GamePanel(Main.window, game);
                Main.window.setPanel(gamePanel);
            }
        });
        panel.add(impossible);
        JButton custom = new JButton("Custom");
        custom.setAlignmentX(Component.CENTER_ALIGNMENT);
        custom.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Popup.error("Not Yet Implemented", "Creating custom games is not yet implemented.", false);
                /*SelectGameMode.super.setVisible(false);
                Game game = new Game(Main.Variables.version, 150, 3000, 10000, Game.GameMode.EASY);
                GamePanel gamePanel = new GamePanel(Main.window, game);
                Main.window.setPanel(gamePanel);*/
            }
        });
        panel.add(custom);
        this.setVisible(true);
        this.setContentPane(panel);
        this.revalidate();
    }
}
