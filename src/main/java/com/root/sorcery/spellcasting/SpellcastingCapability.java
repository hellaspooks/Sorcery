package com.root.sorcery.spellcasting;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.CapabilityManager;

public class SpellcastingCapability
{

    public static ResourceLocation SPELLCASTING_CAP = new ResourceLocation(com.root.sorcery.Constants.MODID, "spellcastingcap");

    public static void register()
    {
        CapabilityManager.INSTANCE.register(ISpellcasting.class, new SpellcastingStorage(), () -> new SpellcastingDefault());
    }
}
