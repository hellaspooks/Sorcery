package com.root.sorcery.utils;

import com.root.sorcery.spell.Spell;
import com.root.sorcery.spellcasting.ISpellcasting;
import com.root.sorcery.spellcasting.SpellcastingCapability;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.*;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.CapabilityProvider;
import net.minecraftforge.fml.common.registry.GameRegistry;

import java.util.ArrayList;
import java.util.Vector;

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

    public static ISpellcasting getSpellCap(CapabilityProvider<?> capProvider)
    {
        return capProvider.getCapability(SpellcastingCapability.SPELLCASTING, null).orElseThrow(NullPointerException::new);
    }

    public static Spell getSpellFromEntity(LivingEntity entity)
    {
        ResourceLocation spellLoc = getSpellCap(entity).getActiveSpell();
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

    // Actually likely not needed, leaving here for now though
    public static ArrayList<Vec2f> randomPointsInCircle(World world, double radius, int numPoints)
    {
        ArrayList<Vec2f> points = new ArrayList<>();
        double[] rand1 = world.rand.doubles(numPoints).toArray();
        double[] rand2 = world.rand.doubles(numPoints).toArray();
        double[] rand3 = world.rand.doubles(numPoints).toArray();

        for (int i = 0; i < numPoints; i++)
        {
            double t = 2 * Math.PI * rand1[i];
            double u = rand2[i] + rand3[i];
            double r = ( u > 1) ? 2 - u : u;
            points.add(new Vec2f((float)(r * Math.cos(t)), (float)(r * Math.sin(t))));
        }
        return points;
    }

}
