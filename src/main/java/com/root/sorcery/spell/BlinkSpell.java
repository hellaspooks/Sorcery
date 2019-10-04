package com.root.sorcery.spell;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;

public class BlinkSpell extends Spell
{
    private int arcanaCost = 42;
    private double blinkDistance;

    public BlinkSpell(double blinkDistanceIn)
    {
        this.blinkDistance = blinkDistanceIn;
    }


    @Override
    public ActionResultType cast(SpellUseContext context)
    {
        if (!drainArcana(context, arcanaCost))
            return ActionResultType.FAIL;

        PlayerEntity player = context.getPlayer();

        Vec3d lookVec = player.getLookVec();

        Vec3d playerLookPos = player.getEyePosition(1.0F);
        Vec3d playerLookEnd = playerLookPos.add(lookVec.mul(blinkDistance, blinkDistance, blinkDistance));


        BlockRayTraceResult blockRTR = context.getWorld().rayTraceBlocks(new RayTraceContext(playerLookPos, playerLookEnd, RayTraceContext.BlockMode.COLLIDER, RayTraceContext.FluidMode.NONE, player));


        // if raytrace hits a block, teleport to that block, ,if it doesnt, teleport the full distance
        if (blockRTR.getType() == RayTraceResult.Type.BLOCK)
        {

            Vec3d baseVec = blockRTR.getHitVec();
            Direction hitDir = blockRTR.getFace();

            double yOffset = 0.01;
            // If looking up at block
            if (hitDir.getIndex() == 0)
                yOffset = -2.0;

            Vec3d finalVec =  baseVec.add(hitDir.getXOffset(), yOffset, hitDir.getZOffset());


            player.teleportKeepLoaded(finalVec.getX(), finalVec.getY(), finalVec.getZ());

            return ActionResultType.SUCCESS;
        }
        else
        {
            player.teleportKeepLoaded(playerLookEnd.getX(), playerLookEnd.getY(), playerLookEnd.getZ());
            return ActionResultType.SUCCESS;
        }
    }
}
