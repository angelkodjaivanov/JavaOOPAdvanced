package app.models.participants;

import app.contracts.Hero;
import app.models.Config;

public class Wizard extends AbstractHero {

    public Wizard(String name) {
        super(Config.WIZARD_BASE_STRENGTH * Config.HERO_HEALTH_MULTIPLIER
                , (Config.WIZARD_BASE_STRENGTH + Config.WIZARD_BASE_INTELLIGENCE
                        + Config.WIZARD_BASE_DEXTERITY)* 2, Config.HERO_START_GOLD
        ,Config.WIZARD_BASE_STRENGTH, Config.WIZARD_BASE_DEXTERITY, Config.WIZARD_BASE_INTELLIGENCE);
        this.setName(name);
    }

    public Wizard(){
        super(Config.WIZARD_BASE_STRENGTH * Config.HERO_HEALTH_MULTIPLIER
                , (Config.WIZARD_BASE_STRENGTH + Config.WIZARD_BASE_INTELLIGENCE
                        + Config.WIZARD_BASE_DEXTERITY)* 2, Config.HERO_START_GOLD
                ,Config.WIZARD_BASE_STRENGTH, Config.WIZARD_BASE_DEXTERITY, Config.WIZARD_BASE_INTELLIGENCE);
    }

    @Override
    public double getDamage() {
        return this.getIntelligence() * 5 + this.getDexterity();
    }



}
