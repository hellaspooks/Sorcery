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
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ArcanaStorageTile extends TileEntity implements ITickableTileEntity
{

    protected Set<ArcanaStorageTile> arcanaStorageInTransferRange = Collections.EMPTY_SET;

    protected Set<ArcanaStorageTile> arcanaStorageInSearchRange = Collections.EMPTY_SET;


    protected Predicate<TileEntity> searchPredicate;

    protected ArcanaStorage arcanaStorage = new ArcanaStorage(10000);

    protected double searchRange = 32;
    protected double transferRange = 16;

    protected int transferRate = 100;

    protected boolean drainAll = false;

    private static int arcanaPerWhack = 1000;

    // new stuff

    protected ArcanaStorageTile arcanaTransferTarget = null;

    boolean manualLinkOnly = true;

    public ArcanaStorageTile(TileEntityType<?> tileEntityTypeIn)
    {
        super(tileEntityTypeIn);
        this.searchPredicate = Utils.getTESearchPredicate(ArcanaStorageTile.class, this, this.searchRange);
    }

    @Override
    public void onLoad()
    {
        super.onLoad();
        // get search predicate
        this.searchPredicate = Utils.getTESearchPredicate(ArcanaStorageTile.class, this, this.searchRange);
        // get phylacteries in transfer range
        this.updateArcanaStorageTiles();
        // Add self to other arcana storage
        for (ArcanaStorageTile tile : this.arcanaStorageInSearchRange)
        {
            tile.addArcanaStorageInRange(this);
        }
    }

    @Override
    public void remove()
    {
        for (ArcanaStorageTile tile : this.arcanaStorageInSearchRange)
        {
            tile.removeArcanaStorageInRange(this);
        }
        super.remove();
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

    @Override
    public void tick()
    {
        if (world.isRemote)
        {
            return;
        }

        long worldTicks = world.getWorld().getGameTime();

        // Every half second
        if (worldTicks % 10 == 0)
        {
            // Pass Arcana to transfer target
            if (this.arcanaTransferTarget != null)
            {
                int arcanaReceived = this.arcanaTransferTarget.recieveArcana(this.transferRate);
                this.extractArcana(arcanaReceived);
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

    // Phylactery Network Stuff

    public void addArcanaStorageInRange(ArcanaStorageTile tile)
    {
        this.arcanaStorageInSearchRange.add(tile);
        BlockPos pos = tile.getPos();

        if (pos.withinDistance(this.getPos(), this.transferRange))
        {
            this.arcanaStorageInTransferRange.add(tile);
        }

        if (!this.manualLinkOnly)
        {
            ArcanaStorageTile minDistanceTile = Collections.min(this.arcanaStorageInTransferRange, Comparator.comparing(aT -> aT.getDistanceSq(this.pos.getX(), this.pos.getY(), this.pos.getZ())));
            this.arcanaTransferTarget = minDistanceTile;
        }
    }

    public void removeArcanaStorageInRange(ArcanaStorageTile tile)
    {
        this.arcanaStorageInSearchRange.remove(tile);
        this.arcanaStorageInTransferRange.remove(tile);
    }

    public void setRange(double range)
    {
        this.transferRange = range;
        this.updateArcanaStorageTiles();
    }

    private void updateArcanaStorageTiles()
    {
        this.arcanaStorageInSearchRange = this.getArcanaStorageInSearchRange();

        Set<ArcanaStorageTile> storageTEsInRange= new HashSet<>();

        for ( ArcanaStorageTile tile : this.arcanaStorageInSearchRange)
        {
            BlockPos pos = tile.getPos();
            if (pos.withinDistance(this.getPos(), this.transferRange))
            {
                storageTEsInRange.add(tile);
            }
        }
        this.arcanaStorageInTransferRange = storageTEsInRange;
    }

    private Set<ArcanaStorageTile> getArcanaStorageInSearchRange()
    {
        Set<ArcanaStorageTile> phylTEs = new HashSet<>();
        List<TileEntity> allTEs = world.loadedTileEntityList;

        for (TileEntity tileEntity : Collections2.filter(allTEs, this.searchPredicate))
        {
            ArcanaStorageTile tile = (ArcanaStorageTile) tileEntity;
            phylTEs.add(tile);
        }
        return phylTEs;
    }

}
