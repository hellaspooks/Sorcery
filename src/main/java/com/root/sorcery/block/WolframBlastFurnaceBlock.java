package com.root.sorcery.block;

import java.util.Random;

import com.root.sorcery.tileentity.WolframBlastFurnaceTile;
import net.minecraft.block.AbstractFurnaceBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class WolframBlastFurnaceBlock extends AbstractFurnaceBlock {

    private static Float hardness = 2.0F;
    private static Float resistance = 2.0F;

    public WolframBlastFurnaceBlock() {
        super(Properties.create(Material.IRON).hardnessAndResistance(hardness,resistance).sound(SoundType.METAL));
        this.setDefaultState(this.stateContainer.getBaseState().with(FACING, Direction.NORTH).with(LIT, Boolean.valueOf(false)));
    }

    @Override
    public TileEntity createNewTileEntity(IBlockReader world)
    {
        return new WolframBlastFurnaceTile();
    }

    @Override
    protected void interactWith(World world, BlockPos pos, PlayerEntity player)
    {
        NetworkHooks.openGui((ServerPlayerEntity) player, (INamedContainerProvider) world.getTileEntity(pos), pos);
    }

    @Override
    public int getLightValue(BlockState state) {
        return !state.get(LIT) ? state.getLightValue() : 15;
    }

    @Override
    public void onReplaced(BlockState state, World worldIn, BlockPos pos, BlockState newState, boolean isMoving) {

        LOGGER.debug("Are we here?");
        
        if (state.getBlock() != newState.getBlock()) {
           TileEntity tileentity = worldIn.getTileEntity(pos);
           if (tileentity instanceof WolframBlastFurnaceTile) {
              tileentity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).ifPresent(x -> {
                  for (int i = 0; i < x.getSlots(); i++) {
                      spawnAsEntity(worldIn, pos, x.getStackInSlot(i));
                  }
              });
              worldIn.updateComparatorOutputLevel(pos, this);
           }
  
           super.onReplaced(state, worldIn, pos, newState, isMoving);
        }
     }

    @OnlyIn(Dist.CLIENT)
    public void animateTick(BlockState stateIn, World worldIn, BlockPos pos, Random rand) {
        if (stateIn.get(LIT)) {
           double d0 = (double)pos.getX() + 0.5D;
           double d1 = (double)pos.getY();
           double d2 = (double)pos.getZ() + 0.5D;
           if (rand.nextDouble() < 0.1D) {
              worldIn.playSound(d0, d1, d2, SoundEvents.BLOCK_FURNACE_FIRE_CRACKLE, SoundCategory.BLOCKS, 1.0F, 1.0F, false);
           }
  
           Direction direction = stateIn.get(FACING);
           Direction.Axis direction$axis = direction.getAxis();
           double d3 = 0.52D;
           double d4 = rand.nextDouble() * 0.6D - 0.3D;
           double d5 = direction$axis == Direction.Axis.X ? (double)direction.getXOffset() * 0.52D : d4;
           double d6 = rand.nextDouble() * 6.0D / 16.0D;
           double d7 = direction$axis == Direction.Axis.Z ? (double)direction.getZOffset() * 0.52D : d4;
           worldIn.addParticle(ParticleTypes.SMOKE, d0 + d5, d1 + d6, d2 + d7, 0.0D, 0.0D, 0.0D);
           worldIn.addParticle(ParticleTypes.FLAME, d0 + d5, d1 + d6, d2 + d7, 0.0D, 0.0D, 0.0D);
        }
     }
}
