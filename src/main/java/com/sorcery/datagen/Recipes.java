package com.sorcery.datagen;

import com.sorcery.block.ModBlock;
import net.minecraft.advancements.criterion.InventoryChangeTrigger;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.IFinishedRecipe;
import net.minecraft.data.RecipeProvider;
import net.minecraft.data.ShapedRecipeBuilder;

import java.util.function.Consumer;

public class Recipes extends RecipeProvider
{

    public Recipes(DataGenerator generatorIn) {
        super(generatorIn);
    }

    @Override
    protected void registerRecipes(Consumer<IFinishedRecipe> consumer) {
        ShapedRecipeBuilder.shapedRecipe(ModBlock.RUNESTONE_BRICKS.get())
                .patternLine("xx")
                .patternLine("xx")
                .key('x', ModBlock.POLISHED_RUNESTONE.get())
                .setGroup("sorcery")
                .addCriterion("sorcery:polished_runestone", InventoryChangeTrigger.Instance.forItems(ModBlock.POLISHED_RUNESTONE.get()))
                .build(consumer);
    }
}
