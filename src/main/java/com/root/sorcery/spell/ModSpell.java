package com.root.sorcery.spell;

import com.root.sorcery.Config;
import com.root.sorcery.Constants;
import net.minecraft.potion.Effects;
import net.minecraftforge.event.RegistryEvent.Register;

public class ModSpell
{

    public static final Spell TEST_SPELL = new TestSpell("Test Spell 1", 0);

    public static final Spell REMOVE_ARCANA_SPELL = new TestSpell("Arcana Removed!", 1000);

    public static final PotionSpell SPEED_SPELL = new PotionSpell(Effects.SPEED, Config.SPEED_SPELL_COST.get(), Config.SPEED_SPELL_DURATION.get());

    public static final BlinkSpell BLINK_SPELL = new BlinkSpell();

    public static final DurationSpell DURATION_SPELL = new DurationSpell();

    public static final IgniteSpell IGNITE_SPELL = new IgniteSpell();

    public static final CombustionSpell COMBUSTION_SPELL = new CombustionSpell();

    public static final PlantDeathSpell PLANT_DEATH_SPELL = new PlantDeathSpell();

    public static final RepelSpell REPEL_SPELL = new RepelSpell();

    public static final ArcanaDrainSpell ARCANA_DRAIN_SPELL = new ArcanaDrainSpell();

    public static final FireboltSpell FIREBOLT_SPELL = new FireboltSpell();


    public static void init(Register<Spell> event)
    {
        registerSpell("test_spell", TEST_SPELL, event);
        registerSpell("remove_arcana_spell", REMOVE_ARCANA_SPELL, event);
        registerSpell("speed_spell", SPEED_SPELL, event);
        registerSpell("blink_spell", BLINK_SPELL, event);
        registerSpell("duration_spell", DURATION_SPELL, event);
        registerSpell("ignite_spell", IGNITE_SPELL, event);
        registerSpell("combustion_spell", COMBUSTION_SPELL, event);
        registerSpell("plant_death_spell", PLANT_DEATH_SPELL, event);
        registerSpell("repel_spell", REPEL_SPELL, event);
        registerSpell("arcana_drain_spell", ARCANA_DRAIN_SPELL, event);
        registerSpell("firebolt_spell", FIREBOLT_SPELL, event);
    }

    public static void registerSpell(String registryName, Spell spell, Register<Spell> event)
    {
       spell.setRegistryName(Constants.MODID + ":" + registryName);
       event.getRegistry().register(spell);
    }

}
