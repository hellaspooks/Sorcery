package com.root.sorcery.tileentity;

import com.root.sorcery.arcana.ArcanaStorage;
import com.root.sorcery.arcana.IArcanaStorage;
import com.root.sorcery.particle.ModParticle;
import com.root.sorcery.particle.ParticleEffects;
import com.root.sorcery.particle.RGBAParticleData;
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
import java.util.Set;

public class ArcanaStorageTile extends TileEntity implements ITickableTileEntity
{

    protected ArcanaStorage arcanaStorage = new ArcanaStorage(10000);

    protected int transferRate = 100;

    protected boolean drainAll = false;

    private static int arcanaPerWhack = 1000;


    // Arcana network vars
    protected ArcanaStorageTile arcanaTransferTarget = null;

    protected Set<ArcanaStorageTile> arcanaTransferSources = new HashSet<>();

    protected int[] targetPos = null;

    // Pulse particles

    protected Vec3d arcanaPulseTarget = null;

    protected Vec3d arcanaPulseSource = null;

    protected Vec3d arcanaPulseOffset = new Vec3d(0.5, 1, 0.5);


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
    }

    // get tag to send client
    @Override
    public CompoundNBT getUpdateTag() {
        CompoundNBT nbt = this.write(new CompoundNBT());
        nbt.put("tData", this.writeTransferTag());
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
    }

    @Override
    public CompoundNBT write(CompoundNBT tag)
    {
        CompoundNBT nbt = super.write(tag);
        nbt.put("tData", this.writeTransferTag());
        return nbt;
    }

    // Update and Save
    public void updateAndMarkDirty()
    {
        world.notifyBlockUpdate(pos, this.getBlockState(), this.getBlockState(), 3);
        markDirty();
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


    @Override
    public void tick()
    {
        if (world.isRemote)
        {
            if (world.getWorld().getGameTime() % 10 == 0) {
                if (this.arcanaTransferTarget != null) {
                    ParticleEffects.sendTo(world.getWorld(), ParticleEffects.getArcanaOrb(), this.arcanaPulseSource, this.arcanaPulseTarget, 1, 1, 0);
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
                int arcanaReceived = this.arcanaTransferTarget.receiveArcana(this.transferRate);
                this.extractArcana(arcanaReceived);
            }
        }
        if (worldTicks % 20 == 0)
        {
            if (this.targetPos !=  null)
            {
                this.setArcanaTransferTargetPos(this.targetPos[0], this.targetPos[1], this.targetPos[2]);
            }
        }
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

    public int receiveArcana(int arcana)
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
}
