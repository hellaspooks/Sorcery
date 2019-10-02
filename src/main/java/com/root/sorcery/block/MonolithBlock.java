package com.root.sorcery.block;

import com.root.sorcery.tileentity.MonolithTile;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;

import javax.annotation.Nullable;

public class MonolithBlock extends Block
{
    private static Float hardness   = 3.0F;
    private static Float resistance = 6.0F;

    public MonolithBlock()
    {
        super(Properties.create(Material.ROCK).hardnessAndResistance(hardness, resistance).sound(SoundType.STONE));
    }

    @Override
    public boolean isNormalCube(BlockState state, IBlockReader worldIn, BlockPos pos)
    {
        return false;
    }

    @Override
    public boolean isSolid(BlockState state)
    {
        return false;
    }

    @Override
    public BlockRenderType getRenderType(BlockState state)
    {
        return BlockRenderType.MODEL;
    }

    @Override
    public boolean hasTileEntity()
    {
        return true;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world)
    {
        return new MonolithTile();
    }

}
