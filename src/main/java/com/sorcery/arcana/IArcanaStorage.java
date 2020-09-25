package com.sorcery.arcana;

import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.common.util.INBTSerializable;

public interface IArcanaStorage extends INBTSerializable<CompoundNBT>
{
    int receiveArcana(int maxReceive, boolean simulate);

    int extractArcana(int maxExtract, boolean simulate);

    int getArcanaStored();

    int getMaxArcanaStored();

    void setMaxArcanaStored(int maxArcana);
}
