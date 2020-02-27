package com.root.sorcery.event;

import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import com.root.sorcery.tileentity.DarkMonolithTile;
import com.root.sorcery.utils.Utils;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;


@Mod.EventBusSubscriber
public class EntityDeathEvent
{

    @SubscribeEvent
    public static void onLivingEntityDeath(LivingDeathEvent event)
    {
        // only process server side
        if (event.getEntity().world.isRemote())
        {
            return;
        }

        // scan for nearby monoliths
        Predicate<TileEntity> searchPred = Utils.getDarkMonolithSearchPredicate(DarkMonolithTile.class, event.getEntityLiving(), 8);

        // first monolith in list processes death, no others
        for (TileEntity tileEntity : Collections2.filter(event.getEntityLiving().world.tickableTileEntities, searchPred) )
        {
            ((DarkMonolithTile)tileEntity).processDeath(event.getEntityLiving());
            break;
        }
    }
}
