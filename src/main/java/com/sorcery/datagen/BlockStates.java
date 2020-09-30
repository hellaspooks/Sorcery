package com.sorcery.datagen;

import com.sorcery.Constants;
import com.sorcery.block.ModBlock;
import net.minecraft.data.DataGenerator;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

public class BlockStates extends BlockStateProvider
{

    public BlockStates(DataGenerator gen, ExistingFileHelper exFileHelper) {
        super(gen, Constants.MODID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        System.out.println("blockstate datagen running");

        // Simple Blocks
        simpleBlock(ModBlock.RUNESTONE_BRICKS.get());
        simpleBlock(ModBlock.CHISELED_RUNESTONE.get());
        simpleBlock(ModBlock.LAPIS_RUNESTONE.get());
        simpleBlock(ModBlock.DARK_RUNESTONE.get());
        simpleBlock(ModBlock.SOLAR_RUNESTONE.get());
        simpleBlock(ModBlock.LUNAR_RUNESTONE.get());
    }

}
