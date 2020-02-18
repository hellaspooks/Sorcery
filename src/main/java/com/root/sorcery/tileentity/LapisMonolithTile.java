package com.root.sorcery.tileentity;

import com.root.sorcery.block.MonolithBlock;


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

    @Override
    public void tick()
    {
        if (!world.isRemote())
        {
            if (world.getGameTime() % 20 == 0)
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
