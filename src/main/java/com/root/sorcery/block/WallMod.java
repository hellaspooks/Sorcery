package com.root.sorcery.block;

import net.minecraft.block.Block;
import net.minecraft.block.WallBlock;

public class WallMod extends WallBlock {
    public WallMod(String registryName, Block block) {
        super(Block.Properties.from(block));
        this.setRegistryName(registryName);
    }
}
