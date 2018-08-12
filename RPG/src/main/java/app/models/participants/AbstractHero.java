package app.models.participants;

import app.contracts.Hero;
import app.contracts.Targetable;
import app.models.Config;

public abstract class AbstractHero implements Hero {

    private String name;
    private double health;
    private double damage;
    private double gold;
    private boolean isAlive;

    private int strength;
    private int dexterity;
    private int intelligence;
    private int level;

    protected AbstractHero(double health, double damage,
                        double gold, int strength, int dexterity, int intelligence) {
        this.health = health;
        this.damage = damage;
        this.gold = gold;
        this.isAlive = true;
        this.strength = strength;
        this.dexterity = dexterity;
        this.intelligence = intelligence;
        this.level = 1;
    }

    @Override
    public String attack(Targetable target) {
        if (!this.isAlive()) {
            return this.getName() + " is dead! Cannot attack.";
        }

        if (!target.isAlive()){
            return target.getName() + " is dead! Cannot be attacked.";
        }

        target.takeDamage(this.getDamage());

        String result = this.getName() + " attacked!";
        if (!target.isAlive()) {
            target.giveReward(this);
            result += String.format(" %s has been slain by %s.", target.getName(), this.getName());
        }

        return result;
    }

    @Override
    public void takeDamage(double damage) {
        this.health -= damage;
        if(this.health <= 0){
            this.isAlive = false;
        }
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public double getDamage() {
        return this.damage;
    }

    @Override
    public double getHealth() {
        return this.health;
    }

    @Override
    public double getGold() {
        return this.gold;
    }

    @Override
    public void setHealth(double health) {
        this.health = health;
    }

    @Override
    public void giveReward(Targetable targetable) {
        targetable.receiveReward(this.gold);
    }

    @Override
    public void receiveReward(double reward) {
        this.gold = this.gold + reward;
    }

    @Override
    public void levelUp() {
        this.level++;
        this.strength = this.getStrength() * Config.LEVEL_UP_MULTIPLIER;
        this.dexterity = this.getDexterity() * Config.LEVEL_UP_MULTIPLIER;
        this.intelligence = this.getIntelligence() * Config.LEVEL_UP_MULTIPLIER;
        setHealth(this.strength * Config.HERO_HEALTH_MULTIPLIER);
    }


    @Override
    public boolean isAlive() {
        return this.isAlive;
    }

    @Override
    public int getStrength() {
        return this.strength;
    }

    @Override
    public void setStrength(int strength) {
        this.strength = strength;
    }

    @Override
    public int getDexterity() {
        return this.dexterity;
    }

    @Override
    public void setDexterity(int dexterity) {
        this.dexterity = dexterity;
    }

    @Override
    public int getIntelligence() {
        return this.intelligence;
    }

    @Override
    public void setIntelligence(int intelligence) {
        this.intelligence = intelligence;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append(String.format("  Name: %s | Class: %s", this.getName(), this.getClass().getSimpleName()))
                .append(System.lineSeparator())
                .append(String.format("  Health: %.2f | Damage: %.2f", this.getHealth(), this.getDamage()))
                .append(System.lineSeparator()).append(String.format("  %d STR | %d DEX | %d INT | %.2f Gold", this.getStrength(), this.getDexterity(),
                this.getIntelligence(), this.getGold()));

        return sb.toString();
    }
}
