package com.root.sorcery.spell;

import net.minecraft.potion.Effects;
import net.minecraftforge.event.RegistryEvent.Register;

public class ModSpell
{

    public static final Spell TEST_SPELL = new TestSpell("Test Spell 1");

    public static final Spell TEST_SPELL_2 = new TestSpell("Test Spell 2: Electric Boogaloo");

    public static final PotionSpell HASTE_SPELL = new PotionSpell(Effects.HASTE, 600);

    public static final BlinkSpell BLINK_SPELL = new BlinkSpell(30);

    public static final DurationSpell DURATION_SPELL = new DurationSpell();

    public static final IgniteSpell IGNITE_SPELL = new IgniteSpell();

    public static final CombustionSpell COMBUSTION_SPELL = new CombustionSpell();

    public static final PlantDeathSpell PLANT_DEATH_SPELL = new PlantDeathSpell(2);

    public static final RepelSpell REPEL_SPELL = new RepelSpell(8, 4);


    public static void init(Register<Spell> event)
    {
        registerSpell("sorcery:testspell", TEST_SPELL, event);
        registerSpell("sorcery:test_spell2", TEST_SPELL_2, event);
        registerSpell("sorcery:haste_spell", HASTE_SPELL, event);
        registerSpell("sorcery:blink_spell", BLINK_SPELL, event);
        registerSpell("sorcery:duration_spell", DURATION_SPELL, event);
        registerSpell("sorcery:ignite_spell", IGNITE_SPELL, event);
        registerSpell("sorcery:combustion_spell", COMBUSTION_SPELL, event);
        registerSpell("sorcery:plant_death_spell", PLANT_DEATH_SPELL, event);
        registerSpell("sorvery:repel_spell", REPEL_SPELL, event);
    }

    public static void registerSpell(String registryName, Spell spell, Register<Spell> event)
    {
       spell.setRegistryName(registryName);
       event.getRegistry().register(spell);
    }

}
