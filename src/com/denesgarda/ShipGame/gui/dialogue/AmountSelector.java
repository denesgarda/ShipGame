package com.denesgarda.ShipGame.gui.dialogue;

import com.denesgarda.ShipGame.Game;
import com.denesgarda.ShipGame.Main;
import com.denesgarda.ShipGame.game.Port;
import com.denesgarda.ShipGame.gui.component.Label;
import com.denesgarda.ShipGame.gui.panels.GamePanel;
import com.denesgarda.ShipGame.util.ImageManager;
import com.denesgarda.ShipGame.util.Popup;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

public class AmountSelector extends Dialogue {
    private Label cost;
    private double m;
    private Label transaction;

    public AmountSelector(Type type, String item, Port port, Game game, Inputted inputted) {
        super("Select Amount");
        this.setResizable(false);
        this.setSize(250, 120);
        this.setIconImage(ImageManager.getImageIcon("/assets/image/icon.png").getImage());
        this.setLocationRelativeTo(null);
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        if (type == Type.BUY) {
            if ((int) Math.floor(game.money / port.prices[Arrays.asList(port.items).indexOf(item)]) == 0) {
                Popup.error("Not Enough Money", "You do not have enough money to buy this item.", false);
                this.setVisible(false);
                return;
            }
            this.setTitle("Buy " + item);
            JSlider slider = new JSlider();
            slider.setMinimum(1);
            slider.setMaximum((int) Math.floor(game.money / port.prices[Arrays.asList(port.items).indexOf(item)]));
            slider.setValue(1);
            slider.addChangeListener(new ChangeListener() {
                @Override
                public void stateChanged(ChangeEvent e) {
                    cost.setText(slider.getValue() + " * " + port.prices[Arrays.asList(port.items).indexOf(item)] + " = " + Math.round((slider.getValue() * port.prices[Arrays.asList(port.items).indexOf(item)]) * 100.0) / 100.0);
                    m = Math.round((slider.getValue() * port.prices[Arrays.asList(port.items).indexOf(item)]) * 100.0) / 100.0;
                    transaction.setText(game.money + " - " + m + " = " + Math.round((game.money - m) * 100.0) / 100.0);
                }
            });
            panel.add(slider);

            cost = new Label("1 * " + port.prices[Arrays.asList(port.items).indexOf(item)] + " = " + port.prices[Arrays.asList(port.items).indexOf(item)], 12, false);
            cost.setAlignmentX(Component.CENTER_ALIGNMENT);
            panel.add(cost);
            m = port.prices[Arrays.asList(port.items).indexOf(item)];

            transaction = new Label(game.money + " - " + m + " = " + Math.round((game.money - m) * 100.0) / 100.0, 12, false);
            transaction.setAlignmentX(Component.CENTER_ALIGNMENT);
            panel.add(transaction);

            JButton buy = new JButton("Buy");
            buy.setAlignmentX(Component.CENTER_ALIGNMENT);
            buy.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    AmountSelector.super.setVisible(false);
                    inputted.onInput(Type.BUY, item, slider.getValue(), port);
                }
            });
            panel.add(buy);
        } else if (type == Type.SELL) {
            if (game.inventory[Arrays.asList(Main.Variables.Port.items).indexOf(port.items[Arrays.asList(port.items).indexOf(item)])] == 0) {
                Popup.error("Not Enough Resources", "You do not have enough resources to sell this item.", false);
                this.setVisible(false);
                return;
            }
            this.setTitle("Sell " + item);
            JSlider slider = new JSlider();
            slider.setMinimum(1);
            slider.setMaximum(game.inventory[Arrays.asList(Main.Variables.Port.items).indexOf(port.items[Arrays.asList(port.items).indexOf(item)])]);
            slider.setValue(1);
            slider.addChangeListener(new ChangeListener() {
                @Override
                public void stateChanged(ChangeEvent e) {
                    cost.setText(slider.getValue() + " * " + port.prices[Arrays.asList(port.items).indexOf(item)] + " = " + Math.round((slider.getValue() * port.prices[Arrays.asList(port.items).indexOf(item)]) * 100.0) / 100.0);
                    m = Math.round((slider.getValue() * port.prices[Arrays.asList(port.items).indexOf(item)]) * 100.0) / 100.0;
                    transaction.setText(game.money + " + " + m + " = " + Math.round((game.money + m) * 100.0) / 100.0);
                }
            });
            panel.add(slider);

            cost = new Label("1 * " + port.prices[Arrays.asList(port.items).indexOf(item)] + " = " + port.prices[Arrays.asList(port.items).indexOf(item)], 12, false);
            cost.setAlignmentX(Component.CENTER_ALIGNMENT);
            panel.add(cost);
            m = port.prices[Arrays.asList(port.items).indexOf(item)];

            transaction = new Label(game.money + " + " + m + " = " + Math.round((game.money + m) * 100.0) / 100.0, 12, false);
            transaction.setAlignmentX(Component.CENTER_ALIGNMENT);
            panel.add(transaction);

            JButton buy = new JButton("Sell");
            buy.setAlignmentX(Component.CENTER_ALIGNMENT);
            buy.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    AmountSelector.super.setVisible(false);
                    inputted.onInput(Type.BUY, item, slider.getValue(), port);
                }
            });
            panel.add(buy);
        }

        this.setVisible(true);
        this.setContentPane(panel);
        this.revalidate();
    }

    public enum Type {
        BUY,
        SELL
    }

    public interface Inputted {
        void onInput(Type type, String item, int amount, Port port);
    }
}
