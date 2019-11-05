package com.root.sorcery.block;

import com.root.sorcery.tileentity.PodiumTile;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.items.IItemHandler;

import javax.annotation.Nullable;

public class PodiumBlock extends Block
{
    private static Float hardness   = 3.0F;
    private static Float resistance = 6.0F;

    protected static final VoxelShape SHAPE = Block.makeCuboidShape(2,0,2,14,16,14);

    public PodiumBlock()
    {
        super(Properties.create(Material.ROCK).hardnessAndResistance(hardness, resistance).sound(SoundType.STONE));
    }

    @Override
    public boolean onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit)
    {
        if (!worldIn.isRemote) {
            if (handIn == Hand.OFF_HAND)
            {
                return false;
            }
            TileEntity ownTile = worldIn.getTileEntity(pos);
            ItemStack heldItem = player.getHeldItem(handIn);
            IItemHandler podiumItemHandler = ((PodiumTile) ownTile).getItemHandler();
            ItemStack previousItem = podiumItemHandler.extractItem(0, 1, false);
            if (!previousItem.isEmpty()) {
                ItemEntity previousItemEntity = new ItemEntity(worldIn, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, previousItem);
                worldIn.addEntity(previousItemEntity);
            }
            ItemStack remainder = podiumItemHandler.insertItem(0, heldItem, false);
            player.inventory.mainInventory.set(player.inventory.currentItem, remainder);
            ownTile.markDirty();
        }
        return false;
    }

    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return SHAPE;
    }

    @Override
    public int getOpacity(BlockState state, IBlockReader worldIn, BlockPos pos)
    {
        return 0;
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
    public boolean hasTileEntity(BlockState state)
    {
        return true;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world)
    {
        return new PodiumTile();
    }
}
