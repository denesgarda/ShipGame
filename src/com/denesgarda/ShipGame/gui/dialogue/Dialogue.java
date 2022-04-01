package com.denesgarda.ShipGame.gui.dialogue;

import javax.swing.*;
import java.awt.event.*;

public class Dialogue extends JFrame {
    public Dialogue(String title) {
        super(title);
        this.addWindowFocusListener(new WindowAdapter() {
            @Override
            public void windowLostFocus(WindowEvent e) {
                Dialogue.super.setVisible(false);
            }
        });
    }
}
