package com.root.sorcery.block;

import com.root.sorcery.block.state.States;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.LogBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class StrippedRunewoodLogBlock extends LogBlock
{
    private static Float hardness   = 3.0F;
    private static Float resistance = 6.0F;

    public static final IntegerProperty NORTH_RUNE = States.NORTH_RUNE;
    public static final IntegerProperty EAST_RUNE = States.EAST_RUNE;
    public static final IntegerProperty SOUTH_RUNE = States.SOUTH_RUNE;
    public static final IntegerProperty WEST_RUNE = States.WEST_RUNE;

    public StrippedRunewoodLogBlock()
    {
        super(MaterialColor.RED, Properties.create(Material.WOOD).hardnessAndResistance(hardness, resistance).sound(SoundType.WOOD));
        this.setDefaultState(super.stateContainer.getBaseState()
                .with(NORTH_RUNE, 0)
                .with(EAST_RUNE, 0)
                .with(SOUTH_RUNE, 0)
                .with(WEST_RUNE, 0)
        );
    }

    @Nullable
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        World world = context.getWorld();
        BlockState blockstate = super.getStateForPlacement(context)
                .with(NORTH_RUNE, world.rand.nextInt(4))
                .with(EAST_RUNE, world.rand.nextInt(4))
                .with(SOUTH_RUNE, world.rand.nextInt(4))
                .with(WEST_RUNE, world.rand.nextInt(4));
        return blockstate;
    }

    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(NORTH_RUNE);
        builder.add(EAST_RUNE);
        builder.add(SOUTH_RUNE);
        builder.add(WEST_RUNE);
        super.fillStateContainer(builder);
    }
}
