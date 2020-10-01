package com.sorcery.event;

import com.sorcery.Sorcery;
import com.sorcery.item.SpellcastingItem;
import com.sorcery.item.StaffItem;
import com.sorcery.spell.CastType;
import com.sorcery.spell.Spell;
import com.sorcery.spell.SpellUseContext;
import com.sorcery.utils.Utils;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;


@Mod.EventBusSubscriber
public class DurationSpellEvent
{
    /**
     * Called every tick a spell is being channeled.
     * @param event
     */
    @SubscribeEvent
    public static void channeledSpell(LivingEntityUseItemEvent.Tick event)
    {
        if (event.getItem().getItem() instanceof SpellcastingItem)
        {
            ItemStack spellProvider = null;
            LivingEntity entity = event.getEntityLiving();
            if (event.getItem().getItem() instanceof StaffItem)
            {
                spellProvider = Utils.getPlayerSpellbook((PlayerEntity)entity);
            } else {
                spellProvider = event.getItem();
            }
            Spell spell = Utils.getSpellFromProvider(spellProvider);
            if (spell == null)
            {
                Sorcery.getLogger().debug("Spell Event Failed");
                return;
            }
            SpellUseContext context = new SpellUseContext(entity.getEntityWorld(), entity, entity.getActiveHand());
            context.setCastingTicks(spell.castDuration - event.getDuration());
            spell.castPerTick(context);
        }
    }

    /**
     * Called when a player stops channeling a spell, before the full duration has completed.
     * @param event
     */
    @SubscribeEvent
    public static void channeledSpellStop(LivingEntityUseItemEvent.Stop event)
    {
        if (event.getItem().getItem() instanceof SpellcastingItem)
        {
            ItemStack spellProvider = null;
            LivingEntity entity = event.getEntityLiving();
            if (event.getItem().getItem() instanceof StaffItem)
            {
                spellProvider = Utils.getPlayerSpellbook((PlayerEntity)entity);
            } else {
                spellProvider = event.getItem();
            }
            Spell spell = Utils.getSpellFromProvider(spellProvider);
            if (spell == null)
            {
                Sorcery.getLogger().debug("Spell Event Failed");
                return;
            }
            if (spell.castType != CastType.DURATION)
            {
                SpellUseContext context = new SpellUseContext(entity.getEntityWorld(), entity, entity.getActiveHand());
                context.setCastingTicks(spell.castDuration - event.getDuration());
                spell.castFinal(context);
            }
        }
    }

    /**
     * Called when a player channels a spell for the full duration.
     * @param event
     */
    @SubscribeEvent
    public static void channeledSpellFinish(LivingEntityUseItemEvent.Finish event)
    {
        if (event.getItem().getItem() instanceof SpellcastingItem)
        {
            ItemStack spellProvider = null;
            LivingEntity entity = event.getEntityLiving();
            if (event.getItem().getItem() instanceof StaffItem)
            {
                spellProvider = Utils.getPlayerSpellbook((PlayerEntity)entity);
            } else {
                spellProvider = event.getItem();
            }
            Spell spell = Utils.getSpellFromProvider(spellProvider);
            if (spell == null)
            {
                Sorcery.getLogger().debug("Spell Event Failed");
                return;
            }
            SpellUseContext context = new SpellUseContext(entity.getEntityWorld(), entity, entity.getActiveHand());
            context.setCastingTicks(spell.castDuration - event.getDuration());
            spell.castFinal(context);
        }
    }
}
