package com.root.sorcery.spell;

import com.root.sorcery.network.PacketHandler;
import com.root.sorcery.network.packets.ParticleEffectPacket;
import com.root.sorcery.particle.Particles;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.math.Vec3d;

public class PotionSpell extends Spell
{
    // Potion effect to apply
    private Effect effect;
    // Duration in ticks, 1 second = 20 ticks
    private int duration;


    public PotionSpell(Effect effectIn, int costIn, int durationIn)
    {
        super(costIn);
        this.effect = effectIn;
        this.duration = durationIn;
    }

    @Override
    public ActionResultType castServer(SpellUseContext context)
    {

        if (context.wasEntityTargeted())
        {
            EffectInstance potionEffect = new EffectInstance(effect, duration);
            context.getTargetEntity().addPotionEffect(potionEffect);
            return ActionResultType.SUCCESS;
        }
        else
        {
            EffectInstance potionEffect = new EffectInstance(effect, duration);
            context.getPlayer().addPotionEffect(potionEffect);
            return ActionResultType.SUCCESS;
        }
    }

    @Override
    public void doParticleEffects(SpellUseContext context)
    {
        Vec3d loc = context.getPlayer().getPositionVec().add(0,1, 0);
        Vec3d look = context.getPlayer().getLook(1);

        ParticleEffectPacket pkt = new ParticleEffectPacket(2, Particles.getPuff(), loc, look, 100, 0.5, 0.2, 20);

        PacketHandler.sendToAllTracking(context.getPlayer(), pkt);
    }
}
