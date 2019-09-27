package com.root.sorcery.spell;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class SpellUseContext
{
    protected final World world;
    protected final BlockPos pos;
    protected final BlockRayTraceResult rayTraceResult;
    protected final PlayerEntity player;
    protected final Hand hand;
    protected final ItemStack item;

    public SpellUseContext(PlayerEntity player, BlockRayTraceResult rayTraceResultIn, Hand handIn)
    {
        this(player.world, rayTraceResultIn, player.getPosition(), player, handIn, player.getHeldItem(handIn));
    }

    public SpellUseContext(World worldIn, BlockRayTraceResult rayTraceResultIn, BlockPos posIn, @Nullable PlayerEntity playerIn, @Nullable Hand handIn, @Nullable ItemStack heldItem)
    {
        this.world = worldIn;
        this.pos = posIn;
        this.rayTraceResult = rayTraceResultIn;
        this.player = playerIn;
        this.hand = handIn;
        this.item = heldItem;

    }

}
