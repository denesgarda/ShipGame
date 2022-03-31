package com.denesgarda.ShipGame.game;

import com.denesgarda.ShipGame.Main;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Map implements Serializable {
    public Port[] ports;

    public Map() {
        Random random = new Random();
        List<String> available = new ArrayList<>(Arrays.asList(Main.Variables.Port.names));
        List<Port> ports = new ArrayList<>();
        while (available.size() > 0) {
            String name = available.get(random.nextInt(available.size()));
            double[][] ranges = Arrays.copyOf(Main.Variables.Port.means, Main.Variables.Port.means.length);
            double[] means = new double[ranges.length];
            for (int i = 0; i < ranges.length; i++) {
                double[] range = ranges[i];
                double add = range[0];
                double dif = range[1] - range[0];
                means[i] = random.nextDouble(dif) + add;
            }
            ports.add(Port.generate(name, Arrays.copyOf(Main.Variables.Port.items, Main.Variables.Port.items.length), means));
            available.remove(name);
        }
        this.ports = Arrays.copyOf(ports.toArray(), ports.size(), Port[].class);
    }

    public void refreshAll() {
        for (Port port : ports) {
            port.refresh();
        }
    }

    public Port get(String name) {
        for (Port port : ports) {
            if (port.name.equals(name)) {
                return port;
            }
        }
        return null;
    }
}
