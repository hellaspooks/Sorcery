package com.sorcery.tileentity;

import com.sorcery.block.MonolithBlock;
import com.sorcery.network.PacketHandler;
import com.sorcery.network.packets.ParticleEffectPacket;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.math.vector.Vector3i;


public class DarkMonolithTile extends AbstractMonolithTile
{

    public double arcanaPerHP = 1;

    public DarkMonolithTile()
    {
        super(ModTile.DARK_MONOLITH_TILE, 1000);
        this.arcanaStorage.extractArcana(1000, false);
    }

    public void processDeath(LivingEntity entity)
    {
        if (this.interference)
        {
            return;
        }
        int arcanaToAdd = (int)(entity.getMaxHealth() * (float)arcanaPerHP);
        this.receiveArcana(arcanaToAdd);
        ParticleEffectPacket pkt =  new ParticleEffectPacket(7, 4, new Vector3d(this.pos.getX(), this.pos.getY(), this.pos.getZ()), entity.getPositionVec(), 20, 1, 1, 40);
        PacketHandler.sendToAllTrackingEntity(entity, pkt);
    }

    @Override
    public void tick()
    {
        if (!world.isRemote())
        {
            if (this.getOffsetWorldTicks() % 20 == 0)
            {
                if (this.arcanaStorage.getArcanaStored() < 10)
                {
                    MonolithBlock.setActivity(world, this.getBlockState(), this.pos, false);
                } else {
                    MonolithBlock.setActivity(world, this.getBlockState(), this.pos, true);

                }
            }
        }
        super.tick();
    }
}
