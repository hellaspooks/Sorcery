package com.sorcery.arcana;

import com.sorcery.Constants;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.IntNBT;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;

public class ArcanaCapability
{
    @CapabilityInject(IArcanaStorage.class)
    public static Capability<IArcanaStorage> ARCANA = null;

    public static ResourceLocation ARCANA_LOC = new ResourceLocation(Constants.MODID, "arcanacap");

    public static void register()
    {
        CapabilityManager.INSTANCE.register(IArcanaStorage.class, new IStorage<IArcanaStorage>()
                {
                    @Override
                    public INBT writeNBT(Capability<IArcanaStorage> capability, IArcanaStorage instance, Direction side)
                    {
                        return IntNBT.valueOf(instance.getArcanaStored());
                    }

                    @Override
                    public void readNBT(Capability<IArcanaStorage> capability, IArcanaStorage instance, Direction side, INBT nbt)
                    {
                        if (!(instance instanceof ArcanaStorage))
                            throw new IllegalArgumentException("Can not deserialize to an instance that isn't the default implementation");
                        ((ArcanaStorage) instance).forceSetArcana(((CompoundNBT) nbt).getInt("arcana"));
                    }
                },
                () -> new ArcanaStorage(1000));
    }
}
