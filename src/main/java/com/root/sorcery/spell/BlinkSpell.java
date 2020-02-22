package com.root.sorcery.spell;

import com.root.sorcery.Config;
import com.root.sorcery.network.PacketHandler;
import com.root.sorcery.network.packets.ParticleEffectPacket;
import com.root.sorcery.particle.Particles;
import com.root.sorcery.potion.ModEffect;
import com.root.sorcery.utils.Utils;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;

public class BlinkSpell extends Spell
{
    private double blinkDistance;

    public BlinkSpell()
    {
        super(Config.BLINK_SPELL_COST.get());
        this.blinkDistance = Config.BLINK_SPELL_DISTANCE.get();
    }


    @Override
    public ActionResultType castServer(SpellUseContext context)
    {

        PlayerEntity player = context.getPlayer();

        // Check for dimensional fraying/ apply fraying
        EffectInstance frayingEffect = player.getActivePotionEffect(ModEffect.DIMENSIONAL_FRAYING);
        int duration = 40;
        // Existing fraying, damage and square time
        if (frayingEffect != null)
        {
            // Damage equal to time remaining, capped at 5 minutes
            int timeRemainingSecs = (int) Math.ceil((double)frayingEffect.getDuration() / 20);
            player.attackEntityFrom(DamageSource.WITHER, timeRemainingSecs);
            // Re-apply effect with time squared
            duration = Math.min( timeRemainingSecs * 2 * 20, 300 * 20);
        }

        EffectInstance newEffect = new EffectInstance(ModEffect.DIMENSIONAL_FRAYING, duration);
        player.addPotionEffect(newEffect);

        BlockRayTraceResult blockRTR = Utils.blockAlongRay(player.getEyePosition(1.0f), player.getLookVec(), blinkDistance, context.getWorld(), player);

        // if raytrace hits a block, teleport to that block, ,if it doesn't, teleport the full distance
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
    public void doParticleEffects(SpellUseContext context)
    {
        Vec3d loc = context.getPlayer().getPositionVec().add(0,1, 0);
        Vec3d look = context.getPlayer().getLookVec();

        ParticleEffectPacket pkt = new ParticleEffectPacket(2, Particles.getSpark(), loc, look, 100, 0.5, 0.2, 20);
        PacketHandler.sendToAllTrackingPlayer(context.getPlayer(), pkt);
    }
}
