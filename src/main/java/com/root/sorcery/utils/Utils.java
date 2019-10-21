package com.root.sorcery.utils;

import com.root.sorcery.block.state.CrystalColor;
import com.root.sorcery.spell.Spell;
import com.root.sorcery.spellcasting.ISpellcasting;
import com.root.sorcery.spellcasting.SpellcastingCapability;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.CapabilityProvider;
import net.minecraftforge.fml.common.registry.GameRegistry;


import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;

import static net.minecraft.block.Block.spawnAsEntity;

public class Utils {

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

}
