package com.root.sorcery.spell;

import com.root.sorcery.network.PacketHandler;
import com.root.sorcery.network.packets.ParticleEffectPacket;
import com.root.sorcery.particle.ModParticle;
import com.root.sorcery.particle.ParticleEffects;
import com.root.sorcery.utils.Utils;
import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.lwjgl.system.CallbackI;

import java.util.List;

public class CombustionSpell extends Spell
{

    public CombustionSpell()
    {
        super(1);
        this.castDuration = 100;
        this.castType = CastType.CHANNELED;
        this.sound = SoundEvents.ITEM_FIRECHARGE_USE;
    }

    @Override
    public ActionResultType castServer(SpellUseContext context)
    {
        List<Entity> entList = Utils.entitiesInCone(context.getWorld(), context.getPos(), context.getPlayer(), context.getPlayer().getEyePosition(1), context.getPlayer().getLook(1), 8, 0.2);

        for ( Entity entity : entList)
        {
            if (entity instanceof CreatureEntity)
            {
                entity.setFire(3);
                entity.attackEntityFrom(DamageSource.ON_FIRE, 1.0F);
            }
        }

        return ActionResultType.SUCCESS;
    }

    @Override
    public void doParticleEffects(SpellUseContext context)
    {
        Vec3d loc = Utils.nBlocksAlongVector(context.getPlayer().getEyePosition(0), context.getPlayer().getLook(0), 1f).add(0, -.1, 0);
        Vec3d look = context.getPlayer().getLookVec();

        ParticleEffectPacket pkt1 = new ParticleEffectPacket(3, ParticleTypes.FLAME, loc, look, 40, 0.5, 0.2);
        ParticleEffectPacket pkt2 = new ParticleEffectPacket(3, ParticleTypes.SMOKE, loc, look, 10, 0.3, 0.2);

        PacketHandler.sendToAllTracking(context.getPlayer(), pkt1);
        PacketHandler.sendToAllTracking(context.getPlayer(), pkt2);
    }
}
