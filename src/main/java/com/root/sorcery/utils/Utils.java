package com.root.sorcery.utils;

import com.root.sorcery.spell.Spell;
import com.root.sorcery.spellcasting.ISpellcasting;
import com.root.sorcery.spellcasting.SpellcastingCapability;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.*;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;

import static net.minecraft.block.Block.doesSideFillSquare;
import static net.minecraft.block.Block.spawnAsEntity;

public class Utils {

    public static void dropItemInWorld(BlockPos source, ItemStack stack, World world) {
        if (stack == null || stack.isEmpty())
            return;

        spawnAsEntity(world, source, stack);
    }

    public static int getIndexOfMainHand(PlayerEntity player) {
        ItemStack mainHandItem = player.getHeldItemMainhand();
        int index = -1;

        for (int i = 0; i < 9; i++) {
            if (mainHandItem == player.inventory.getStackInSlot(i))
                index = i;
        }

        return index;
    }

    public static ISpellcasting getSpellCapFromEntity(LivingEntity entity)
    {
        return entity.getCapability(SpellcastingCapability.SPELLCASTING, null).orElseThrow(NullPointerException::new);
    }

    public static Spell getSpellFromEntity(LivingEntity entity)
    {
        ResourceLocation spellLoc = getSpellCapFromEntity(entity).getActiveSpell();
        return GameRegistry.findRegistry(Spell.class).getValue(spellLoc);
    }

    public static Vec3d nBlocksAlongVector(Vec3d pos, Vec3d unitVector, float distance)
    {
       return pos.add(unitVector.getX() * distance, unitVector.getY() * distance, unitVector.getZ() * distance);
    }

    public static BlockRayTraceResult blockAlongRay(Vec3d rayStart, Vec3d rayUnitVec, double rayLength, World world, Entity entity)
    {
        Vec3d rayEnd = rayStart.add(rayUnitVec.mul(rayLength, rayLength, rayLength));
        return world.rayTraceBlocks(new RayTraceContext(rayStart, rayEnd, RayTraceContext.BlockMode.COLLIDER, RayTraceContext.FluidMode.NONE, entity));
    }
}
