package com.denesgarda.ShipGame.gui.panels;

import com.denesgarda.JarData.data.Serialized;
import com.denesgarda.ShipGame.Game;
import com.denesgarda.ShipGame.Main;
import com.denesgarda.ShipGame.gui.Panel;
import com.denesgarda.ShipGame.gui.component.Label;
import com.denesgarda.ShipGame.gui.dialogue.SelectGameMode;
import com.denesgarda.ShipGame.util.ImageManager;
import com.denesgarda.ShipGame.util.Popup;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class Menu extends Panel {
    public Menu(JFrame parent) {
        super(parent);

        Label title = new Label("The Ship Game", 25, true);
        layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, title, 0, SpringLayout.HORIZONTAL_CENTER, this);
        layout.putConstraint(SpringLayout.NORTH, title, 120, SpringLayout.NORTH, this);
        this.add(title);

        JButton play = new JButton("Play");
        layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, play, 0, SpringLayout.HORIZONTAL_CENTER, this);
        layout.putConstraint(SpringLayout.NORTH, play, 60, SpringLayout.NORTH, title);
        play.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int option = Popup.play();
                if (option == 0) {
                    new SelectGameMode();
                } else if (option == 1) {
                    JFileChooser fileChooser = new JFileChooser();
                    int result = fileChooser.showOpenDialog(null);
                    if (result == JFileChooser.APPROVE_OPTION) {
                        try {
                            File file = new File(fileChooser.getSelectedFile().getAbsolutePath());
                            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
                            String data = bufferedReader.readLine();
                            bufferedReader.close();
                            Serialized serialized = new Serialized(data);
                            Game game = (Game) serialized.deSerialize();
                            if (Main.Variables.version != game.version) {
                                int choice = JOptionPane.showOptionDialog(null, "This game was created in a different version.\nLaunching it may cause issues.\n\nGame version: " + game.version + "\nCurrent version: " + Main.Variables.version, "Wrong Version", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, ImageManager.getImageIcon("/assets/image/warning.png"), new String[]{"Cancel", "Launch Anyway"}, "Cancel");
                                if (choice == 1) {
                                    GamePanel gamePanel = new GamePanel(Main.window, game);
                                    Main.window.setPanel(gamePanel);
                                }
                            } else {
                                GamePanel gamePanel = new GamePanel(Main.window, game);
                                Main.window.setPanel(gamePanel);
                            }
                        } catch (Exception ex) {
                            Popup.error("Open Failed", "Failed to open game.", false);
                        }
                    }
                }
            }
        });
        this.add(play);

        JButton howToPlay = new JButton("How to Play");
        layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, howToPlay, 0, SpringLayout.HORIZONTAL_CENTER, this);
        layout.putConstraint(SpringLayout.NORTH, howToPlay, 30, SpringLayout.NORTH, play);
        howToPlay.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "You start off with a certain amount of food, money, and a set goal.\nYou have to sail from port to port, buying and selling items.\nThe items' prices vary from port to port.\nEvery time you sail, you use up 5 food.\nThe objective is to reach the money goal before running out of food.", "How to Play", JOptionPane.INFORMATION_MESSAGE, ImageManager.getImageIcon("/assets/image/info.png"));
            }
        });
        this.add(howToPlay);

        JButton github = new JButton("GitHub");
        layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, github, 0, SpringLayout.HORIZONTAL_CENTER, this);
        layout.putConstraint(SpringLayout.NORTH, github, 30, SpringLayout.NORTH, howToPlay);
        github.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Desktop.getDesktop().browse(new URI("https://github.com/DenDen747/ShipGame"));
                } catch (URISyntaxException | IOException ex) {
                    Popup.error("Internal Error", "An internal error occurred.", false);
                }
            }
        });
        this.add(github);

        JButton checkForUpdate = new JButton("Check for Update");
        layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, checkForUpdate, 0, SpringLayout.HORIZONTAL_CENTER, this);
        layout.putConstraint(SpringLayout.NORTH, checkForUpdate, 30, SpringLayout.NORTH, github);
        checkForUpdate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean update = Main.checkForUpdate();
                if (!update) {
                    JOptionPane.showMessageDialog(null, "You are running the newest version.", "Up to Date", JOptionPane.INFORMATION_MESSAGE, ImageManager.getImageIcon("/assets/image/info.png"));
                }
            }
        });
        this.add(checkForUpdate);

        JButton quit = new JButton("Quit");
        layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, quit, 0, SpringLayout.HORIZONTAL_CENTER, this);
        layout.putConstraint(SpringLayout.NORTH, quit, 30, SpringLayout.NORTH, checkForUpdate);
        quit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        this.add(quit);
    }
}
