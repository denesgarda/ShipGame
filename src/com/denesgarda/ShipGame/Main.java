package com.denesgarda.ShipGame;

import com.denesgarda.ShipGame.gui.Window;
import com.denesgarda.ShipGame.gui.panels.Menu;

public class Main {
    public static class Variables {
        public static final String name = "The Ship Game";
        public static final double version = 3.0;
    }

    public static Window window;

    public static void main(String[] args) {
        window = new Window();
        window.setPanel(new Menu(window));
    }
}
