package com.root.sorcery.block;

import com.root.sorcery.tileentity.ConjuredLightTile;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockReader;

import javax.annotation.Nullable;

public class ConjuredLight extends Block
{
    public ConjuredLight()
    {
        super(Properties.create(Material.AIR).hardnessAndResistance(0, 0).sound(SoundType.LANTERN).lightValue(15));
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world)
    {
        return new ConjuredLightTile();
    }
}
