package com.root.sorcery.utils;

import com.root.sorcery.arcana.ArcanaCapability;
import com.root.sorcery.arcana.IArcanaStorage;
import com.root.sorcery.block.state.CrystalColor;
import com.root.sorcery.spell.Spell;
import com.root.sorcery.spellcasting.ISpellcasting;
import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityProvider;
import net.minecraftforge.fml.common.registry.GameRegistry;


import java.util.ArrayList;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static net.minecraft.block.Block.spawnAsEntity;

public class Utils {

    @CapabilityInject(ISpellcasting.class)
    static Capability<ISpellcasting> SPELLCASTING = null;

    @CapabilityInject(IArcanaStorage.class)
    static Capability<IArcanaStorage> ARCANA_STORAGE = null;

    public static final EnumMap<CrystalColor, String> CRYSTAL_COLOR_MAP = getCrystalColorMap();

    public static final Map<String, CrystalColor> COLOR_CRYSTAL_MAP = getColorCrystalMap();

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
        return capProvider.getCapability(SPELLCASTING, null).orElseThrow(NullPointerException::new);
    }

    public static IArcanaStorage getArcanaCap(CapabilityProvider<?> capabilityProvider)
    {
        return capabilityProvider.getCapability(ARCANA_STORAGE, null).orElseThrow(NullPointerException::new);
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

    public static EnumMap getCrystalColorMap()
    {
        EnumMap<CrystalColor, String> map = new EnumMap<>(CrystalColor.class);
        for (CrystalColor crystalColor : CrystalColor.values())
        {
            map.put(crystalColor, crystalColor.getCrystalName());
        }
        return map;
    }

    public static Map getColorCrystalMap()
    {
        Map<String, CrystalColor> map = new HashMap<>();

        for (CrystalColor crystalColor : CrystalColor.values())
        {
            map.put(crystalColor.getCrystalName(), crystalColor);
        }
        return map;
    }

    public static List<Entity> entitiesInRange(World world, BlockPos pos, int range, Entity excludeEnt)
    {
        AxisAlignedBB aaBB = new AxisAlignedBB(pos.add(range, range, range), pos.add(-range, -range, -range));
        return world.getEntitiesWithinAABBExcludingEntity(excludeEnt, aaBB);
    }

    public static List<Entity> entitiesInCone(World world, BlockPos pos, Entity excludeEnt, Vec3d startPos, Vec3d lookVec, int range, double angleRads)
    {
        List<Entity> entList = entitiesInRange(world, pos, range, excludeEnt);
        List<Entity> finalList = new ArrayList<>();
        for ( Entity entity : entList)
        {
            Vec3d pathVec = entity.getPositionVec().subtract(startPos);
            Double angleBetween = Math.acos(pathVec.dotProduct(lookVec) / (pathVec.length() * lookVec.length()));

            if (angleBetween <= angleRads)
            {
                finalList.add(entity);
            }
        }
        return finalList;
    }

}
