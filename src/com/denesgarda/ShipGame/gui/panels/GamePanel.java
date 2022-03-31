package com.denesgarda.ShipGame.gui.panels;

import com.denesgarda.ShipGame.Game;
import com.denesgarda.ShipGame.gui.Panel;

import javax.swing.*;

public class GamePanel extends Panel {
    public Game game;

    public GamePanel(JFrame parent, Game game) {
        super(parent);
        this.game = game;
    }
}
