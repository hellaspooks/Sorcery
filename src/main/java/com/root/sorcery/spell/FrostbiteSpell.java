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
        List<Entity> entList = Utils.entitiesInCone(context.getWorld(), context.getPos(), context.getPlayer(), context.getPlayer().getEyePosition(1), context.getPlayer().getLook(1), Config.FROSTBITE_SPELL_RANGE.get(), (Config.FROSTBITE_SPELL_CONE_RADIUS.get() * .1));
        Effect slowEffect = Effects.SLOWNESS.getEffect();
        
        // Integer count = 0;
        for ( Entity entity : entList)
        {
            if (entity instanceof LivingEntity)
            {
                // Checks if there is already an existing slow effect and it is under MAX_RAMP value. If so, add RAMP value to its duration.
                if (((LivingEntity)entity).getActivePotionEffect(slowEffect) != null && ((LivingEntity)entity).getActivePotionEffect(slowEffect).getDuration() < Config.FROSTBITE_SPELL_MAX_RAMP.get())
                {
                    ((LivingEntity)entity).addPotionEffect(new EffectInstance(slowEffect, ((LivingEntity)entity).getActivePotionEffect(slowEffect).getDuration() + Config.FROSTBITE_SPELL_RAMP.get()));
                }
                // If there is not a current slow effect, adds one to the target.
                if (((LivingEntity)entity).getActivePotionEffect(slowEffect) == null)
                {
                    ((LivingEntity)entity).addPotionEffect(new EffectInstance(slowEffect, Config.FROSTBITE_SPELL_RAMP.get()));
                }
                // Checks if there is already an existing slow effect and it is over or equal MAX_RAMP value. If so, set effect duration to MAX_RAMP + DURATION and deal damage.
                if (((LivingEntity)entity).getActivePotionEffect(slowEffect) != null && ((LivingEntity)entity).getActivePotionEffect(slowEffect).getDuration() >= Config.FROSTBITE_SPELL_MAX_RAMP.get())
                {
                    ((LivingEntity)entity).addPotionEffect(new EffectInstance(slowEffect, Config.FROSTBITE_SPELL_MAX_RAMP.get() + Config.FROSTBITE_SPELL_DURATION.get()));
                    entity.attackEntityFrom(DamageSource.GENERIC, Config.FROSTBITE_SPELL_DAMAGE.get());
                }
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

        ParticleEffectPacket pkt1 = new ParticleEffectPacket(3, ParticleEffects.getSnowflake(0), loc, look, 4, 0.35, 0.1);
        ParticleEffectPacket pkt2 = new ParticleEffectPacket(3, ParticleEffects.getSnowflake(1), loc, look, 3, 0.35, 0.1);


        PacketHandler.sendToAllTracking(context.getPlayer(), pkt1);
        PacketHandler.sendToAllTracking(context.getPlayer(), pkt2);
    }
}