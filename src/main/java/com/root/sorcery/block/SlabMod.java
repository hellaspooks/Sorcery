package com.root.sorcery.block;

import net.minecraft.block.Block;
import net.minecraft.block.SlabBlock;

public class SlabMod extends SlabBlock {
    public SlabMod(String registryName, Block block) {
        super(Block.Properties.from(block));
        this.setRegistryName(registryName);
    }
}
