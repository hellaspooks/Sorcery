package com.root.sorcery.events;

import com.root.sorcery.items.Crystals;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.Item;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class BlockRightClicks {

    @SubscribeEvent
    public static void rightClickStoneCutter(PlayerInteractEvent.RightClickBlock event) {
        Block blockClicked = event.getWorld().getBlockState(event.getPos()).getBlock();
        Item itemUsed = event.getItemStack().getItem();
        if (blockClicked != Blocks.STONECUTTER)
            return;
        if (itemUsed == null)
            return;

        if (!Crystals.isCrystal(itemUsed))
            return;

        Crystals.getRandomCrystal();

        event.setUseBlock(Event.Result.DENY);
    }
}
