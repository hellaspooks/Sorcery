package com.root.sorcery.blocks;

import com.root.sorcery.tile_entities.ChondriteBlastFurnaceEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class ChondriteBlastFurnace extends Block{



    public ChondriteBlastFurnace(){
        super(Properties.create(Material.IRON).sound(SoundType.METAL).hardnessAndResistance(4.0F));
        this.setRegistryName("chondrite_blast_furnace");
        Registry.register(Registry.BLOCK, "chondrite_blast_furnace", this);

    }


    @Override
    public boolean hasTileEntity(BlockState state){
        return true;
    }

    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world){
        return new ChondriteBlastFurnaceEntity();
    }




    @Override
        public void onBlockPlacedBy(World world, BlockPos pos, BlockState state, @Nullable LivingEntity entity, ItemStack stack){
            if (entity != null){
                world.setBlockState(pos, state.with(BlockStateProperties.FACING, getFacingFromEntity(pos, entity)), 2);
                world.setBlockState(pos, state.with(BlockStateProperties.LIT, false), 2);
            }
        }

    public static Direction getFacingFromEntity(BlockPos clickedBlock, LivingEntity entity) {

    return Direction.getFacingFromVector((float) (entity.posX - clickedBlock.getX()), (float) (entity.posY - clickedBlock.getY()), (float)(entity.posZ - clickedBlock.getZ()));
    }

    @Override
        protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder){
            builder.add(BlockStateProperties.FACING);
        }

}
