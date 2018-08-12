package hell.entities.miscellaneous;

import hell.interfaces.Inventory;
import hell.interfaces.Item;
import hell.interfaces.Recipe;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.*;
import java.lang.reflect.Field;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

public class HeroInventoryTest {

    private Inventory inventory;

    private static final int VALUE = Integer.MAX_VALUE;
    private static final String STRENGTH_MESSAGE = "Strength is not correct!";
    private static final String AGILITY_MESSAGE = "Agility is not correct!";
    private static final String INTELLIGENCE_MESSAGE = "Intelligence is not correct!";
    private static final String HIT_POINTS_MESSAGE = "Hit points is not correct!";
    private static final String DAMAGE_MESSAGE = "Damage is not correct!";

    @Before
    public void setUp() throws Exception {
        this.inventory = new HeroInventory();
    }

    private Item createItem(){
        Item item = Mockito.mock(Item.class);

        Mockito.when(item.getName()).thenReturn(String.valueOf(Math.random()));
        Mockito.when(item.getStrengthBonus()).thenReturn(VALUE);
        Mockito.when(item.getAgilityBonus()).thenReturn(VALUE);
        Mockito.when(item.getIntelligenceBonus()).thenReturn(VALUE);
        Mockito.when(item.getHitPointsBonus()).thenReturn(VALUE);
        Mockito.when(item.getDamageBonus()).thenReturn(VALUE);

        return item;
    }

    private void seedItemsInInventory(){
        this.inventory.addCommonItem(createItem());
        this.inventory.addCommonItem(createItem());
        this.inventory.addCommonItem(createItem());
    }

    @Test
    public void getTotalStrengthBonus() {
        //Arrange
        this.seedItemsInInventory();

        //Act
        long realValue = this.inventory.getTotalStrengthBonus();
        long expectedValue = 3L * VALUE;

        //Assert
        Assert.assertEquals(STRENGTH_MESSAGE, expectedValue , realValue);
    }

    @Test
    public void getTotalAgilityBonus() {
        //Arrange
        this.seedItemsInInventory();

        //Act
        long realValue = this.inventory.getTotalAgilityBonus();
        long expectedValue = 3L * VALUE;

        //Assert
        Assert.assertEquals(AGILITY_MESSAGE, expectedValue , realValue);
    }

    @Test
    public void getTotalIntelligenceBonus() {
        //Arrange
        this.seedItemsInInventory();

        //Act
        long realValue = this.inventory.getTotalIntelligenceBonus();
        long expectedValue = 3L * VALUE;

        //Assert
        Assert.assertEquals(INTELLIGENCE_MESSAGE, expectedValue , realValue);
    }

    @Test
    public void getTotalHitPointsBonus() {
        //Arrange
        this.seedItemsInInventory();

        //Act
        long realValue = this.inventory.getTotalHitPointsBonus();
        long expectedValue = 3L * VALUE;

        //Assert
        Assert.assertEquals(HIT_POINTS_MESSAGE, expectedValue , realValue);
    }

    @Test
    public void getTotalDamageBonus() {
        //Arrange
        this.seedItemsInInventory();

        //Act
        long realValue = this.inventory.getTotalDamageBonus();
        long expectedValue = 3L * VALUE;

        //Assert
        Assert.assertEquals(DAMAGE_MESSAGE, expectedValue , realValue);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void addCommonItem() throws IllegalAccessException {
        this.seedItemsInInventory();
        Item commonItemMock4 = Mockito.mock(Item.class);
        Mockito.when(commonItemMock4.getName()).thenReturn("commonItemMock4");

        // Act
        this.inventory.addCommonItem(commonItemMock4);
        int actualCountOfCommonItems = 0;

        try {
            Field commonItemsField = this.inventory.getClass().getDeclaredField("commonItems");
            commonItemsField.setAccessible(true);
            Map<String, Item> commonItemsMap = (Map<String, Item>) commonItemsField.get(this.inventory);
            actualCountOfCommonItems = commonItemsMap.size();
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }

        int expectedCountOfCommonItems = 4;

        // Assert
        Assert.assertEquals(expectedCountOfCommonItems, actualCountOfCommonItems);
    }

    @Test
    public void addRecipeItem() {
        // Arrange
        Recipe recipeMock = Mockito.mock(Recipe.class);

        // Act
        this.inventory.addRecipeItem(recipeMock);
        int actualCountOfRecipes = 0;

        try {
            Field recipesField = this.inventory.getClass().getDeclaredField("recipeItems");
            recipesField.setAccessible(true);
            Map<String, Recipe> recipesMap = (Map<String, Recipe>) recipesField.get(this.inventory);
            actualCountOfRecipes = recipesMap.size();
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }

        int expectedCountOfRecipes = 1;

        // Assert
        Assert.assertEquals(expectedCountOfRecipes, actualCountOfRecipes);
    }
}