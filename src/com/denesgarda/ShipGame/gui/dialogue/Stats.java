package com.denesgarda.ShipGame.gui.dialogue;

import com.denesgarda.ShipGame.gui.component.Label;
import com.denesgarda.ShipGame.util.ImageManager;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Stats extends Dialogue {
    public Stats(Finished finished) {
        super("Custom Settings");
        this.setResizable(false);
        this.setSize(250, 200);
        this.setIconImage(ImageManager.getImageIcon("/assets/image/icon.png").getImage());
        this.setLocationRelativeTo(null);
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JSlider foodSlider = new JSlider();
        foodSlider.setAlignmentX(Component.CENTER_ALIGNMENT);
        foodSlider.setMinimum(10);
        foodSlider.setMaximum(300);
        foodSlider.setValue(10);
        panel.add(foodSlider);

        Label food = new Label("Food: 10", 12, false);
        food.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(food);

        JSlider moneySlider = new JSlider();
        moneySlider.setAlignmentX(Component.CENTER_ALIGNMENT);
        moneySlider.setMinimum(250);
        moneySlider.setMaximum(15000);
        moneySlider.setValue(250);
        panel.add(moneySlider);

        Label money = new Label("Money: 250", 12, false);
        money.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(money);

        JSlider goalSlider = new JSlider();
        goalSlider.setAlignmentX(Component.CENTER_ALIGNMENT);
        goalSlider.setMinimum(5000);
        goalSlider.setMaximum(100000);
        goalSlider.setValue(5000);
        panel.add(goalSlider);

        Label goal = new Label("Goal: 5000", 12, false);
        goal.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(goal);

        JButton start = new JButton("Start");
        start.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(start);

        foodSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                food.setText("Food: " + foodSlider.getValue());
            }
        });

        moneySlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                money.setText("Money: " + moneySlider.getValue());
            }
        });

        goalSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                goal.setText("Goal: " + goalSlider.getValue());
            }
        });

        start.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Stats.super.setVisible(false);
                finished.onFinish(foodSlider.getValue(), moneySlider.getValue(), goalSlider.getValue());
            }
        });

        this.setVisible(true);
        this.setContentPane(panel);
        this.revalidate();
    }

    public interface Finished {
        void onFinish(int food, int money, int goal);
    }
}
