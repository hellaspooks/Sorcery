package com.root.sorcery.arcana;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class ArcanaProvider implements ICapabilitySerializable<CompoundNBT>
{
    private final IArcanaStorage impl = new ArcanaStorage(10000);

    private final LazyOptional<IArcanaStorage> cap = LazyOptional.of(() -> impl);

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> capability, @Nullable Direction side)
    {
        if (capability == ArcanaCapability.ARCANA)
        {
            return cap.cast();
        }
        return LazyOptional.empty();
    }

    @Override
    public CompoundNBT serializeNBT()
    {
        return impl.serializeNBT();
    }

    @Override
    public void deserializeNBT(CompoundNBT nbt)
    {
        impl.deserializeNBT(nbt);
    }
}
