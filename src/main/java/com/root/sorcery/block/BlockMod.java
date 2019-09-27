package com.root.sorcery.block;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

public class BlockMod extends Block
{
    public BlockMod(String registryName, Material material, Float hardness, Float resistance, SoundType sound)
    {
        super(Properties
                .create(material)
                .sound(sound)
                .hardnessAndResistance(hardness, resistance));

        setRegistryName(registryName);

    }
}
