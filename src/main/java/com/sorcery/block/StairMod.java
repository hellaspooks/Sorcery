package com.sorcery.block;

import net.minecraft.block.Block;
import net.minecraft.block.StairsBlock;

public class StairMod extends StairsBlock {


    public StairMod(Block block) {
        super(block.getDefaultState(), Block.Properties.from(block));

    }
}
