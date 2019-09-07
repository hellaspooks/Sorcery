package com.root.sorcery.events;

import com.root.sorcery.blocks.BlockReliquary;
import net.minecraft.block.Blocks;
import net.minecraft.util.Direction;
import net.minecraftforge.event.world.BlockEvent.EntityPlaceEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import static net.minecraft.block.LecternBlock.FACING;

public class StructureFormHandler
{
    @SubscribeEvent
    public static void onBlockPlaced(EntityPlaceEvent event)
    {
        if (event.getPlacedBlock().getBlock() == Blocks.LECTERN)
        {
            Direction direction = event.getEntity().getEntityWorld().getBlockState(event.getPos()).get(FACING).getOpposite();
            BlockReliquary.checkAndTryPlacement(event.getEntity().getEntityWorld(), event.getPos(), direction);
        }
    }
}
