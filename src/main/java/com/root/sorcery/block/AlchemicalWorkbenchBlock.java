package com.root.sorcery.block;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;

public class AlchemicalWorkbenchBlock extends Block
{
    private static Float hardness   = 3.0F;
    private static Float resistance = 6.0F;

    public AlchemicalWorkbenchBlock()
    {
        super(Block.Properties.create(Material.ROCK).hardnessAndResistance(hardness, resistance).sound(SoundType.STONE));
    }
}
