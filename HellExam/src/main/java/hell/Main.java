package hell;

import hell.entities.heroes.Assassin;
import hell.entities.heroes.Barbarian;
import hell.entities.heroes.Wizard;
import hell.entities.items.CommonItem;
import hell.entities.items.RecipeItem;
import hell.entities.miscellaneous.HeroInventory;
import hell.interfaces.*;
import hell.io.ConsoleReader;
import hell.io.ConsoleWriter;

import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        InputReader reader = new ConsoleReader();
        OutputWriter writer = new ConsoleWriter();

        Map<String, Hero> heroes = new LinkedHashMap<>();

        String line;
        while (true){
            if("Quit".equals(line = reader.readLine())){
                quit(heroes);
                break;
            }

            String[] tokens = line.split("\\s+");
            String command = tokens[0];
            String[] arguments = Arrays.stream(tokens).skip(1).toArray(String[]::new);

            switch (command){
                case "Hero":
                    Hero hero = createHero(arguments);
                    heroes.put(hero.getName(), hero);
                    writer.writeLine(String.format("Created %s - %s",
                            hero.getClass().getSimpleName(), hero.getName()));
                    break;
                case "Item":
                    addCommonItemToAHero(arguments, heroes);
                    break;
                case "Recipe":
                    addRecipeToAHero(arguments, heroes);
                    break;
                case "Inspect":
                    try {
                        inspectHero(arguments[0], heroes);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                    break;
                default:
                    System.out.println("Not a valid command!");
                    break;
            }

        }
    }

    private static Hero createHero(String[] arguments) {
        Hero hero = null;
        switch (arguments[1]){
            case "Barbarian":
                hero = new Barbarian(arguments[0], new HeroInventory());
                break;
            case "Assassin":
                hero = new Assassin(arguments[0], new HeroInventory());
                break;
            case "Wizard":
                hero = new Wizard(arguments[0], new HeroInventory());
                break;
        }
        return hero;
    }

    private static void addCommonItemToAHero(String[] arguments, Map<String, Hero> heroes) {
        Item item = new CommonItem(arguments[0], Integer.parseInt(arguments[2]), Integer.parseInt(arguments[3])
            ,Integer.parseInt(arguments[4]), Integer.parseInt(arguments[5]), Integer.parseInt(arguments[6]));

        Hero hero = heroes.get(arguments[1]);
        hero.addItem(item);

        System.out.println(String.format("Added item - %s to Hero - %s",
                item.getName(), hero.getName()
                ));
    }

    private static void addRecipeToAHero(String[] arguments, Map<String,Hero> heroes) {
        Recipe recipe = new RecipeItem(arguments[0], Integer.parseInt(arguments[2]), Integer.parseInt(arguments[3])
                ,Integer.parseInt(arguments[4]), Integer.parseInt(arguments[5]), Integer.parseInt(arguments[6]),
                Arrays.stream(arguments).skip(7).toArray(String[]::new));

        Hero hero = heroes.get(arguments[1]);
        hero.addRecipe(recipe);

        System.out.println(String.format("Added recipe - %s to Hero - %s",
                recipe.getName(), hero.getName()
                ));
    }

    private static void inspectHero(String name, Map<String,Hero> heroes) throws IllegalAccessException {
        Hero hero = heroes.get(name);
        System.out.println(String.format("Hero: %s, Class: %s\n" +
                "HitPoints: %d, Damage: %d\n" +
                "Strength: %d\n" +
                "Agility: %d\n" +
                "Intelligence: %d",
                hero.getName(), hero.getClass().getSimpleName(), hero.getHitPoints(),
                hero.getDamage(), hero.getStrength(), hero.getAgility(), hero.getIntelligence()));

        if(hero.getItems().isEmpty()){
            System.out.println("Items: None");
        }
        else{
            System.out.println("Items:");
            for (Item item:hero.getItems()) {
                System.out.println(String.format(
                        "###Item: %s\n" +
                        "###+%d Strength\n" +
                        "###+%d Agility\n" +
                        "###+%d Intelligence\n" +
                        "###+%d HitPoints\n" +
                        "###+%d Damage",
                        item.getName(), item.getStrengthBonus(), item.getAgilityBonus(),
                        item.getIntelligenceBonus(), item.getHitPointsBonus(), item.getDamageBonus()));
            }
        }
    }

    private static void quit(Map<String,Hero> heroes) {
        final int[] index = {1};
        heroes.values().stream().sorted(getHeroesComparator())
                .forEach(hero -> {
                    System.out.println(String.format("%d. %s: %s\n" +
                            "###HitPoints: %d\n" +
                            "###Damage: %d\n" +
                            "###Strength: %d\n" +
                            "###Agility: %d\n" +
                            "###Intelligence: %d",
                            index[0], hero.getClass().getSimpleName(), hero.getName(), hero.getHitPoints(),
                            hero.getDamage(), hero.getStrength(), hero.getAgility(), hero.getIntelligence()));
                    try {
                        String items = hero.getItems().size() == 0 ?
                                "None" :
                                hero.getItems()
                                        .stream()
                                        .map(Item::getName)
                                        .collect(Collectors.joining(", "));
                        System.out.println("###Items: " + items);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                    index[0]++;
                });
    }

    private static Comparator<? super Hero> getHeroesComparator() {
        return (hero1, hero2) -> {
            long firstComparatorSumHero1 = hero1.getStrength() + hero1.getAgility() + hero1.getIntelligence();
            long firstComparatorSumHero2 = hero2.getStrength() + hero2.getAgility() + hero2.getIntelligence();

            int comparatorByFirstSum = Long.compare(firstComparatorSumHero2, firstComparatorSumHero1);

            if (comparatorByFirstSum != 0) {
                return comparatorByFirstSum;
            }

            long secondComparatorSumHero1 = hero1.getHitPoints() + hero1.getDamage();
            long secondComparatorSumHero2 = hero2.getHitPoints() + hero2.getDamage();

            return Long.compare(secondComparatorSumHero2, secondComparatorSumHero1);
        };
    }

}