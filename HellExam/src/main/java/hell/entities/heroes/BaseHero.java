package hell.entities.heroes;

import hell.entities.miscellaneous.ItemCollection;
import hell.interfaces.Hero;
import hell.interfaces.Inventory;
import hell.interfaces.Item;
import hell.interfaces.Recipe;

import java.util.Map;
import java.lang.reflect.Field;
import java.util.Collection;

public abstract class BaseHero implements Hero {

    private String name;
    private int strength;
    private int agility;
    private int intelligence;
    private int hitPoints;
    private int damage;
    private Inventory inventory;

    protected BaseHero(String name, int strength, int agility,
                    int intelligence, int hitPoints, int damage, Inventory inventory) {
        this.name = name;
        this.strength = strength;
        this.agility = agility;
        this.intelligence = intelligence;
        this.hitPoints = hitPoints;
        this.damage = damage;
        this.inventory = inventory;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public long getStrength() {
        return this.strength + inventory.getTotalStrengthBonus();
    }

    @Override
    public long getAgility() {
        return this.agility + inventory.getTotalAgilityBonus();
    }

    @Override
    public long getIntelligence() {
        return this.intelligence + inventory.getTotalIntelligenceBonus();
    }

    @Override
    public long getHitPoints() {
        return this.hitPoints + inventory.getTotalHitPointsBonus();
    }

    @Override
    public long getDamage() {
        return this.damage + inventory.getTotalDamageBonus();
    }

    @Override
    @SuppressWarnings("unchecked")
    public Collection<Item> getItems() throws IllegalAccessException {

        Collection<Item> items = null;

        Field[] declaredFields = this.inventory.getClass().getDeclaredFields();
        for (Field declaredField : declaredFields) {
            if(declaredField.isAnnotationPresent(ItemCollection.class)) {
                declaredField.setAccessible(true);
                Map<String, Item> itemMap = (Map<String, Item>) declaredField.get(this.inventory);
                items = itemMap.values();
            }
        }

        return items;
    }

    @Override
    public void addItem(Item item) {
        this.inventory.addCommonItem(item);
    }

    @Override
    public void addRecipe(Recipe recipe) {
        this.inventory.addRecipeItem(recipe);
    }
}
