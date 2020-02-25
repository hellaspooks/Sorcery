package com.root.sorcery.event;

import com.root.sorcery.item.SpellcastingItem;
import com.root.sorcery.spell.CastType;
import com.root.sorcery.spell.Spell;
import com.root.sorcery.spell.SpellUseContext;
import com.root.sorcery.utils.Utils;
import net.minecraft.entity.LivingEntity;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;


public class DurationSpellEvent
{
    @SubscribeEvent
    public static void channeledSpell(LivingEntityUseItemEvent.Tick event)
    {
        if (event.getItem().getItem() instanceof SpellcastingItem)
        {
            LivingEntity entity = event.getEntityLiving();
            Spell spell = Utils.getSpellFromProvider(event.getItem());

            if (spell.getCastType() == CastType.CHANNELED) {
                SpellUseContext context = new SpellUseContext(entity.getEntityWorld(), entity, entity.getActiveHand());
                context.setCastingTicks(spell.castDuration - event.getDuration());
                spell.cast(context);
            }
        }
    }

    @SubscribeEvent
    public static void channeledSpellStop(LivingEntityUseItemEvent.Stop event)
    {
        if (event.getItem().getItem() instanceof SpellcastingItem)
        {
            LivingEntity entity = event.getEntityLiving();
            Spell spell = Utils.getSpellFromProvider(event.getItem());

            if (spell.getCastType() == CastType.CHANNELED) {
                SpellUseContext context = new SpellUseContext(entity.getEntityWorld(), entity, entity.getActiveHand());
                context.setCastingTicks(spell.castDuration - event.getDuration());
                spell.cast(context);
            }
        }
    }

    @SubscribeEvent
    public static void channeledSpellFinish(LivingEntityUseItemEvent.Finish event)
    {
        if (event.getItem().getItem() instanceof SpellcastingItem)
        {
            LivingEntity entity = event.getEntityLiving();
            Spell spell = Utils.getSpellFromProvider(event.getItem());

            if (spell.getCastType() == CastType.CHANNELED) {
                SpellUseContext context = new SpellUseContext(entity.getEntityWorld(), entity, entity.getActiveHand());
                context.setCastingTicks(spell.castDuration - event.getDuration());
                spell.cast(context);
            }
        }
    }
}
