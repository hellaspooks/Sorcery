package com.sorcery.spell;

import com.sorcery.Config;
import com.sorcery.Constants;
import net.minecraft.item.Item;
import net.minecraft.potion.Effects;
import net.minecraftforge.event.RegistryEvent.Register;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModSpell
{

    public static final DeferredRegister<Spell> SPELLS = DeferredRegister.create(Spell.class, Constants.MODID);


    public static final RegistryObject<Spell> TEST_SPELL = SPELLS.register("test_spell", () -> new TestSpell("poof!", 0));

    public static final RegistryObject<Spell> REMOVE_ARCANA_SPELL = SPELLS.register("remove_arcana_spell", () -> new TestSpell("Arcana Removed!", 1000));

    public static final RegistryObject<Spell> SPEED_SPELL = SPELLS.register("speed_spell", () -> new PotionSpell(Effects.SPEED, Config.SPEED_SPELL_COST.get(), Config.SPEED_SPELL_DURATION.get()));

    public static final RegistryObject<Spell> BLINK_SPELL = SPELLS.register("blink_spell", () -> new BlinkSpell());

    public static final RegistryObject<Spell> DURATION_SPELL = SPELLS.register("duration_spell", () -> new DurationSpell());

    public static final RegistryObject<Spell> IGNITE_SPELL = SPELLS.register("ignite_spell", () -> new IgniteSpell());

    public static final RegistryObject<Spell> COMBUSTION_SPELL = SPELLS.register("combustion_spell", () -> new CombustionSpell());

    public static final RegistryObject<Spell> PLANT_DEATH_SPELL = SPELLS.register("plant_death_spell", () -> new PlantDeathSpell());

    public static final RegistryObject<Spell> REPEL_SPELL = SPELLS.register("repel_spell", () -> new RepelSpell());

    public static final RegistryObject<Spell> ARCANA_DRAIN_SPELL = SPELLS.register("arcana_drain_spell", () -> new ArcanaDrainSpell());

    public static final RegistryObject<Spell> FIREBOLT_SPELL = SPELLS.register("firebolt_spell", () -> new FireboltSpell());

    public static final RegistryObject<Spell> CREATE_WATER_SPELL = SPELLS.register("create_water_spell", () -> new CreateWaterSpell());


    public static void init()
    {
        System.out.println("Initing spells");
        SPELLS.register(FMLJavaModLoadingContext.get().getModEventBus());
    }

}
