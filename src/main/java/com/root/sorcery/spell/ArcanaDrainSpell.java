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
            System.out.println("Context has Hit Pos");
            BlockPos pos = context.getHitPos();
            System.out.println("Hit pos:");
            System.out.println(pos);
            TileEntity tile = context.getWorld().getTileEntity(pos);
            System.out.println("Tile Entity?");
            System.out.println(tile);

            if (tile instanceof ArcanaStorageTile)
            {
                System.out.println("Draining Arcana");
                int arcanaExtracted = ((ArcanaStorageTile) tile).extractArcana(arcanaDrainRate);
                System.out.println(String.format("Arcana Drained: %d", arcanaExtracted));
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
