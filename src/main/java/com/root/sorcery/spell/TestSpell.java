package com.root.sorcery.spell;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.text.StringTextComponent;

public class TestSpell extends Spell
{

    public TestSpell()
    {

    }

    public static void cast(PlayerEntity playerEntity)
    {
        playerEntity.sendMessage(new StringTextComponent("Test Spell Cast!"));
    }
}
