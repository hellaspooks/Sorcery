package com.root.sorcery.tileentity;

import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;

public class ConjuredBlockTile extends TileEntity implements ITickableTileEntity
{

    public int lifeTicks;

    public boolean decay = true;

    public ConjuredBlockTile()
    {
        super(ModTile.CONJURED_BLOCK_TILE);
        this.lifeTicks = 60 * 20;
    }

    @Override
    public void tick()
    {
        if (!this.world.isRemote())
        {
            if (this.decay)
            {
                if (this.lifeTicks <= 0)
                {
                    this.world.removeBlock(this.pos, false);
                    this.remove();
                } else {
                    this.lifeTicks -= 1;
                }
            }
        }

    }

    public void setLifeTicks(int lifeTicks)
    {
        this.lifeTicks = lifeTicks;
    }
}
