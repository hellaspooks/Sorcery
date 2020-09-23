package com.sorcery.potion;


import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectType;

// this needs to be here because base Effect constructor is Protected
public class EffectMod extends Effect
{
    public EffectMod(EffectType effectType, int color)
    {
        super(effectType, color);
    }

}
