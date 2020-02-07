package com.root.sorcery.block;

import com.root.sorcery.block.trees.RunewoodTree;
import net.minecraft.block.Block;
import net.minecraft.block.SaplingBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

public class RunewoodSaplingBlock extends SaplingBlock
{
    public RunewoodSaplingBlock()
    {
        super(new RunewoodTree(), Block.Properties.create(Material.PLANTS).doesNotBlockMovement().tickRandomly().hardnessAndResistance(0, 0).sound(SoundType.PLANT));
    }
}
