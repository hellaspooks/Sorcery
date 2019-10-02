package com.root.sorcery.spell;

import net.minecraftforge.event.RegistryEvent.Register;

public class ModSpell
{

    public static Spell TEST_SPELL = new TestSpell("Test Spell 1");

    public static Spell TEST_SPELL_2 = new TestSpell("Test Spell 2: Electric Boogaloo");


    public static void registerSpells(Register<Spell> event)
    {
        TEST_SPELL.setRegistryName("sorcery:testspell");
        TEST_SPELL_2.setRegistryName("sorcery:test_spell2");
        event.getRegistry().register(TEST_SPELL);
        event.getRegistry().register(TEST_SPELL_2);
    }

}
