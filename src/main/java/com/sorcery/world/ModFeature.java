package com.sorcery.world;

import com.sorcery.block.ModBlock;
import net.minecraft.world.gen.blockstateprovider.SimpleBlockStateProvider;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.foliageplacer.BlobFoliagePlacer;
import net.minecraft.world.gen.trunkplacer.StraightTrunkPlacer;


public abstract class ModFeature
{
        public static final ConfiguredFeature<BaseTreeFeatureConfig, ?> RUNEWOOD_TREE = Feature.TREE.withConfiguration((new BaseTreeFeatureConfig.Builder(new SimpleBlockStateProvider(ModBlock.RUNEWOOD_LOG.get().getDefaultState()), new SimpleBlockStateProvider(ModBlock.RUNEWOOD_LOG.get().getDefaultState()), new BlobFoliagePlacer(FeatureSpread.func_242252_a(2), FeatureSpread.func_242252_a(0), 3), new StraightTrunkPlacer(4, 2, 0), new TwoLayerFeature(1, 0, 1))).setIgnoreVines().build());
}
