package com.sorcery.spell;

import com.sorcery.Config;
import com.sorcery.network.PacketHandler;
import com.sorcery.network.packets.ParticleEffectPacket;
import com.sorcery.utils.Utils;
import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.Entity;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.vector.Vector3d;

import java.util.List;

public class CombustionSpell extends Spell
{
    private int dmgPerTick;
    private int fireDuration;

    public CombustionSpell()
    {
        super(Config.COMBUSTION_SPELL_COST.get());
        this.castDuration = Config.COMBUSTION_SPELL_CAST_DURATION.get();
        this.dmgPerTick = Config.COMBUSTION_SPELL_DAMAGE.get();
        this.fireDuration = Config.COMBUSTION_SPELL_FIRE_DURATION.get();
        this.castType = CastType.CHANNELED;
        this.sound = SoundEvents.ITEM_FIRECHARGE_USE;
        this.castFrequency = 2;
    }

    @Override
    public ActionResultType doCastPerTick(SpellUseContext context)
    {
        this.doParticleEffects(context);
        this.playSound(context);
        List<Entity> entList = Utils.entitiesInCone(context.getWorld(), context.getPos(), context.getPlayer(), context.getPlayer().getEyePosition(1), context.getPlayer().getLook(1), 8, 0.2);

        Double castPercent = this.getCastPercent(context);


        for ( Entity entity : entList)
        {
            if (entity instanceof CreatureEntity)
            {
                entity.setFire((int)((double)this.fireDuration / castPercent));
                entity.attackEntityFrom(DamageSource.ON_FIRE, (int)((double) this.dmgPerTick/ castPercent));
            }
        }
        return ActionResultType.SUCCESS;
    }

    @Override
    public void doParticleEffects(SpellUseContext context)
    {
        Vector3d loc = Utils.nBlocksAlongVector(context.getPlayer().getEyePosition(0), context.getPlayer().getLook(0), 1f).add(0, -.1, 0);
        Vector3d look = context.getPlayer().getLookVec();

        ParticleEffectPacket pkt1 = new ParticleEffectPacket(3, ParticleTypes.FLAME, loc, look, 40, 0.5, 0.2, 20);
        ParticleEffectPacket pkt2 = new ParticleEffectPacket(3, ParticleTypes.SMOKE, loc, look, 10, 0.3, 0.2, 20);

        PacketHandler.sendToAllTrackingPlayer(context.getPlayer(), pkt1);
        PacketHandler.sendToAllTrackingPlayer(context.getPlayer(), pkt2);
    }
}
