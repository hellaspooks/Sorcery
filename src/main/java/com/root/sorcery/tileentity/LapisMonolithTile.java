package com.root.sorcery.tileentity;

import com.root.sorcery.block.MonolithBlock;
import com.root.sorcery.network.PacketHandler;
import com.root.sorcery.network.packets.ParticleEffectPacket;
import com.root.sorcery.particle.Particles;
import com.root.sorcery.utils.Utils;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.item.Items;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

import java.util.ArrayList;
import java.util.List;


public class LapisMonolithTile extends AbstractMonolithTile
{

    private int arcanaPerLapis = 100;

    public LapisMonolithTile()
    {
        super(ModTile.LAPIS_MONOLITH_TILE, 1000);
        this.arcanaStorage.extractArcana(1000, false);
    }

    public void acceptLapis()
    {
        this.receiveArcana(arcanaPerLapis);
    }

    public boolean canAcceptLapis()
    {
        int arcanaSpace = this.arcanaStorage.getMaxArcanaStored() - this.arcanaStorage.getArcanaStored();
        if (arcanaSpace >= this.arcanaPerLapis)
        {
            return true;
        } else {
            return false;
        }
    }

    public void doSuckParticleEffect(ItemEntity entity)
    {
        Vec3d suckVec = entity.getPositionVec();
        ParticleEffectPacket pkt =  new ParticleEffectPacket(7, Particles.getLapisSpark(), this.arcanaPulseSource, suckVec, 20, 1, 0.5, 40);
        PacketHandler.sendToAllTrackingEntity(entity, pkt);
    }

    @Override
    public void tick()
    {
        if (!world.isRemote())
        {
            long worldTicks = this.getOffsetWorldTicks();
            if (worldTicks % 20 == 0) {
                // check if active
                if (this.arcanaStorage.getArcanaStored() < 10) {
                    MonolithBlock.setActivity(world, this.getBlockState(), this.pos, false);
                } else {
                    MonolithBlock.setActivity(world, this.getBlockState(), this.pos, true);
                }
            }

            if (worldTicks % 40 == 0)
            {
                // look for lapis on ground
                List<ItemEntity> allItemEntities = world.getEntitiesWithinAABB(ItemEntity.class, Utils.getRangeAABB(this.pos, 8, 4, 2));
                List<ItemEntity> lapisItemEntities = new ArrayList<>();
                // set no despawn on all
                for ( ItemEntity itemEntity : allItemEntities)
                {
                    if (itemEntity.getItem().getItem() == Items.LAPIS_LAZULI)
                    {
                        itemEntity.setNoDespawn();
                        lapisItemEntities.add(itemEntity);
                    }
                }
                // suck up one lapis
                if (!lapisItemEntities.isEmpty())
                {
                    ItemEntity itemEntity = lapisItemEntities.get(0);
                    if (this.canAcceptLapis() && !this.interference)
                    {
                        this.doSuckParticleEffect(itemEntity);
                        this.acceptLapis();
                        itemEntity.getItem().shrink(1);
                    }
                }
            }
        }
        super.tick();
    }
}
