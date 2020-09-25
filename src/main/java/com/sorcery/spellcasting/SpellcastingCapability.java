package com.sorcery.spellcasting;

import com.sorcery.Constants;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;

public class SpellcastingCapability
{

    @CapabilityInject(ISpellcasting.class)
    public static Capability<ISpellcasting> SPELLCASTING = null;

    public static ResourceLocation SPELLCASTING_LOC = new ResourceLocation(Constants.MODID, "spellcastingcap");

    public static SpellcastingStorage SPELLCASTING_STORAGE = new SpellcastingStorage();

    public static void register()
    {
        CapabilityManager.INSTANCE.register(ISpellcasting.class, SPELLCASTING_STORAGE, SpellcastingDefault::new);
    }
}
