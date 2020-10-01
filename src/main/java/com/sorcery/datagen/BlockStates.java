package com.sorcery.datagen;

import com.sorcery.Constants;
import com.sorcery.block.ModBlock;
import com.sorcery.entity.ModEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.StairsBlock;
import net.minecraft.data.DataGenerator;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;

import java.util.function.Function;

public class BlockStates extends BlockStateProvider
{

    public BlockStates(DataGenerator gen, ExistingFileHelper exFileHelper) {
        super(gen, Constants.MODID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        // Simple Blocks
        simpleBlock(ModBlock.RUNESTONE_BRICKS.get());
        simpleBlock(ModBlock.CHISELED_RUNESTONE.get());
        simpleBlock(ModBlock.LAPIS_RUNESTONE.get());
        simpleBlock(ModBlock.DARK_RUNESTONE.get());
        simpleBlock(ModBlock.SOLAR_RUNESTONE.get());
        simpleBlock(ModBlock.LUNAR_RUNESTONE.get());


        stairsBlock((StairsBlock) ModBlock.RUNESTONE_BRICK_STAIRS.get(), new ResourceLocation(Constants.MODID, "block/runestone_bricks"));
        stairsBlock((StairsBlock) ModBlock.RUNEWOOD_PLANK_STAIRS.get(), new ResourceLocation(Constants.MODID, "block/runewood_planks"));
    }


}
