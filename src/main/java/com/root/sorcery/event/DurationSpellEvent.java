package com.root.sorcery.event;

import com.root.sorcery.item.SpellcastingItem;
import com.root.sorcery.spell.CastType;
import com.root.sorcery.spell.Spell;
import com.root.sorcery.spell.SpellUseContext;
import com.root.sorcery.spellcasting.ISpellcasting;
import com.root.sorcery.spellcasting.SpellcastingCapability;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class DurationSpellEvent
{

    @SubscribeEvent
    public static void durationSpell(LivingEntityUseItemEvent.Start event)
    {
        System.out.println("In duration spell event");
        if (event.getItem().getItem() instanceof SpellcastingItem)
        {
            ISpellcasting cap = event.getEntityLiving().getCapability(SpellcastingCapability.SPELLCASTING, null).orElseThrow(NullPointerException::new);
            Spell spell = GameRegistry.findRegistry(Spell.class).getValue(cap.getActiveSpell());

            if (spell.getCastType() == CastType.DURATION || spell.getCastType() == CastType.CHANNELED)
            {
                event.setDuration(spell.getCastDuration());
            }
            else
            {
                event.setDuration(1);
            }

        }
    }

    @SubscribeEvent
    public static void channeledSpell(LivingEntityUseItemEvent.Tick event)
    {
        if (event.getItem().getItem() instanceof SpellcastingItem)
        {
            ISpellcasting cap = event.getEntityLiving().getCapability(SpellcastingCapability.SPELLCASTING, null).orElseThrow(NullPointerException::new);
            Spell spell = GameRegistry.findRegistry(Spell.class).getValue(cap.getActiveSpell());

            if (spell.getCastType() == CastType.CHANNELED)
            {
                SpellUseContext context = new SpellUseContext(event.getEntityLiving().getEntityWorld(), event.getEntityLiving(), event.getEntityLiving().getActiveHand());
                spell.cast(context);
            }

        }
    }
}
