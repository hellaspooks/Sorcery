package com.root.sorcery.spell;

import com.root.sorcery.network.PacketHandler;
import com.root.sorcery.network.packets.ParticleEffectPacket;
import com.root.sorcery.tileentity.ArcanaStorageTile;
import com.root.sorcery.utils.Utils;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

public class ArcanaDrainSpell extends Spell
{
    private int arcanaDrainRate = 100;

    public ArcanaDrainSpell()
    {
        super(0);
        this.castDuration = 100;
        this.castType = CastType.CHANNELED;
    }

    @Override
    public ActionResultType doCastPerTick(SpellUseContext context)
    {
        if (context.hasHitPos())
        {
            BlockPos pos = context.getHitPos();
            TileEntity tile = context.getWorld().getTileEntity(pos);

            if (tile instanceof ArcanaStorageTile)
            {
                this.doSuckParticleEffect(context, (ArcanaStorageTile)tile);
                int arcanaExtracted = ((ArcanaStorageTile) tile).extractArcana(arcanaDrainRate);
                context.getArcanaSource().receiveArcana(arcanaExtracted, false);
                return ActionResultType.SUCCESS;
            }
        }
        return ActionResultType.FAIL;
    }

    public void doSuckParticleEffect(SpellUseContext context, ArcanaStorageTile tile)
    {
        Vec3d suckVec = Utils.getStaffVector(context.getPlayer());
        ParticleEffectPacket pkt =  new ParticleEffectPacket(7, 0, suckVec, tile.getArcanaPulseTarget(), 20, 1, 0.5, 40);
        PacketHandler.sendToAllTrackingPlayer(context.getPlayer(), pkt);
    }
}
