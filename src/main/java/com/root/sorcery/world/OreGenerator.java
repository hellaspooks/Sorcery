package com.root.sorcery.world;

import com.root.sorcery.Config;
import com.root.sorcery.block.ModBlock;
import net.minecraft.block.Block;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.OreFeatureConfig;
import net.minecraft.world.gen.placement.CountRangeConfig;
import net.minecraft.world.gen.placement.Placement;
import net.minecraftforge.registries.ForgeRegistries;

public class OreGenerator
{

    public static void overworldOreGen()
    {
        for (Biome biome : ForgeRegistries.BIOMES)
        {
            oreHelper(biome, ModBlock.WOLFRAMITE_ORE, Config.WOLFRAMITE_COUNT.get(), Config.WOLFRAMITE_CLUSTERS.get(), Config.WOLFRAMITE_BOTTOM_OFFSET.get(), Config.WOLFRAMITE_TOP_OFFSET.get(), Config.WOLFRAMITE_MAX_HEIGHT.get());
        }
    }

    public static void oreHelper(Biome biome, Block oreBlock, int count, int clusters, int botOffset, int topOffset, int maxHeight)
    {
        biome.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Biome.createDecoratedFeature(Feature.ORE, new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, oreBlock.getDefaultState(), clusters), Placement.COUNT_RANGE, new CountRangeConfig(count, botOffset, topOffset, maxHeight)));

    }
}
