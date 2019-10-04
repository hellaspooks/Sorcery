package com.root.sorcery.spell;

import net.minecraft.potion.Effects;
import net.minecraftforge.event.RegistryEvent.Register;

public class ModSpell
{

    public static Spell TEST_SPELL = new TestSpell("Test Spell 1");

    public static Spell TEST_SPELL_2 = new TestSpell("Test Spell 2: Electric Boogaloo");

    public static Spell HASTE_SPELL = new PotionSpell(Effects.HASTE, 600);

    public static Spell BLINK_SPELL =   new BlinkSpell(30);

    public static Spell TEST_CHANNELED_SPELL = new ChanneledSpell();


    public static void registerSpells(Register<Spell> event)
    {
        TEST_SPELL.setRegistryName("sorcery:testspell");
        TEST_SPELL_2.setRegistryName("sorcery:test_spell2");
        HASTE_SPELL.setRegistryName("sorcery:haste_spell");
        BLINK_SPELL.setRegistryName("sorcery:blink_spell");
        TEST_CHANNELED_SPELL.setRegistryName("sorcery:channeled_spell");
        event.getRegistry().register(HASTE_SPELL);
        event.getRegistry().register(TEST_SPELL);
        event.getRegistry().register(TEST_SPELL_2);
        event.getRegistry().register(BLINK_SPELL);
        event.getRegistry().register(TEST_CHANNELED_SPELL);
    }

}
