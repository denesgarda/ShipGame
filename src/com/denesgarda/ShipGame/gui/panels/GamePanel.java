package com.denesgarda.ShipGame.gui.panels;

import com.denesgarda.JarData.data.Serialized;
import com.denesgarda.JarData.data.statics.Serialization;
import com.denesgarda.ShipGame.Game;
import com.denesgarda.ShipGame.Main;
import com.denesgarda.ShipGame.game.Map;
import com.denesgarda.ShipGame.game.Port;
import com.denesgarda.ShipGame.gui.Panel;
import com.denesgarda.ShipGame.gui.component.Label;
import com.denesgarda.ShipGame.gui.dialogue.AmountSelector;
import com.denesgarda.ShipGame.gui.dialogue.ItemSelector;
import com.denesgarda.ShipGame.gui.dialogue.Timer;
import com.denesgarda.ShipGame.util.ImageManager;
import com.denesgarda.ShipGame.util.Popup;
import com.denesgarda.ShipGame.util.Printer;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.util.Arrays;
import java.util.Random;

public class GamePanel extends Panel {
    public Game game;

    public Label money = new Label("Money: ---", 14, true);
    public Label food = new Label("Food: ---", 14, true);
    public Label goal = new Label("Goal: ---", 14, true);

    public JButton quit = new JButton("Quit");
    public JButton save = new JButton("Save");

    public Label inventoryTitle = new Label("Inventory", 16, true);
    public Label inventoryDisplay = new Label("-----", 12, false);
    public Label portTitle = new Label("----------------", 16, true);
    public Label portDisplay = new Label("-----", 12, false);

    public JButton buy = new JButton("Buy");
    public JButton sell = new JButton("Sell");
    public JButton sail = new JButton("Sail");

