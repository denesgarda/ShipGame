package com.denesgarda.ShipGame;

import com.denesgarda.ShipGame.game.Map;

import java.io.Serializable;

public class Game implements Serializable {
    public double version;
    public int food;
    public double money;
    public int goal;
    public GameMode gameMode;
    public boolean won;
    public Map map = null;
    public int[] inventory = null;
    public String current;

    public Game(double version, int food, double money, int goal, GameMode gameMode) {
        this.version = version;
        this.food = food;
        this.money = money;
        this.goal = goal;
        this.gameMode = gameMode;
        won = false;
    }

    public enum GameMode {
        EASY,
        MEDIUM,
        HARD,
        IMPOSSIBLE,
        CUSTOM
    }
}
