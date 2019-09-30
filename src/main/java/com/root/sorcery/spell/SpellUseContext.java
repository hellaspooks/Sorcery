package com.root.sorcery.spell;


import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.Vec3d;
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

    // Grody spellUseContext from ItemUseContext
    public SpellUseContext(ItemUseContext itemUseContext)
    {
        this(itemUseContext.getPlayer(), new BlockRayTraceResult(itemUseContext.getHitVec(), itemUseContext.getFace(), itemUseContext.getPos(), itemUseContext.func_221533_k()), itemUseContext.getHand());
    }

    public SpellUseContext(PlayerEntity player, BlockRayTraceResult rayTraceResultIn, Hand handIn)
    {
        this(player.world, rayTraceResultIn, player.getPosition(), player, handIn, player.getHeldItem(handIn));
    }

    public SpellUseContext(World worldIn, @Nullable BlockRayTraceResult rayTraceResultIn, BlockPos posIn, @Nullable PlayerEntity playerIn, @Nullable Hand handIn, @Nullable ItemStack heldItem)
    {
        this.world = worldIn;
        this.pos = posIn;
        this.rayTraceResult = rayTraceResultIn;
        this.player = playerIn;
        this.hand = handIn;
        this.item = heldItem;

    }

    public PlayerEntity getPlayer()
    {
        return this.player;
    }


    public ItemStack getItem()
    {
        return this.item;
    }

    public boolean isCasterSneaking()
    {
        return this.player != null && this.player.isSneaking();
    }

    public BlockPos getPos()
    {
        return this.pos;
    }

    public BlockPos getHitPos()
    {
        return this.rayTraceResult.getPos();
    }

    public Direction getHitFace()
    {
        return this.rayTraceResult.getFace();
    }

    public Vec3d getHitVec()
    {
        return this.rayTraceResult.getHitVec();
    }

    public World getWorld()
    {
        return this.world;
    }

}
