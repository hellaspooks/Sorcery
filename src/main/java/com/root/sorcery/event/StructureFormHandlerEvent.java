package com.root.sorcery.event;

import com.root.sorcery.block.ReliquaryBlock;
import net.minecraft.block.Blocks;
import net.minecraft.util.Direction;
import net.minecraftforge.event.world.BlockEvent.EntityPlaceEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static net.minecraft.block.LecternBlock.FACING;

@Mod.EventBusSubscriber
public class StructureFormHandlerEvent
{
    @SubscribeEvent
    public static void onBlockPlaced(EntityPlaceEvent event)
    {
        if (event.getPlacedBlock().getBlock() == Blocks.LECTERN)
        {
            Direction direction = event.getEntity().getEntityWorld().getBlockState(event.getPos()).get(FACING).getOpposite();
            ReliquaryBlock.checkAndTryPlacement(event.getEntity().getEntityWorld(), event.getPos(), direction);
        }
    }
}
