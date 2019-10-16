package com.root.sorcery.spell;

import com.root.sorcery.particle.ModParticle;
import com.root.sorcery.particle.ParticleEffects;
import com.root.sorcery.utils.Utils;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;

public class BlinkSpell extends Spell
{
    private double blinkDistance;

    public BlinkSpell(double blinkDistanceIn)
    {
        this.blinkDistance = blinkDistanceIn;
        this.arcanaCost = 42;
    }


    @Override
    public ActionResultType castServer(SpellUseContext context)
    {

        PlayerEntity player = context.getPlayer();

        BlockRayTraceResult blockRTR = Utils.blockAlongRay(player.getEyePosition(1.0f), player.getLookVec(), blinkDistance, context.getWorld(), player);


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
            Vec3d finalVec = Utils.nBlocksAlongVector(player.getEyePosition(1.0f), player.getLookVec(), (float) blinkDistance);
            player.teleportKeepLoaded(finalVec.getX(), finalVec.getY(), finalVec.getZ());
            return ActionResultType.SUCCESS;
        }
    }

    @Override
    public void castClient(SpellUseContext context)
    {
        Vec3d particleLocation = context.getPlayer().getPositionVec().add(0,1, 0);
        ParticleEffects.expandingSphere(context.getWorld(), ModParticle.SPARK_SLOW, particleLocation, 100, 0.5);
    }
}
