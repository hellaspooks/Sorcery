package com.root.sorcery.potion;

import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.registries.ObjectHolder;

@ObjectHolder("sorcery")
public class ModEffect
{

    @ObjectHolder("dimensional_fraying")
    public static Effect DIMENSIONAL_FRAYING;

    @ObjectHolder("corrosion")
    public static Effect CORROSION;

    @ObjectHolder("necrosis")
    public static Effect NECROSIS;

    public static void init(RegistryEvent.Register<Effect> event)
    {
        registerEffect("dimensional_fraying", new EffectMod(EffectType.NEUTRAL, 13223375), event);

        registerEffect("corrosion", (new EffectMod(EffectType.HARMFUL, 1531410).addAttributesModifier(SharedMonsterAttributes.ARMOR, "d5dfa148-cd83-45db-adba-48b6a0925210", 0.5, AttributeModifier.Operation.MULTIPLY_TOTAL)), event);

        registerEffect("necrosis", (new EffectMod(EffectType.HARMFUL, 5378331).addAttributesModifier(SharedMonsterAttributes.MAX_HEALTH, "8589daf4-84a8-4189-beee-57ffa27838bb", -2, AttributeModifier.Operation.ADDITION)), event);

    }

    public static void registerEffect(String registryName, Effect effect, RegistryEvent.Register<Effect> event)
    {
        effect.setRegistryName(registryName);
        event.getRegistry().register(effect);
    }
}
