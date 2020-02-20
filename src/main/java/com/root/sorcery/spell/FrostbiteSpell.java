package com.root.sorcery.spell;

import com.root.sorcery.Config;
import com.root.sorcery.network.PacketHandler;
import com.root.sorcery.network.packets.ParticleEffectPacket;
import com.root.sorcery.particle.ParticleEffects;
import com.root.sorcery.utils.Utils;
import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.potion.Effects;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;

import java.util.List;

public class FrostbiteSpell extends Spell {

    public FrostbiteSpell() {
        super(Config.FROSTBITE_SPELL_COST.get());
        this.castDuration = 100;
        this.castType = CastType.CHANNELED;

    }

    @Override
    public ActionResultType castServer(SpellUseContext context) {

        // ripped from CombustionSpell
        List<Entity> entList = Utils.entitiesInCone(context.getWorld(), context.getPos(), context.getPlayer(), context.getPlayer().getEyePosition(1), context.getPlayer().getLook(1), Config.FROSTBITE_SPELL_RANGE.get(), .5);
        Effect slowEffect = Effects.SLOWNESS.getEffect();
        
        Integer count = 0;
        for ( Entity entity : entList)
        {
            if (entity instanceof LivingEntity)
            {
                ((LivingEntity)entity).addPotionEffect(new EffectInstance(slowEffect, Config.FROSTBITE_SPELL_DURATION.get()));
                // generic damage doesn't draw mob aggro, what other damage type to use?
                entity.attackEntityFrom(DamageSource.GENERIC, Config.FROSTBITE_SPELL_DAMAGE.get());
                // debug message
                // context.getPlayer().sendMessage(new StringTextComponent(((LivingEntity)entity).getActivePotionEffects().toString()));
                count++;
            }
        }
        // debug message
        // context.getPlayer().sendMessage(new StringTextComponent("Affected " + count.toString() + " Targets."));
        return ActionResultType.SUCCESS;
    }

    // also ripped from CombustionSpell
    @Override
    public void doParticleEffects(SpellUseContext context)
    {
        Vec3d loc = Utils.nBlocksAlongVector(context.getPlayer().getEyePosition(0), context.getPlayer().getLook(0), 1f).add(0, -.1, 0);
        Vec3d look = context.getPlayer().getLookVec();

        ParticleEffectPacket pkt1 = new ParticleEffectPacket(3, ParticleEffects.getSnowflake(), loc, look, 40, 0.5, 0.2);

        PacketHandler.sendToAllTracking(context.getPlayer(), pkt1);
    }
}