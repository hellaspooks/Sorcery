package com.root.sorcery.spell;

import com.root.sorcery.Config;
import com.root.sorcery.network.PacketHandler;
import com.root.sorcery.network.packets.ParticleEffectPacket;
import com.root.sorcery.utils.Utils;
import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.Entity;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.Vec3d;

import java.util.List;

public class CombustionSpell extends Spell
{
    private int dmgPerTick;

    public CombustionSpell()
    {
        super(Config.COMBUSTION_SPELL_COST.get());
        this.castDuration = 100;
        this.dmgPerTick = Config.COMBUSTION_SPELL_DAMAGE.get();
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
                entity.attackEntityFrom(DamageSource.ON_FIRE, this.dmgPerTick);
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
