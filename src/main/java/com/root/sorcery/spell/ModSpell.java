package com.root.sorcery.spell;

import net.minecraftforge.event.RegistryEvent;

public class ModSpell {

    public static final Spell testSpell = new TestSpell().setRegistryName("testspell");

    public static void registerSpells(RegistryEvent.Register<Spell> event){

        event.getRegistry().registerAll(testSpell);
    }

}
