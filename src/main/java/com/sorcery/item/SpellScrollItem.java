package com.sorcery.item;

import com.sorcery.Constants;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;

public class SpellScrollItem extends Item
{
    public ResourceLocation spellLoc;

    public SpellScrollItem(Properties properties, ResourceLocation spellLocIn)
    {
        super(properties);
        this.spellLoc = spellLocIn;

    }

    public ResourceLocation getSpell()
    {
        return this.spellLoc;
    }


}
