package com.sorcery.item;

import com.sorcery.Constants;
import com.sorcery.spell.Spell;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.RegistryObject;

public class SpellScrollItem extends Item
{
    public ResourceLocation spellLoc;

    public SpellScrollItem(Properties properties, RegistryObject<Spell> spellLocIn)
    {
        super(properties);
        this.spellLoc = spellLocIn.getId();

    }

    public ResourceLocation getSpell()
    {
        return this.spellLoc;
    }


}
