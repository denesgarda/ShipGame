package com.denesgarda.ShipGame;

import java.io.Serializable;

public class Game implements Serializable {
    public int food;
    public int money;
    public int goal;

    public Game(int food, int money, int goal) {
        this.food = food;
        this.money = money;
        this.goal = goal;
    }
}
