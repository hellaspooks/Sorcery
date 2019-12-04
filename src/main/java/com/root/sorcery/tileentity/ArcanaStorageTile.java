package com.root.sorcery.tileentity;

import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import com.root.sorcery.arcana.ArcanaStorage;
import com.root.sorcery.arcana.IArcanaStorage;
import com.root.sorcery.particle.ModParticle;
import com.root.sorcery.particle.ParticleEffects;
import com.root.sorcery.utils.Utils;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
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

    protected Vec3d arcanaPulseTarget = null;

    protected Vec3d arcanaPulseSource = null;

    protected Vec3d arcanaPulseOffset = new Vec3d(0.5, 1, 0.5);

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
        this.arcanaPulseSource = new Vec3d(this.getPos()).add(this.arcanaPulseOffset);
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
            if (world.getWorld().getGameTime() % 10 == 0) {
                if (this.arcanaTransferTarget != null) {
                    ParticleEffects.sendTo(world.getWorld(), ModParticle.SPARK_SLOW, this.arcanaPulseSource, this.arcanaPulseTarget, 1, 1, 0);
                }
            }
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

    // New Stuff

    public void setArcanaTransferTarget(ArcanaStorageTile tile)
    {
        System.out.println("Setting arcana transfer target!");
        this.arcanaTransferTarget = tile;
        this.arcanaPulseTarget = tile.getArcanaPulseTarget();
        world.notifyBlockUpdate(this.pos, getBlockState(), getBlockState(), 3);
        markDirty();
    }

    public void setArcanaTransferTargetPos(int x, int y, int z)
    {
        TileEntity tile = world.getTileEntity(new BlockPos(x, y, z));
        if (tile instanceof ArcanaStorageTile)
        {
            this.setArcanaTransferTarget((ArcanaStorageTile) tile);
        }
    }

    @Override
    public CompoundNBT write(CompoundNBT compound)
    {
        CompoundNBT nbt = super.write(new CompoundNBT());
        if (this.arcanaTransferTarget != null)
        {
            BlockPos tPos = this.arcanaTransferTarget.getPos();
            CompoundNBT targetTag = new CompoundNBT();
            targetTag.putInt("x", tPos.getX());
            targetTag.putInt("y", tPos.getY());
            targetTag.putInt("z", tPos.getZ());
            nbt.put("aTarget", targetTag);
        }
        return nbt;
    }

    @Override
    public void read(CompoundNBT compound)
    {
        super.read(compound);
        if (compound.contains("aTarget"))
        {
            CompoundNBT nbt = compound.getCompound("aTarget");
            this.setArcanaTransferTargetPos(nbt.getInt("x"), nbt.getInt("y"), nbt.getInt("z"));
        }
    }

    @Override
    public void handleUpdateTag(CompoundNBT tag)
    {
        if (tag.contains("aTarget"))
        {
           CompoundNBT nbt = tag.getCompound("aTarget");
           this.setArcanaTransferTargetPos(nbt.getInt("x"), nbt.getInt("y"), nbt.getInt("z"));
        }
    }

    @Override
    public CompoundNBT getUpdateTag() {
        CompoundNBT nbt = this.write(new CompoundNBT());
        return nbt;
    }

    @Override
    @Nullable
    public SUpdateTileEntityPacket getUpdatePacket() {
        return new SUpdateTileEntityPacket(this.pos, 3, this.getUpdateTag());
    }

    @Override
    public void onDataPacket(NetworkManager net, SUpdateTileEntityPacket pkt) {
        super.onDataPacket(net, pkt);
        handleUpdateTag(pkt.getNbtCompound());
    }

    public Vec3d getArcanaPulseTarget()
    {
        return this.arcanaPulseSource;
    }

}
