package com.sorcery.item;

import com.sorcery.spell.Spell;
import com.sorcery.spellcasting.ISpellcasting;
import com.sorcery.utils.Utils;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.common.registry.GameRegistry;

import java.util.Set;

public class SpellGrantingItem extends Item
{

    public SpellGrantingItem(Item.Properties properties)
    {
        super(properties);
    }


    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn)
    {
        ItemStack itemStack = playerIn.getHeldItem(handIn);
        ISpellcasting itemCap = Utils.getSpellCap(itemStack);

        // If creative, add all spells
        if ( playerIn.isCreative() )
        {
            addAllSpells(itemCap);
        }

        ItemStack spellbook  = Utils.getPlayerSpellbook(playerIn);

        if (spellbook.getItem() instanceof SpellbookItem)
        {
            ISpellcasting spellbookCap = Utils.getSpellCap(spellbook);
            // For development, this should be handled by some other method later
            for ( ResourceLocation spell : itemCap.getPreparedSpells() )
            {
                spellbookCap.addPreparedSpell(spell);

            }

        }

        return new ActionResult<>(ActionResultType.SUCCESS, itemStack);
    }

    // In the future spells should be added to itemstack's capability as part of the crafting process, not here
    public void addAllSpells(ISpellcasting itemCap)
    {
        Set<ResourceLocation> allSpells = GameRegistry.findRegistry(Spell.class).getKeys();

        for (ResourceLocation spell : allSpells)
        {
            itemCap.addPreparedSpell(spell);
        }
    }

    // This just makes it always have the enchantment glint
    @Override
    @OnlyIn(Dist.CLIENT)
    public boolean hasEffect(ItemStack stack)
    {
        return true;
    }


}
