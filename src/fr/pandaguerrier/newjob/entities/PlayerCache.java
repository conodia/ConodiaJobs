package fr.pandaguerrier.newjob.entities;

public class PlayerCache {
    int level;
    double xp;

    public PlayerCache(int level, double xp) {
        this.level = level;
        this.xp = xp;
    }

    public int getLevel() {
        return level;
    }
    public double getXp() {
        return xp;
    }

    public void addLevel() {
        level++;
    }
    public void addXp(double xp) {
        this.xp += xp;
    }

    public void setLevel(int level) {
        this.level = level;
    }
    public void setXp(double xp) {
        this.xp = xp;
    }
}