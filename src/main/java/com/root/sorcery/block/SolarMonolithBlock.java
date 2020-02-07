package com.root.sorcery.block;

import com.root.sorcery.tileentity.SolarMonolithTile;
import net.minecraft.block.BlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockReader;

import javax.annotation.Nullable;

public class SolarMonolithBlock extends BasicMonolithBlock
{
    public SolarMonolithBlock()
    {
        super();
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world)
    {
        return new SolarMonolithTile();
    }
}
