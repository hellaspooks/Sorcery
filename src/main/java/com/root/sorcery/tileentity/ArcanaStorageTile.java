package com.root.sorcery.tileentity;

import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import com.root.sorcery.arcana.ArcanaStorage;
import com.root.sorcery.arcana.IArcanaStorage;
import com.root.sorcery.utils.Utils;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ArcanaStorageTile extends TileEntity implements ITickableTileEntity
{
    @CapabilityInject(IArcanaStorage.class)
    protected Capability<IArcanaStorage> ARCANA_CAP = null;


    protected Set<PhylacteryTile> phylacteriesInRange = Collections.EMPTY_SET;

    protected Predicate<TileEntity> tileEntityPredicate;

    protected ArcanaStorage arcanaStorage = new ArcanaStorage(10000);

    protected double range = 16;
    protected int transferRate = 100;

    protected boolean drainAll = false;

    private static int arcanaPerWhack = 1000;

    public ArcanaStorageTile(TileEntityType<?> tileEntityTypeIn)
    {
        super(tileEntityTypeIn);
        this.tileEntityPredicate = Utils.getTESearchPredicate(PhylacteryTile.class, this.getPos(), this.range);
    }

    @Override
    public void onLoad()
    {
        super.onLoad();
        this.tileEntityPredicate = Utils.getTESearchPredicate(PhylacteryTile.class, this.getPos(), this.range);
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side)
    {
        if (cap instanceof IArcanaStorage)
        {
            return LazyOptional.of(() -> arcanaStorage).cast();
        }
        return super.getCapability(cap, side);
    }

    @Override
    public void tick()
    {
        if (world.isRemote)
        {
            return;
        }

        long worldTicks = world.getWorld().getGameTime();

        // Every 2 seconds
        if (worldTicks % 40 == 0)
        {
            // Look for other Phylacteries to pass Arcana to
            this.phylacteriesInRange = this.getPhylacteriesInRange();
        }

        // Every half second
        if (worldTicks % 10 == 0)
        {
            // Pass Arcana to other Phylacteries
            for (PhylacteryTile otherPhyl : this.phylacteriesInRange)
            {
                if (otherPhyl.getStoredArcana() < this.getStoredArcana() || this.drainAll)
                {
                    int arcanaRecieved = otherPhyl.recieveArcana(this.transferRate);
                    this.extractArcana(arcanaRecieved);
                }
            }
        }
    }

    public int recieveArcana(int arcana)
    {
        return this.arcanaStorage.receiveArcana(arcana, false);
    }

    public int getStoredArcana()
    {
        return this.arcanaStorage.getArcanaStored();
    }

    public int extractArcana(int arcana)
    {
        return this.arcanaStorage.extractArcana(arcana, false);
    }

    public void setRange(double range)
    {
        this.range = range;
        this.tileEntityPredicate = Utils.getTESearchPredicate(PhylacteryTile.class, this.pos, this.range);
    }

    private Set<PhylacteryTile> getPhylacteriesInRange()
    {
        Set<PhylacteryTile> phylTEs = new HashSet<>();
        List<TileEntity> allTEs = world.loadedTileEntityList;

        for (TileEntity tileEntity : Collections2.filter(allTEs, this.tileEntityPredicate))
        {
            PhylacteryTile tile = (PhylacteryTile) tileEntity;
            phylTEs.add(tile);
        }
        return phylTEs;
    }

    public int getArcanaForPlayer()
    {
        if (arcanaPerWhack <= this.arcanaStorage.getArcanaStored())
        {
            this.arcanaStorage.extractArcana(arcanaPerWhack, false);
            return arcanaPerWhack;
        } else {
            int arcanaAmount = this.arcanaStorage.extractArcana(this.arcanaStorage.getArcanaStored(), false);
            return arcanaAmount;
        }
    }
}
