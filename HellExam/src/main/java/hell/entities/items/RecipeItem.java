package hell.entities.items;

import hell.interfaces.Recipe;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class RecipeItem extends BaseItem implements Recipe {

    private List<String> requiredItems;

    public RecipeItem(String name, int strengthBonus, int agilityBonus, int intelligenceBonus,
                         int hitPointsBonus, int damageBonus, String... requiredItems) {
        super(name, strengthBonus, agilityBonus, intelligenceBonus, hitPointsBonus, damageBonus);
        this.requiredItems = Arrays.stream(requiredItems).collect(Collectors.toList());
    }

    @Override
    public List<String> getRequiredItems() {
        return Collections.unmodifiableList(this.requiredItems);
    }
}
