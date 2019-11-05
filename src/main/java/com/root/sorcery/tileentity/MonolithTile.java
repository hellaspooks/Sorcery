package com.root.sorcery.tileentity;

import com.root.sorcery.arcana.IArcanaStorage;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;

public class MonolithTile extends TileEntity implements ITickableTileEntity
{
    @CapabilityInject(IArcanaStorage.class)
    static Capability<IArcanaStorage> ARCANA_STORAGE_CAP = null;

    private static int arcanaPerRegen = 2;
    private static int ticksPerRegen = 2;
    private static int arcanaPerWhack = 1000;

    public MonolithTile()
    {
        super(ModTile.MONOLITH_TILE);
        this.getArcanaCap().setMaxArcanaStored(1000);
    }

    private IArcanaStorage getArcanaCap(){
        return this.getCapability(ARCANA_STORAGE_CAP, null).orElseThrow(NullPointerException::new);
    }

    @Override
    public void tick()
    {
        long worldTicks = world.getWorld().getGameTime();

        // every other tick
        if (worldTicks % ticksPerRegen == 0)
        {
            this.getArcanaCap().receiveArcana(arcanaPerRegen, false);
        }
    }

    public int getArcanaForPlayer()
    {
        IArcanaStorage cap = this.getArcanaCap();

        if (arcanaPerWhack <= cap.getArcanaStored())
        {
            cap.extractArcana(arcanaPerWhack, false);
            return arcanaPerWhack;
        } else {
            int arcanaAmount = cap.extractArcana(cap.getArcanaStored(), false);
            return arcanaAmount;
        }
    }
}
