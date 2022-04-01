package com.denesgarda.ShipGame;

import com.denesgarda.ShipGame.gui.Window;
import com.denesgarda.ShipGame.gui.panels.Menu;
import com.denesgarda.ShipGame.util.ImageManager;
import com.denesgarda.ShipGame.util.Popup;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;

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
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            double newestVersion = Double.parseDouble(reader.readLine());
            if (Variables.version < newestVersion) {
                String link = reader.readLine();
                int option = JOptionPane.showOptionDialog(null, "A newer version is available. Updating is highly recommended.", "Update Available", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, ImageManager.getImageIcon("/assets/image/update.png"), new String[]{"Update", "Skip Version"}, "Update");
                if (option == 0) {
                    Desktop.getDesktop().browse(new URI(link));
                    System.exit(0);
                }
            }
        } catch (IOException | URISyntaxException e) {
            Popup.error("Update Check Failed", "Failed to check for update.", false);
        }

        window = new Window();
        window.setPanel(new Menu(window));
    }
}
