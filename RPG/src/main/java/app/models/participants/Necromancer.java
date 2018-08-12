package app.models.participants;

import app.contracts.Hero;
import app.models.Config;

public class Necromancer extends AbstractHero{

    public Necromancer(String name) {
        super(Config.NECROMANCER_BASE_STRENGTH * Config.HERO_HEALTH_MULTIPLIER
                , Config.NECROMANCER_BASE_INTELLIGENCE * 5 + Config.NECROMANCER_BASE_DEXTERITY,
                Config.HERO_START_GOLD, Config.NECROMANCER_BASE_STRENGTH, Config.NECROMANCER_BASE_DEXTERITY,
                Config.NECROMANCER_BASE_INTELLIGENCE);
        this.setName(name);
    }

    public Necromancer(){
        super(Config.NECROMANCER_BASE_STRENGTH * Config.HERO_HEALTH_MULTIPLIER
                , Config.NECROMANCER_BASE_INTELLIGENCE * 5 + Config.NECROMANCER_BASE_DEXTERITY,
                Config.HERO_START_GOLD, Config.NECROMANCER_BASE_STRENGTH, Config.NECROMANCER_BASE_DEXTERITY,
                Config.NECROMANCER_BASE_INTELLIGENCE);
    }

    @Override
    public double getDamage() {
        return (this.getStrength() + this.getDexterity() + this.getIntelligence()) * 2;
    }
}
