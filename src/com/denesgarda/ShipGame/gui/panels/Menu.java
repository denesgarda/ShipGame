package com.denesgarda.ShipGame.gui.panels;

import com.denesgarda.ShipGame.gui.Panel;
import com.denesgarda.ShipGame.gui.component.Label;
import com.denesgarda.ShipGame.gui.dialogue.GameMode;
import com.denesgarda.ShipGame.util.Popup;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Menu extends Panel {
    public Menu(JFrame parent) {
        super(parent);

        Label title = new Label("The Ship Game", 25);
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
                    new GameMode();
                } else if (option == 1) {
                    JFileChooser fileChooser = new JFileChooser();
                    int result = fileChooser.showOpenDialog(null);
                    if (result == JFileChooser.APPROVE_OPTION) {
                        Popup.error("Not Yet Implemented", "Opening saved games is not yet implement.", false);
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
