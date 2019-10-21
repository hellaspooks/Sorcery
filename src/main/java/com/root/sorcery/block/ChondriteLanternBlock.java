package com.root.sorcery.block;

import com.root.sorcery.Constants;
import com.root.sorcery.block.state.CrystalColor;
import com.root.sorcery.block.state.States;
import com.root.sorcery.item.CrystalItem;
import com.root.sorcery.utils.Utils;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.AirItem;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.pathfinding.PathType;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;

import javax.annotation.Nullable;

public class ChondriteLanternBlock extends Block
{
    private static Float hardness = 1.5F;
    private static Float resistance = 6.0F;

    public static final EnumProperty<CrystalColor> COLOR = States.CRYSTAL_COLOR;
    public static final BooleanProperty HANGING = BlockStateProperties.HANGING;
    protected static final VoxelShape field_220279_b = VoxelShapes.or(Block.makeCuboidShape(5.0D, 0.0D, 5.0D, 11.0D, 7.0D, 11.0D), Block.makeCuboidShape(6.0D, 7.0D, 6.0D, 10.0D, 9.0D, 10.0D));
    protected static final VoxelShape field_220280_c = VoxelShapes.or(Block.makeCuboidShape(5.0D, 1.0D, 5.0D, 11.0D, 8.0D, 11.0D), Block.makeCuboidShape(6.0D, 8.0D, 6.0D, 10.0D, 10.0D, 10.0D));

    public ChondriteLanternBlock()
    {
        super(Properties.create(Material.ROCK).hardnessAndResistance(hardness, resistance).sound(SoundType.STONE).lightValue(15));
        this.setDefaultState(this.stateContainer.getBaseState().with(HANGING, Boolean.valueOf(false)).with(COLOR, CrystalColor.NONE));
    }

    // WIP
    public boolean onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit)
    {
        if (!worldIn.isRemote)
        {
            if (handIn == Hand.OFF_HAND)
            {
                return false;
            }

            // pop out old crystal
            String crystalName = state.get(COLOR).getCrystalName();
            Item crystalItem = GameRegistry.findRegistry(Item.class).getValue(new ResourceLocation(Constants.MODID, crystalName));
            ItemEntity crystalEntity = new ItemEntity(worldIn, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, new ItemStack(crystalItem));
            worldIn.addEntity(crystalEntity);


            Item playerItem = player.getHeldItem(handIn).getItem();
            CrystalColor color = CrystalColor.NONE;
            // if crystal in hand
            if (playerItem instanceof CrystalItem)
            {
                color = Utils.COLOR_CRYSTAL_MAP.get(playerItem.getRegistryName().getPath());
                worldIn.setBlockState(pos, state.with(COLOR, color), 11);
                player.getHeldItem(handIn).shrink(1);

            } else if (playerItem instanceof AirItem)
            {
                worldIn.setBlockState(pos, state.with(COLOR, color), 11);
            }
            return true;
        }
        return false;
    }

    @Nullable
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        for(Direction direction : context.getNearestLookingDirections()) {
            if (direction.getAxis() == Direction.Axis.Y) {
                BlockState blockstate = this.getDefaultState().with(HANGING, Boolean.valueOf(direction == Direction.UP));
                if (blockstate.isValidPosition(context.getWorld(), context.getPos())) {
                    return blockstate;
                }
            }
        }
        return null;
    }

    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return state.get(HANGING) ? field_220280_c : field_220279_b;
    }

    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(HANGING);
        builder.add(COLOR);
    }

    /**
     * Gets the render layer this block will render on. SOLID for solid blocks, CUTOUT or CUTOUT_MIPPED for on-off
     * transparency (glass, reeds), TRANSLUCENT for fully blended transparency (stained glass)
     */
    public BlockRenderLayer getRenderLayer() {
        return BlockRenderLayer.CUTOUT;
    }

    public boolean isValidPosition(BlockState state, IWorldReader worldIn, BlockPos pos) {
        Direction direction = func_220277_j(state).getOpposite();
        return Block.func_220055_a(worldIn, pos.offset(direction), direction.getOpposite());
    }

    protected static Direction func_220277_j(BlockState p_220277_0_) {
        return p_220277_0_.get(HANGING) ? Direction.DOWN : Direction.UP;
    }

    /**
     * Update the provided state given the provided neighbor facing and neighbor state, returning a new state.
     * For example, fences make their connections to the passed in state if possible, and wet concrete powder immediately
     * returns its solidified counterpart.
     * Note that this method should ideally consider only the specific face passed in.
     */
    public BlockState updatePostPlacement(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos) {
        return func_220277_j(stateIn).getOpposite() == facing && !stateIn.isValidPosition(worldIn, currentPos) ? Blocks.AIR.getDefaultState() : super.updatePostPlacement(stateIn, facing, facingState, worldIn, currentPos, facingPos);
    }

    public void updateColor(CrystalColor color)
    {

    }

    public boolean allowsMovement(BlockState state, IBlockReader worldIn, BlockPos pos, PathType type)
    {
        return false;
    }
}
