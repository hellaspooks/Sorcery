package com.sorcery.block.trees;

import com.sorcery.world.ModFeature;
import net.minecraft.block.trees.Tree;
import net.minecraft.world.gen.feature.*;

import javax.annotation.Nullable;
import java.util.Random;

public class RunewoodTree extends Tree
{

    @Nullable
    @Override
    protected ConfiguredFeature<BaseTreeFeatureConfig, ?> getTreeFeature(Random randomIn, boolean p_225546_2_)
    {
        return ModFeature.RUNEWOOD_TREE;
    }
}
