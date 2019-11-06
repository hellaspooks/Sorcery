package com.root.sorcery.spell;

import com.root.sorcery.tileentity.ArcanaStorageTile;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.math.BlockPos;

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
    public ActionResultType castServer(SpellUseContext context)
    {
        if (context.hasHitPos())
        {
            BlockPos pos = context.getHitPos();
            TileEntity tile = context.getWorld().getTileEntity(pos);

            if (tile instanceof ArcanaStorageTile)
            {
                int arcanaExtracted = ((ArcanaStorageTile) tile).extractArcana(arcanaDrainRate);
                context.getArcanaSource().receiveArcana(arcanaExtracted, false);
                return ActionResultType.SUCCESS;
            }
        }
        return ActionResultType.FAIL;
    }

    @Override
    public void doParticleEffects(SpellUseContext context)
    {
    }
}
