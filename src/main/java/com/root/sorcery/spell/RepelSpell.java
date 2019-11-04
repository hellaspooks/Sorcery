package com.root.sorcery.spell;

import com.root.sorcery.network.PacketHandler;
import com.root.sorcery.network.packets.ParticleEffectPacket;
import com.root.sorcery.particle.ModParticle;
import com.root.sorcery.utils.Utils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.math.Vec3d;

import java.util.List;

public class RepelSpell extends Spell
{
    private int range;
    private double velocity;

    public RepelSpell(int range, int velocity)
    {
        super(10);
        this.range = range;
        this.velocity = velocity;
    }

    // Server-side only stuff happens here
    public ActionResultType castServer(SpellUseContext context)
    {
        LivingEntity repelFrom = context.getPlayer();
        List<Entity> entities = Utils.entitiesInRange(context.getWorld(), context.getPos(), range, repelFrom);

        for (Entity entity : entities)
        {
            if (entity instanceof LivingEntity)
            {
                // Send them flying away, also slightly up
                Vec3d repelVec = entity.getPositionVec().subtract(repelFrom.getPositionVec()).normalize().add(0, 0.2, 0).normalize();
                entity.addVelocity(repelVec.x * velocity, repelVec.y * velocity, repelVec.z * velocity);

                // Poof effects
                ParticleEffectPacket pkt = new ParticleEffectPacket(0, ModParticle.SIMPLE_PUFF, entity.getPositionVec(), entity.getLookVec(), 5, 0, 0.2);
                PacketHandler.sendToAllTracking(repelFrom, pkt);
            }
        }

        return ActionResultType.SUCCESS;
    }
}
