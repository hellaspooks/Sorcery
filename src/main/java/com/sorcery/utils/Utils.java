package com.sorcery.utils;

import com.google.common.base.Predicate;
import com.sorcery.arcana.IArcanaStorage;
import com.sorcery.block.state.CrystalColor;
import com.sorcery.item.SpellbookItem;
import com.sorcery.spell.ModSpell;
import com.sorcery.spell.Spell;
import com.sorcery.spellcasting.ISpellcasting;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityProvider;
import net.minecraftforge.fml.common.registry.GameRegistry;
import org.lwjgl.system.CallbackI;

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
        System.out.println("getting spell from provider");
        ResourceLocation spellLoc = getSpellCap(provider).getActiveSpell();
        System.out.println("active spell:" + spellLoc);
        Spell spellOut = GameRegistry.findRegistry(Spell.class).getValue(spellLoc);
        return spellOut;
    }

    public static Vector3d nBlocksAlongVector(Vector3d pos, Vector3d unitVector, float distance)
    {
       return pos.add(unitVector.getX() * distance, unitVector.getY() * distance, unitVector.getZ() * distance);
    }

    public static BlockRayTraceResult blockAlongRay(Vector3d rayStart, Vector3d rayUnitVec, double rayLength, World world, Entity entity)
    {
        Vector3d rayEnd = rayStart.add(rayUnitVec.mul(rayLength, rayLength, rayLength));
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

    public static List<Entity> entitiesInCone(World world, BlockPos pos, Entity excludeEnt, Vector3d startPos, Vector3d lookVec, int range, double angleRads)
    {
        List<Entity> entList = entitiesInRange(world, pos, range, excludeEnt);
        List<Entity> finalList = new ArrayList<>();
        for ( Entity entity : entList)
        {
            Vector3d pathVec = entity.getPositionVec().subtract(startPos);
            Double angleBetween = Math.acos(pathVec.dotProduct(lookVec) / (pathVec.length() * lookVec.length()));

            if (angleBetween <= angleRads)
            {
                finalList.add(entity);
            }
        }
        return finalList;
    }

    public static Vector3d getSunVector(World world)
    {
        double celestialRads = world.getCelestialAngleRadians(1.0F);
        return new Vector3d(-Math.sin(celestialRads), Math.cos(celestialRads), 0);
    }

    public static Vector3d getMoonVector(World world)
    {
        double celestialRads = world.getCelestialAngleRadians(1.0F) + Math.PI;
        return new Vector3d(-Math.sin(celestialRads), Math.cos(celestialRads), 0);
    }

    public static Vector3d getStaffVector(PlayerEntity player)
    {
        BasisVectors basis = new BasisVectors(player.getLook(1));

        Vector3d vec = basis.addXYZ(player.getEyePosition(1), 0.25, 0, 0.1);

        return vec;
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

    public static Vector3d getVectorFromPos(BlockPos pos){
        return new Vector3d(pos.getX(), pos.getY(), pos.getZ());
    }

    @Nullable
    public static ItemStack getPlayerSpellbook(PlayerEntity playerEntity)
    {
        ItemStack spellbook = null;

        for (ItemStack stack : playerEntity.inventory.mainInventory)
        {
            if (stack.getItem() instanceof SpellbookItem)
            {
                System.out.println("Found spellbook in getPlayerSpellbook");
                spellbook = stack;
            }
        }
        System.out.println("returning spellbook");
        return spellbook;
    }

}
