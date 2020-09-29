package com.sorcery.item;

import com.sorcery.Constants;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;

public class SpellScrollItem extends Item
{
    public ResourceLocation spellLoc;

    public SpellScrollItem(Properties properties, ResourceLocation spellLocIn)
    {
        super(Constants.ITEM_PROPS_NONSTACK);
        this.spellLoc = spellLocIn;

    }

    public ResourceLocation getSpell()
    {
        return this.spellLoc;
    }


}
