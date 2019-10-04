package com.root.sorcery.spell;


import net.minecraft.entity.LivingEntity;
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
    private final World world;
    private final BlockPos pos;
    private final BlockRayTraceResult rayTraceResult;
    private final PlayerEntity player;
    private final Hand hand;
    private final ItemStack item;
    private final LivingEntity targetEntity;



    public SpellUseContext(World worldIn, BlockPos posIn, @Nullable BlockRayTraceResult rayTraceResultIn, @Nullable PlayerEntity playerIn, @Nullable Hand handIn, @Nullable ItemStack heldItem, @Nullable LivingEntity targetEntityIn)
    {
        this.world = worldIn;
        this.pos = posIn;
        this.rayTraceResult = rayTraceResultIn;
        this.player = playerIn;
        this.hand = handIn;
        this.item = heldItem;
        this.targetEntity = targetEntityIn;

    }

    // Convenience constructor from ItemUseContext
    public SpellUseContext(ItemUseContext itemUseContext)
    {
        this(itemUseContext.getPlayer(), new BlockRayTraceResult(itemUseContext.getHitVec(), itemUseContext.getFace(), itemUseContext.getPos(), itemUseContext.func_221533_k()), itemUseContext.getHand());
    }

    // Convenience constructor for block hits
    public SpellUseContext(PlayerEntity player, BlockRayTraceResult rayTraceResultIn, Hand handIn)
    {
        this(player.world, player.getPosition(), rayTraceResultIn, player, handIn, player.getHeldItem(handIn), null);
    }

    // Convenience constructor for entity hits
    public SpellUseContext(World worldIn, BlockPos pos, PlayerEntity playerIn, Hand handIn, ItemStack itemStackIn, LivingEntity targetEntityIn)
    {
        this(worldIn, pos, null, playerIn, handIn, itemStackIn, targetEntityIn);
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

    public boolean wasEntityTargeted(){
        if (targetEntity == null)
            return false;
        return true;
    }

    public LivingEntity getTargetEntity()
    {
        return this.targetEntity;
    }

}
