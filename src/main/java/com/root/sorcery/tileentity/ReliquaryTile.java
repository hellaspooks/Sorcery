package com.root.sorcery.tileentity;

import com.root.sorcery.arcana.IArcanaStorage;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;

public class ReliquaryTile extends TileEntity implements ITickableTileEntity
{
    @CapabilityInject(IArcanaStorage.class)
    static Capability<IArcanaStorage> ARCANA_STORAGE_CAP = null;

    private static int arcanaRegenPerTick = 1;
    private static int arcanaRegenTicks = 2;
    private static int arcanaPerWhack = 1000;

    public ReliquaryTile()
    {
        super(ModTile.RELIQUARY_TILE);
        this.getArcanaCap().setMaxArcanaStored(5000);
    }

    private IArcanaStorage getArcanaCap(){
        return this.getCapability(ARCANA_STORAGE_CAP, null).orElseThrow(NullPointerException::new);
    }


    @Override
    public void tick()
    {
        long worldTicks = world.getWorld().getGameTime();

        // every other tick
        if (worldTicks % arcanaRegenTicks == 0)
        {
            this.getArcanaCap().receiveArcana(arcanaRegenPerTick * arcanaRegenTicks, false);
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
