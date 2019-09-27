package com.root.sorcery.block;

import net.minecraft.block.Block;
import net.minecraft.block.StairsBlock;

public class StairMod extends StairsBlock {


    public StairMod(String registryName, Block block) {
        super(block.getDefaultState(), Block.Properties.from(block));
        this.setRegistryName(registryName);

    }
}
