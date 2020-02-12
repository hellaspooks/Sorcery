package com.root.sorcery.block;

import com.root.sorcery.tileentity.DarkMonolithTile;
import com.root.sorcery.tileentity.LunarMonolithTile;
import net.minecraft.block.BlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockReader;

import javax.annotation.Nullable;

public class DarkMonolithBlock extends BasicMonolithBlock
{
    public DarkMonolithBlock()
    {
        super();
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world)
    {
        return new DarkMonolithTile();
    }
}
