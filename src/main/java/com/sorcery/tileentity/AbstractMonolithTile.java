package com.sorcery.tileentity;

import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import com.sorcery.utils.Utils;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.math.vector.Vector3d;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public abstract class AbstractMonolithTile extends ArcanaStorageTile implements ITickableTileEntity
{

    protected int arcanaPerRegen = 2;
    protected int ticksPerRegen = 2;

    protected boolean active = false;

    protected int interferenceRange = 5;

    protected boolean interference = false;


    public AbstractMonolithTile(TileEntityType variantIn, int maxArcana)
    {
        super(variantIn);
        this.arcanaStorage.setMaxArcanaStored(maxArcana);
        this.arcanaPulseOffset = new Vector3d(0.5, 2, 0.5);
    }

    @Override
    public void tick()
    {
        if (!world.isRemote())
        {
            if (this.getOffsetWorldTicks() % 40 == 0)
            {
                updateInterference();
            }
        }
        super.tick();
    }



    public void updateInterference()
    {
        Predicate<TileEntity> searchPredicate = Utils.getTESearchPredicate(AbstractMonolithTile.class, this, this.interferenceRange);

        Set<ArcanaStorageTile> otherMonoliths = new HashSet<>();

        List<TileEntity> allTEs = world.loadedTileEntityList;

        for (TileEntity tileEntity : Collections2.filter(allTEs, searchPredicate))
        {
            otherMonoliths.add((ArcanaStorageTile)tileEntity);
        }
        if (!otherMonoliths.isEmpty())
        {
            this.interference = true;
        } else {
            this.interference = false;
        }
    }

    public int getInterferenceRange()
    {
        return this.interferenceRange;
    }

}
