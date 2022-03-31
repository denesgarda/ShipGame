package com.denesgarda.ShipGame.game;

import com.denesgarda.ShipGame.util.Printer;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Stream;

public class Port implements Serializable {
    public String name;
    public int x;
    public int y;
    public String[] items;
    public double[] prices;
    public double[] means;
    public double[] deviations;

    public Port(String name, String[] items, double[] means, double[] deviations, int x, int y) {
        this.name = name;
        this.items = items;
        this.prices = new double[items.length];
        this.means = means;
        this.deviations = deviations;
        this.x = x;
        this.y = y;
    }

    public void refresh() {
        Random random = new Random();
        for (int i = 0; i < items.length; i++) {
            double price;
            do {
                price = Math.round(random.nextGaussian(means[i], deviations[i]) * 100.0) / 100.0;
            } while (price <= 0);
            prices[i] = price;
        }
    }

    public static Port generate(String name, String[] allItems, double[] allMeans) {
        String[] aio = Arrays.copyOf(allItems, allItems.length);
        Random random = new Random();
        int use = (int) Math.round(allItems.length * 0.66);
        List<String> ail = Arrays.asList(allItems);
        Collections.shuffle(ail);
        String[] items = Arrays.copyOfRange(ail.toArray(), 0, use, String[].class);
        List<Double> cm = new ArrayList<>();
        for (String item : items) {
            cm.add(allMeans[Arrays.asList(aio).indexOf(item)]);
        }
        Double[] pMeans = Arrays.copyOf(cm.toArray(), cm.size(), Double[].class);
        double[] means = Stream.of(pMeans).mapToDouble(Double::doubleValue).toArray();
        List<Double> dl = new ArrayList<>();
        for (int i = 0; i < use; i++) {
            dl.add(random.nextDouble(5) + 1);
        }
        Double[] pDeviations = Arrays.copyOf(dl.toArray(), dl.size(), Double[].class);
        double[] deviations = Stream.of(pDeviations).mapToDouble(Double::doubleValue).toArray();
        return new Port(name, items, means, deviations, random.nextInt(15), random.nextInt(15));
    }

    @Override
    public String toString() {
        String s = "<html>";
        for (int i = 0; i < items.length; i++) {
            s += Printer.dot(items[i], String.valueOf(prices[i]), 40) + "<br>";
        }
        s =  s.substring(0, s.length() - 1);
        s += "</html>";
        return s;
    }

    public String getName() {
        return name + " Trading Port";
    }
}
