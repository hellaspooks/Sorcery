package com.root.sorcery.utils;

import com.google.common.base.Predicate;
import com.root.sorcery.arcana.IArcanaStorage;
import com.root.sorcery.block.state.CrystalColor;
import com.root.sorcery.spell.Spell;
import com.root.sorcery.spellcasting.ISpellcasting;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.tileentity.TileEntity;
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

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Utils {

    @CapabilityInject(ISpellcasting.class)
    static Capability<ISpellcasting> SPELLCASTING = null;

    @CapabilityInject(IArcanaStorage.class)
    static Capability<IArcanaStorage> ARCANA_STORAGE = null;


    public static final EnumMap<CrystalColor, String> CRYSTAL_COLOR_MAP = getCrystalColorMap();

    public static final Map<String, CrystalColor> COLOR_CRYSTAL_MAP = getColorCrystalMap();

    public static ISpellcasting getSpellCap(CapabilityProvider<?> capProvider)
    {
        return capProvider.getCapability(SPELLCASTING, null).orElseThrow(NullPointerException::new);
    }

    public static IArcanaStorage getArcanaCap(CapabilityProvider<?> capabilityProvider)
    {
        return capabilityProvider.getCapability(ARCANA_STORAGE, null).orElseThrow(NullPointerException::new);
    }

    public static Spell getSpellFromProvider(CapabilityProvider<?> provider)
    {
        ResourceLocation spellLoc = getSpellCap(provider).getActiveSpell();
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

    public static Vec3d getSunVector(World world)
    {
        double celestialRads = world.getCelestialAngleRadians(1.0F);
        return new Vec3d(-Math.sin(celestialRads), Math.cos(celestialRads), 0);
    }

    public static Vec3d getMoonVector(World world)
    {
        double celestialRads = world.getCelestialAngleRadians(1.0F) + Math.PI;
        return new Vec3d(-Math.sin(celestialRads), Math.cos(celestialRads), 0);
    }

    public static Predicate<TileEntity> getTESearchPredicate(Class clazz, TileEntity tile, double range)
    {
        Predicate<TileEntity> pred = new Predicate<TileEntity>()
        {
            BlockPos pos = tile.getPos();

            @Override
            public boolean apply(@Nullable TileEntity input)
            {
                // Only add selected class
                if (!clazz.isInstance(input))
                {
                    return false;
                }
                // Don't add self
                if (input == tile)
                {
                    return false;
                }
                // Only add items with distance
                if (!pos.withinDistance(input.getPos(), range))
                {
                    return false;
                }
                return true;
            }
        };
        return pred;
    }

    public static Predicate<TileEntity> getDarkMonolithSearchPredicate(Class clazz, LivingEntity entity, double range)
    {
        Predicate<TileEntity> pred = new Predicate<TileEntity>()
        {
            BlockPos pos = entity.getPosition();

            @Override
            public boolean apply(@Nullable TileEntity input)
            {
                // Only add selected class
                if (!clazz.isInstance(input))
                {
                    return false;
                }
                // Only add items with distance
                if (!pos.withinDistance(input.getPos(), range))
                {
                    return false;
                }
                return true;
            }
        };
        return pred;
    }

    public static AxisAlignedBB getRangeAABB(BlockPos pos, int horiz, int posVert, int negVert)
    {
        BlockPos pos1 = pos.add(-horiz, -negVert, -horiz);
        BlockPos pos2 = pos.add(horiz, posVert, horiz);
        return new AxisAlignedBB(pos1, pos2);
    }


}
