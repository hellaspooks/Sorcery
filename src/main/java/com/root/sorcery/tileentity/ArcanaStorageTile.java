package com.root.sorcery.tileentity;

import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import com.root.sorcery.arcana.ArcanaStorage;
import com.root.sorcery.arcana.IArcanaStorage;
import com.root.sorcery.particle.ParticleEffectContext;
import com.root.sorcery.particle.ParticleEffects;
import com.root.sorcery.particle.Particles;
import com.root.sorcery.utils.Utils;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.IntArrayNBT;
import net.minecraft.nbt.ListNBT;
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
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ArcanaStorageTile extends TileEntity implements ITickableTileEntity
{

    // base vars

    protected ArcanaStorage arcanaStorage = new ArcanaStorage(10000);

    protected int transferRate = 100;


    // Arcana network vars
    protected ArcanaStorageTile arcanaTransferTarget = null;

    protected Set<ArcanaStorageTile> arcanaTransferSources = new HashSet<>();

    protected int[] targetPos = null;

    // Arcana Vacuum Vars

    private boolean vacuum = false;

    private int vacuumRange = 8;

    protected int vacuumAmountTarget = 0;

    public int vacuumPerTick = 0;

    private Set<ArcanaStorageTile> vacuumSources = new HashSet<>();

    // Pulse particles

    protected Vec3d arcanaPulseTarget = null;

    protected Vec3d arcanaPulseSource = null;

    protected Vec3d arcanaPulseOffset = new Vec3d(0.5, 1, 0.5);

    // to offset processing
    protected int worldTickOffset = 0;


    public ArcanaStorageTile(TileEntityType<?> tileEntityTypeIn)
    {
        super(tileEntityTypeIn);
    }
    // REFACTOR STARTS HERE

    // Set arcana transfer target
    public void setArcanaTransferTarget(ArcanaStorageTile tile)
    {
        this.arcanaTransferTarget = tile;
        this.arcanaPulseTarget = tile.getArcanaPulseTarget();
        tile.addArcanaTransferSource(this);
        this.updateAndMarkDirty();
    }

    public void setArcanaTransferTargetPos(int x, int y, int z)
    {
        this.targetPos = new int[]{x, y, z};
        try {
            TileEntity tile = world.getTileEntity(new BlockPos(x, y, z));
            if (tile instanceof ArcanaStorageTile) {
                this.setArcanaTransferTarget((ArcanaStorageTile) tile);
            }
        } catch (NullPointerException e)
        {
            System.out.println("target not yet loaded");
        }
    }

    // Remove arcana transfer target
    public void removeArcanaTransferTarget()
    {
        this.arcanaTransferTarget = null;
        this.targetPos = null;
        this.arcanaPulseTarget = null;
        this.updateAndMarkDirty();
    }

    // Add arcana transfer source
    public void addArcanaTransferSource(ArcanaStorageTile tile)
    {
        this.arcanaTransferSources.add(tile);
        this.updateAndMarkDirty();
    }

    public void addArcanaTransferSource(int x, int y, int z)
    {
        try {
            TileEntity tile = world.getTileEntity(new BlockPos(x, y, z));
            if (tile instanceof ArcanaStorageTile) {
                this.addArcanaTransferSource((ArcanaStorageTile) tile);
            }
        } catch (NullPointerException e)
        {
            System.out.println("source not yet loaded");
        }
    }

    // Remove Arcana transfer source
    public void removeArcanaTransferSource(ArcanaStorageTile tile)
    {
        if (this.arcanaTransferSources.contains(tile))
        {
            this.arcanaTransferSources.remove(tile);
        }
        this.updateAndMarkDirty();
    }

    // Arcana Vacuum methods

    public void startArcanaVacuum(int arcanaAmount)
    {
        this.vacuumAmountTarget = arcanaAmount;
        this.vacuum = true;

        this.updateVacuumSources();
    }

    private void updateVacuumSources()
    {
        Predicate<TileEntity> searchPredicate = Utils.getTESearchPredicate(ArcanaStorageTile.class, this, this.vacuumRange);

        Set<ArcanaStorageTile> sources = new HashSet<>();

        List<TileEntity> allTEs = world.loadedTileEntityList;

        for (TileEntity tileEntity : Collections2.filter(allTEs, searchPredicate))
        {
            sources.add((ArcanaStorageTile)tileEntity);
        }
        this.vacuumSources = sources;
    }

    private void tryVacuumArcana()
    {
        int target = this.vacuumPerTick;
        
        for (ArcanaStorageTile tile : this.vacuumSources)
        {
            target -= tile.extractArcana(target);

            if (target <= 0)
            {
                target = 0;
                break;
            }
        }
        this.receiveArcana(this.vacuumPerTick - target);
    }
    
    private void clearVacuum()
    {
        this.vacuum = false;
        this.vacuumAmountTarget = 0;
    }
    
    @Override
    public void tick()
    {
        if (world.isRemote)
        {
            if (this.getOffsetWorldTicks() % 40 == 0) {
                if (this.arcanaTransferTarget != null) {
                    ParticleEffects.arcanaPulse(new ParticleEffectContext(world.getWorld(), Particles.arcanaOrbs(), this.arcanaPulseSource, this.arcanaPulseTarget, 1, 1, 0, 40));
                }
                if (this.vacuum)
                {
                    // TODO: vacuum particle effect
                }
            }
            return;
        }

        long worldTicks = world.getWorld().getGameTime();

        // Every half second
        if (worldTicks % 10 == 0)
        {
            if (this.vacuum)
            {
                if (this.arcanaStorage.getArcanaStored() >= this.vacuumAmountTarget)
                {
                    this.clearVacuum();
                } else {
                    this.tryVacuumArcana();
                }
            }
            // Pass Arcana to transfer target
            if (this.arcanaTransferTarget != null)
            {
                int arcanaReceived = this.arcanaTransferTarget.receiveArcana(this.transferRate);
                this.extractArcana(arcanaReceived);
            }
        }
        if (worldTicks % 40 == 0)
        {
            if (this.targetPos !=  null)
            {
                this.setArcanaTransferTargetPos(this.targetPos[0], this.targetPos[1], this.targetPos[2]);
            }
        }
    }

    // Serialize transfer data

    public CompoundNBT writeTransferTag()
    {
        CompoundNBT tag = new CompoundNBT();

        // Transfer target
        if (this.arcanaTransferTarget != null) {
            BlockPos tPos = this.arcanaTransferTarget.getPos();
            tag.putIntArray("tPos", new int[]{tPos.getX(), tPos.getY(), tPos.getZ()});
        }

        // Transfer Sources
        if (!this.arcanaTransferSources.isEmpty())
        {
            ListNBT listNBT = new ListNBT();
            for (ArcanaStorageTile tile : this.arcanaTransferSources)
            {
                BlockPos pos = tile.getPos();
                IntArrayNBT posTag = new IntArrayNBT(new int[]{pos.getX(), pos.getY(), pos.getZ()});
                listNBT.add(posTag);
            }
            tag.put("sPos", listNBT);
        }

        return tag;
    }

    public void readTransferTag(CompoundNBT nbt)
    {
        if (nbt.contains("tPos"))
        {
            int[] tPos = nbt.getIntArray("tPos");
            this.setArcanaTransferTargetPos(tPos[0], tPos[1], tPos[2]);
        } else {
            this.arcanaTransferTarget = null;
        }

        if (nbt.contains("sPos"))
        {
            // Clear all existing sources
            this.arcanaTransferSources = new HashSet<>();
            // Load new sources
            ListNBT posList = nbt.getList("sPos", 11);
            for ( INBT posTag : posList)
            {
                int[] pos = ((IntArrayNBT) posTag).getIntArray();
                this.addArcanaTransferSource(pos[0], pos[1], pos[2]);
            }
        } else {
            this.arcanaTransferSources = new HashSet<>();
        }
    }


    // Sync client

    // tag received by client
    @Override
    public void handleUpdateTag(CompoundNBT tag)
    {
        if (tag.contains("tData"))
        {
            this.readTransferTag(tag.getCompound("tData"));
        }
        if (tag.contains("aData"))
        {
            this.arcanaStorage.deserializeNBT(tag.getCompound("aData"));
        }
    }

    // get tag to send client
    @Override
    public CompoundNBT getUpdateTag() {
        CompoundNBT nbt = this.write(new CompoundNBT());
        nbt.put("tData", this.writeTransferTag());
        nbt.put("aData", this.arcanaStorage.serializeNBT());
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

    // Save to disk

    @Override
    public void read(CompoundNBT nbt)
    {
        super.read(nbt);
        if (nbt.contains("tData"))
        {
            this.readTransferTag(nbt.getCompound("tData"));
        }
        if (nbt.contains("aData"))
        {
            this.arcanaStorage.deserializeNBT(nbt.getCompound("aData"));
        }
    }

    @Override
    public CompoundNBT write(CompoundNBT tag)
    {
        CompoundNBT nbt = super.write(tag);
        nbt.put("tData", this.writeTransferTag());
        nbt.put("aData", this.arcanaStorage.serializeNBT());
        return nbt;
    }

    // Update and Save
    public void updateAndMarkDirty()
    {
        if (!this.world.isRemote())
        {
            this.markDirty();
            this.world.notifyBlockUpdate(this.getPos(), this.getBlockState(), this.getBlockState(), 3);
        }
    }


    // Arcana Pulse helpers
    public Vec3d getArcanaPulseTarget()
    {
        return this.arcanaPulseSource;
    }

    public void setArcanaPulseSource()
    {
        this.arcanaPulseSource = new Vec3d(pos).add(this.arcanaPulseOffset);
    }


    // Loading and removing

    @Override
    public void onLoad()
    {
        this.setArcanaPulseSource();
        this.worldTickOffset = this.world.rand.nextInt(20);
        super.onLoad();
    }

    @Override
    public void remove()
    {
        if (this.arcanaTransferTarget != null)
        {
            this.arcanaTransferTarget.removeArcanaTransferSource(this);
        }
        for (ArcanaStorageTile tile : this.arcanaTransferSources)
        {
            tile.removeArcanaTransferTarget();
        }
        super.remove();
    }

    // Arcana Handling

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

    public int receiveArcana(int arcana)
    {

        int spareArcana = this.arcanaStorage.receiveArcana(arcana, false);
        this.updateAndMarkDirty();
        return spareArcana;
    }

    public int getStoredArcana()
    {
        return this.arcanaStorage.getArcanaStored();
    }

    public int extractArcana(int arcana)
    {
        int arcanaExtracted = this.arcanaStorage.extractArcana(arcana, false);
        this.updateAndMarkDirty();
        return arcanaExtracted;
    }

    public int getMaxArcana()
    {
        return this.arcanaStorage.getMaxArcanaStored();
    }

    // to stagger processing
    public long getOffsetWorldTicks()
    {
        return this.world.getGameTime() + this.worldTickOffset;

    }
}
