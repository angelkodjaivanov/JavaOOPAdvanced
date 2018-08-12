package app.models.participants;

import app.contracts.Targetable;
import app.models.Config;

public class Boss implements Targetable{

    private String name;
    private double health;
    private double damage;
    private double gold;
    private boolean isAlive;

    public Boss(String name) {
        this.name = name;
        this.health = Config.BOSS_HEALTH;
        this.damage = Config.BOSS_DAMAGE;
        this.gold = Config.BOSS_GOLD;
        this.isAlive = true;
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

        return this.getName() + " attacked!";
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
        this.gold += reward * Config.BOSS_GOLD_MULTIPLIER;
    }

    @Override
    public void levelUp() {
        this.setHealth(Config.BOSS_HEALTH);
    }

    @Override
    public boolean isAlive() {
        return this.isAlive;
    }

    @Override
    public String toString() {
        return String.format("  Name: %s | Class: Boss\n" +
                "  Health: %.2f | Damage: %.2f | %.2f Gold", this.name,
                this.health, this.damage, this.gold);
    }
}
