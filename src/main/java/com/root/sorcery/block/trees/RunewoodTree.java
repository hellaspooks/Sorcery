package com.root.sorcery.block.trees;

import com.root.sorcery.block.ModBlock;
import net.minecraft.block.trees.Tree;
import net.minecraft.world.gen.feature.AbstractTreeFeature;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.feature.TreeFeature;

import javax.annotation.Nullable;
import java.util.Random;

public class RunewoodTree extends Tree
{
    @Nullable
    @Override
    protected AbstractTreeFeature<NoFeatureConfig> getTreeFeature(Random random)
    {
        return new TreeFeature(NoFeatureConfig::deserialize, true, 8, ModBlock.RUNEWOOD_LOG.getDefaultState(), ModBlock.RUNEWOOD_LEAVES.getDefaultState(), false);
    }
}
