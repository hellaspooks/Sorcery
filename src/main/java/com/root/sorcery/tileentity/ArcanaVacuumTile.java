package com.root.sorcery.tileentity;

import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import com.root.sorcery.network.PacketHandler;
import com.root.sorcery.network.packets.ParticleEffectPacket;
import com.root.sorcery.particle.ParticleEffects;
import com.root.sorcery.utils.Utils;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.math.Vec3d;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ArcanaVacuumTile extends TileEntity implements ITickableTileEntity
{

    protected int arcanaTarget = 0;

    protected int arcanaReceieved = 0;
    protected int arcanaStored = 0;

    private int arcanaSuckedPerTick = 20;

    protected boolean suckingArcana = false;

    private int vacuumRange = 8;

    private Set<ArcanaStorageTile> currentSources = new HashSet<>();


    public ArcanaVacuumTile(TileEntityType<?> tileEntityTypeIn)
    {
        super(tileEntityTypeIn);
    }


    public void startArcanaVacuum(int arcanaTarget)
    {
        this.arcanaTarget = arcanaTarget;
        this.suckingArcana = true;

        this.updateArcanaSources();
    }

    private void updateArcanaSources()
    {
        Predicate<TileEntity> searchPredicate = Utils.getTESearchPredicate(ArcanaStorageTile.class, this, this.vacuumRange);

        Set<ArcanaStorageTile> sources = new HashSet<>();

        List<TileEntity> allTEs = world.loadedTileEntityList;

        for (TileEntity tileEntity : Collections2.filter(allTEs, searchPredicate))
        {
            sources.add((ArcanaStorageTile)tileEntity);
        }
        this.currentSources = sources;
    }

    private void trySuckArcana()
    {
        int target = this.arcanaSuckedPerTick;

        for (ArcanaStorageTile tile : this.currentSources)
        {
            target -= tile.extractArcana(target);
            ParticleEffectPacket pkt = new ParticleEffectPacket(4, ParticleEffects.getArcanaOrb(1), tile.arcanaPulseSource, new Vec3d(this.pos), 1, 1, 0);
            PacketHandler.sendToAllTrackingChunk(world.getChunkAt(this.pos), pkt);

            if (target <= 0)
            {
                target = 0;
                break;
            }
        }
        this.arcanaReceieved += (this.arcanaSuckedPerTick - target);
        this.arcanaStored += (this.arcanaSuckedPerTick - target);
    }


    @Override
    public void tick()
    {
        if (!world.isRemote)
        {
            long worldTicks = world.getWorld().getGameTime();

            if (worldTicks % 10 == 0) {
                if (this.suckingArcana) {
                    if (this.arcanaReceieved >= this.arcanaTarget) {
                        this.suckingArcana = false;
                        this.arcanaTarget = 0;
                        this.arcanaReceieved = 0;
                    } else {
                        this.trySuckArcana();
                    }
                }
            }
        }
    }
}
