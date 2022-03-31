package com.denesgarda.ShipGame.gui.panels;

import com.denesgarda.JarData.data.Serialized;
import com.denesgarda.ShipGame.Game;
import com.denesgarda.ShipGame.Main;
import com.denesgarda.ShipGame.gui.Panel;
import com.denesgarda.ShipGame.gui.component.Label;
import com.denesgarda.ShipGame.gui.dialogue.SelectGameMode;
import com.denesgarda.ShipGame.util.Popup;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

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
                            GamePanel gamePanel = new GamePanel(Main.window, game);
                            Main.window.setPanel(gamePanel);
                        } catch (Exception ex) {
                            Popup.error("Open Failed", "Failed to open game.", false);
                        }
                    }
                }
            }
        });
        this.add(play);

        JButton quit = new JButton("Quit");
        layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, quit, 0, SpringLayout.HORIZONTAL_CENTER, this);
        layout.putConstraint(SpringLayout.NORTH, quit, 30, SpringLayout.NORTH, play);
        quit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        this.add(quit);
    }
}