    public GamePanel(JFrame parent, Game game) {
        super(parent);
        this.game = game;
        layout.putConstraint(SpringLayout.EAST, money, 0, SpringLayout.EAST, this);
        this.add(money);
        layout.putConstraint(SpringLayout.NORTH, food, 20, SpringLayout.NORTH, money);
        layout.putConstraint(SpringLayout.EAST, food, 0, SpringLayout.EAST, this);
        this.add(food);
        layout.putConstraint(SpringLayout.NORTH, goal, 20, SpringLayout.NORTH, food);
        layout.putConstraint(SpringLayout.EAST, goal, 0, SpringLayout.EAST, this);
        this.add(goal);
        layout.putConstraint(SpringLayout.NORTH, quit, 0, SpringLayout.NORTH, this);
        quit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int choice = Popup.quit();
                if (choice == 1) {
                    Main.window.setPanel(new Menu(Main.window));
                }
            }
        });
        this.add(quit);
        layout.putConstraint(SpringLayout.NORTH, save, 25, SpringLayout.NORTH, quit);
        save.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setFileFilter(new FileNameExtensionFilter("sav", "SAV File", "*.sav"));
                int result = fileChooser.showSaveDialog(null);
                if (result == JFileChooser.APPROVE_OPTION) {
                    File file = new File(fileChooser.getSelectedFile().getAbsolutePath());
                    String extension;
                    try {
                        extension = file.getName().substring(file.getName().lastIndexOf("."));
                    } catch (StringIndexOutOfBoundsException ex) {
                        extension = "";
                    }
                    if (!extension.equalsIgnoreCase("sav")) {
                        file = new File(file + ".sav");
                        file = new File(file.getParentFile(), file.getName().substring(0, file.getName().lastIndexOf(".")) + ".sav");
                    }
                    try {
                        Serialized serialized = Serialization.serialize(game);
                        file.createNewFile();
                        FileWriter fileWriter = new FileWriter(file);
                        fileWriter.write(serialized.getData());
                        fileWriter.flush();
                        fileWriter.close();
                    } catch (Exception ex) {
                        Popup.error("Save Failed", "Failed to save game.", false);
                    }
                }
            }
        });
        this.add(save);
        layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, inventoryTitle, 0, SpringLayout.HORIZONTAL_CENTER, this);
        layout.putConstraint(SpringLayout.NORTH, inventoryTitle, 50, SpringLayout.NORTH, this);
        this.add(inventoryTitle);
        layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, inventoryDisplay, 0, SpringLayout.HORIZONTAL_CENTER, this);
        layout.putConstraint(SpringLayout.NORTH, inventoryDisplay, 20, SpringLayout.NORTH, inventoryTitle);
        this.add(inventoryDisplay);
        layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, portTitle, 0, SpringLayout.HORIZONTAL_CENTER, this);
        layout.putConstraint(SpringLayout.NORTH, portTitle, 150, SpringLayout.NORTH, inventoryDisplay);
        this.add(portTitle);
        layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, portDisplay, 0, SpringLayout.HORIZONTAL_CENTER, this);
        layout.putConstraint(SpringLayout.NORTH, portDisplay, 20, SpringLayout.NORTH, portTitle);
        this.add(portDisplay);
        layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, buy, 0, SpringLayout.HORIZONTAL_CENTER, this);
        layout.putConstraint(SpringLayout.NORTH, buy, 100, SpringLayout.NORTH, portDisplay);
        buy.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ItemSelector(game.map.get(game.current).items, new ItemSelector.Selected() {
                    @Override
                    public void onSelect(String item) {
                        new AmountSelector(AmountSelector.Type.BUY, item, game.map.get(game.current), game, new AmountSelector.Inputted() {
                            @Override
                            public void onInput(AmountSelector.Type type, String item, int amount, Port port) {
                                new Timer("Executing Transaction", "Executing transaction...", new Runnable() {
                                    @Override
                                    public void run() {
                                        double cost = Math.round(port.prices[Arrays.asList(port.items).indexOf(item)] * amount * 100.0) / 100.0;
                                        game.money -= cost;
                                        game.money = Math.round(game.money * 100.0) / 100.0;
                                        game.inventory[Arrays.asList(Main.Variables.Port.items).indexOf(port.items[Arrays.asList(port.items).indexOf(item)])] += amount;
                                        money.setText("Money: " + game.money);
                                        inventoryDisplay.setText(getInventory());
                                    }
                                });
                            }
                        });
                    }
                });
            }
        });
        this.add(buy);
        layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, sell, 0, SpringLayout.HORIZONTAL_CENTER, this);
        layout.putConstraint(SpringLayout.NORTH, sell, 25, SpringLayout.NORTH, buy);
        sell.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ItemSelector(game.map.get(game.current).items, new ItemSelector.Selected() {
                    @Override
                    public void onSelect(String item) {
                        new AmountSelector(AmountSelector.Type.SELL, item, game.map.get(game.current), game, new AmountSelector.Inputted() {
                            @Override
                            public void onInput(AmountSelector.Type type, String item, int amount, Port port) {
                                new Timer("Executing Transaction", "Executing transaction...", new Runnable() {
                                    @Override
                                    public void run() {
                                        double cost = Math.round(port.prices[Arrays.asList(port.items).indexOf(item)] * amount * 100.0) / 100.0;
                                        game.money += cost;
                                        game.money = Math.round(game.money * 100.0) / 100.0;
                                        game.inventory[Arrays.asList(Main.Variables.Port.items).indexOf(port.items[Arrays.asList(port.items).indexOf(item)])] -= amount;
                                        money.setText("Money: " + game.money);
                                        inventoryDisplay.setText(getInventory());
                                        if (game.money >= game.goal) {
                                            JOptionPane.showMessageDialog(null, "You have reached the goal. YOU WIN!", "You Win", JOptionPane.INFORMATION_MESSAGE, ImageManager.getImageIcon("/assets/image/win.png"));
                                            game.won = true;
                                        }
                                    }
                                });
                            }
                        });
                    }
                });
            }
        });
        this.add(sell);
        layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, sail, 0, SpringLayout.HORIZONTAL_CENTER, this);
        layout.putConstraint(SpringLayout.NORTH, sail, 25, SpringLayout.NORTH, sell);
        sail.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Timer("Sailing", "Sailing...", new Runnable() {
                    @Override
                    public void run() {
                        sail();
                        Port port = game.map.get(game.current);
                        portTitle.setText(port.getName());
                        portDisplay.setText(port.toString());
                        inventoryDisplay.setText(getInventory());
                        if (game.food < 0) {
                            if (game.won) {
                                JOptionPane.showMessageDialog(null, "You have run out of food. But you already won by reaching the goal before!", "Game Over", JOptionPane.INFORMATION_MESSAGE, ImageManager.getImageIcon("/assets/image/lose.png"));
                            } else {
                                JOptionPane.showMessageDialog(null, "You have run out of food. YOU LOSE!", "You Lose", JOptionPane.INFORMATION_MESSAGE, ImageManager.getImageIcon("/assets/image/lose.png"));
                            }
                            Main.window.setPanel(new Menu(Main.window));
                        }
                    }
                });
            }
        });
        this.add(sail);
        game.map = new Map();
        money.setText("Money: " + game.money);
        food.setText("Food: " + game.food);
        goal.setText("Goal: " + game.goal);
        if (game.current == null) {
            sail();
            game.food += 5;
            food.setText("Food: " + game.food);
            game.inventory = new int[Main.Variables.Port.items.length];
        } else {
            game.map.refreshAll();
        }
        Port port = game.map.get(game.current);
        portTitle.setText(port.getName());
        portDisplay.setText(port.toString());
        inventoryDisplay.setText(getInventory());
    }

    private void sail() {
        game.food -= 5;
        food.setText("Food: " + game.food);
        String old = game.current;
        if (old == null) {
            old = "";
        }
        do {
            game.current = game.map.ports[new Random().nextInt(game.map.ports.length)].name;
        } while (old.equals(game.current));
        game.map.refreshAll();
    }

    private String getInventory() {
        String inv = "<html>";
        for (int i = 0; i < game.inventory.length; i ++) {
            inv += Printer.dot(Main.Variables.Port.items[i], String.valueOf(game.inventory[i]), 40) + "<br>";
        }
        inv = inv.substring(0, inv.length() - 1);
        inv += "</html>";
        return inv;
    }

    private String listCurrent(String name) {
        String s = "";
        Port port = game.map.get(name);
        for (int i = 0; i < port.items.length; i++) {
            s += (i + 1) + ": " + port.items[i] + "\n";
        }
        return s.substring(0, s.length() - 1);
    }

    private String listPorts() {
        String s = "";
        for (int i = 0; i < game.map.ports.length; i++) {
            int food = (int) Math.round(Math.sqrt(Math.pow((game.map.get(game.current).x-game.map.ports[i].x), 2) + Math.pow((game.map.get(game.current).y-game.map.ports[i].y), 2)));
            s += Printer.dot((i + 1) + ": " + game.map.ports[i].name, food + " Food\n", 40);
        }
        return s.substring(0, s.length() - 1);
    }
}
