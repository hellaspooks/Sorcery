package com.root.sorcery.event;

import com.root.sorcery.item.SpellcastingItem;
import com.root.sorcery.spell.Spell;
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

            if (spell.getCastDuration() == 0)
            {
                event.setDuration(1);
            }
            else
            {
                event.setDuration(spell.getCastDuration());
            }

        }
    }
}
