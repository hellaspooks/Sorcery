package com.root.sorcery.arcana;

import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.common.util.INBTSerializable;

import java.util.Objects;

public class ArcanaStorage implements IArcanaStorage, INBTSerializable<CompoundNBT>
{
    private int capacity;
    private int arcana;

    public ArcanaStorage(int capacity)
    {
        this.capacity = capacity;
    }

    @Override
    public int receiveArcana(int maxReceive, boolean simulate)
    {
        int arcanaReceived = Math.min(capacity - arcana, maxReceive);

        if (!simulate)
            arcana += arcanaReceived;
        return arcanaReceived;
    }

    @Override
    public int extractArcana(int maxExtract, boolean simulate)
    {
        int arcanaExtracted = Math.min(arcana, maxExtract);

        if (!simulate)
            arcana -= arcanaExtracted;
        return arcanaExtracted;
    }

    @Override
    public int getArcanaStored()
    {
        return arcana;
    }

    @Override
    public int getMaxArcanaStored()
    {
        return capacity;
    }

    public void forceSetArcana(int arcana)
    {
        this.arcana = arcana;
    }

    public void setMaxArcanaStored(int max)
    {
        capacity = max;

        if (arcana > capacity)
            arcana = capacity;
    }

    @Override
    public CompoundNBT serializeNBT()
    {
        CompoundNBT tag = new CompoundNBT();

        tag.putInt("arcana", arcana);
        tag.putInt("capacity", capacity);
        return tag;
    }

    @Override
    public void deserializeNBT(CompoundNBT tag)
    {
        forceSetArcana(tag.getInt("arcana"));
        setMaxArcanaStored(tag.getInt("capacity"));
    }

    @Override
    public String toString()
    {
        return "ArcanaStorage{" +
                "capacity=" + capacity +
                ", arcana=" + arcana +
                '}';
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ArcanaStorage that = (ArcanaStorage) o;
        return capacity == that.capacity &&
                arcana == that.arcana;
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(capacity, arcana);
    }
}
