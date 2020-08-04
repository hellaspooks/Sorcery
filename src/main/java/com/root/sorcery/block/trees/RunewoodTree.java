package com.root.sorcery.block.trees;

import com.root.sorcery.block.ModBlock;
import com.root.sorcery.world.ModFeature;
import net.minecraft.block.trees.Tree;
import net.minecraft.world.biome.DefaultBiomeFeatures;
import net.minecraft.world.gen.feature.*;

import javax.annotation.Nullable;
import java.util.Random;

public class RunewoodTree extends Tree
{

    @Nullable
    @Override
    protected ConfiguredFeature<TreeFeatureConfig, ?> getTreeFeature(Random randomIn, boolean p_225546_2_)
    {
        return ModFeature.RUNEWOOD_TREE.withConfiguration(DefaultBiomeFeatures.OAK_TREE_CONFIG);
    }
}
