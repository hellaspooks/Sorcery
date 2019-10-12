package com.root.sorcery.spell;

import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.ActionResultType;

public class PotionSpell extends Spell
{
    // Potion effect to apply
    private Effect effect;
    // Duration in ticks, 1 second = 20 ticks
    private int duration;


    public PotionSpell(Effect effectIn, int durationIn)
    {
        this.effect = effectIn;
        this.duration = durationIn;
        this.arcanaCost = 50;
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
}
