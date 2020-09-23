package com.sorcery.block;

import com.sorcery.block.trees.RunewoodTree;
import net.minecraft.block.Block;
import net.minecraft.block.SaplingBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

public class RunewoodSaplingBlock extends SaplingBlock
{
    public RunewoodSaplingBlock()
    {
        // added plant sound
        super(new RunewoodTree(), Block.Properties
            .create(Material.PLANTS)
            .hardnessAndResistance(1.0F, 1.0F)
            .sound(SoundType.PLANT));
    }
}
