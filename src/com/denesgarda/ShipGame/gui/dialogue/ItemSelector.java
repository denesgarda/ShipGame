package com.denesgarda.ShipGame.gui.dialogue;

import com.denesgarda.ShipGame.Game;
import com.denesgarda.ShipGame.Main;
import com.denesgarda.ShipGame.gui.panels.GamePanel;
import com.denesgarda.ShipGame.util.ImageManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ItemSelector extends JFrame {
    public ItemSelector(String[] items, Selected selected) {
        super("Select An Item");
        this.setResizable(false);
        this.setSize(250, 210);
        this.setIconImage(ImageManager.getImageIcon("/assets/image/icon.png").getImage());
        this.setLocationRelativeTo(null);
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        for (String item : items) {
            JButton button = new JButton(item);
            button.setAlignmentX(Component.CENTER_ALIGNMENT);
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    ItemSelector.super.setVisible(false);
                    selected.onSelect(item);
                }
            });
            panel.add(button);
        }
        this.setVisible(true);
        this.setContentPane(panel);
        this.revalidate();
    }

    public interface Selected {
        void onSelect(String item);
    }
}
