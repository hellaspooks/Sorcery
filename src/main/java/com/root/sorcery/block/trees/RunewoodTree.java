package com.root.sorcery.block.trees;

import com.root.sorcery.block.ModBlock;
import com.root.sorcery.world.ModFeature;
import net.minecraft.block.trees.Tree;
import net.minecraft.world.gen.blockstateprovider.SimpleBlockStateProvider;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.foliageplacer.BlobFoliagePlacer;

import javax.annotation.Nullable;
import java.util.Random;

public class RunewoodTree extends Tree
{

    @Nullable
    @Override
    protected ConfiguredFeature<TreeFeatureConfig, ?> getTreeFeature(Random randomIn, boolean p_225546_2_)
    {
        return ModFeature.RUNEWOOD_TREE.withConfiguration(new TreeFeatureConfig.Builder(new SimpleBlockStateProvider(ModBlock.RUNEWOOD_LOG.getDefaultState()), new SimpleBlockStateProvider(ModBlock.RUNEWOOD_LEAVES.getDefaultState()), new BlobFoliagePlacer(2, 0)).baseHeight(4).heightRandA(2).foliageHeight(3).ignoreVines().setSapling((net.minecraftforge.common.IPlantable) ModBlock.RUNEWOOD_SAPLING).build());
    }
}
