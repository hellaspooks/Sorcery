package com.root.sorcery.block;

import com.root.sorcery.block.trees.RunewoodTree;
import net.minecraft.block.Block;
import net.minecraft.block.SaplingBlock;
import net.minecraft.block.material.Material;

public class RunewoodSaplingBlock extends SaplingBlock
{
    public RunewoodSaplingBlock()
    {
        super(new RunewoodTree(), Block.Properties.create(Material.PLANTS).hardnessAndResistance(1.0F, 1.0F));
    }
}
