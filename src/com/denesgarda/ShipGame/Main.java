package com.denesgarda.ShipGame;

import com.denesgarda.ShipGame.gui.Window;
import com.denesgarda.ShipGame.gui.panels.Menu;

import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class Main {
    public static class Variables {
        public static final String name = "The Ship Game";
        public static final double version = 3.0;

        public static class Port {
            public static final String[] names = new String[]{"Havenborough", "Woodham", "Lightmeadow", "Coldfield", "Arkala", "Wolford", "Blackpool", "Exeter", "Cesterfield", "Falkirk", "Oakheart"};
            public static final String[] items = new String[]{"Cotton", "Wool", "Wood", "Steel", "Oil", "Gold", "Diamond", "Gunpowder", "Silk"};
            public static final double[][] means = {{45, 60}, {75, 90}, {110, 125}, {180, 195}, {255, 275}, {1190, 1235}, {3180, 3245}, {465, 485}, {305, 325}};
        }
    }

    public static Window window;

    public static void main(String[] args) {
        try {
            URLConnection connection = new URL("https://raw.githubusercontent.com/DenDen747/ShipGame/main/.versioninfo").openConnection();
        } catch (MalformedURLException e) {
            
        }

        window = new Window();
        window.setPanel(new Menu(window));
    }
}
