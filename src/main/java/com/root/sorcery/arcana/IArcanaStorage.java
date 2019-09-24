package com.root.sorcery.arcana;

public interface IArcanaStorage
{
    int receiveArcana(int maxReceive, boolean simulate);

    int extractArcana(int maxExtract, boolean simulate);

    int getArcanaStored();

    int getMaxArcanaStored();
}
