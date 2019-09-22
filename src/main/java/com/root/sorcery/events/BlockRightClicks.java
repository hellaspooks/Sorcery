package com.root.sorcery.events;

import com.root.sorcery.items.Crystals;
import com.root.sorcery.items.Geode;
import com.root.sorcery.utils.Utils;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.ArrayList;

public class BlockRightClicks {

    @SubscribeEvent
    public static void leftClickStoneCutter(PlayerInteractEvent.LeftClickBlock event) {
        if (event.getWorld().isRemote)
            return;
        if (event.getHand() != Hand.MAIN_HAND)
            return;
        Block blockClicked = event.getWorld().getBlockState(event.getPos()).getBlock();
        Item itemUsed = event.getItemStack().getItem();
        if (blockClicked != Blocks.STONECUTTER)
            return;
        if (itemUsed == null)
            return;
        if (!Geode.isGeode(itemUsed))
            return;

        if (!event.getPlayer().isSneaking()) {
            Utils.dropItemInWorld(event.getPos(), Crystals.getRandomCrystal(), event.getWorld());
            ItemStack replaceItemStack = event.getPlayer().getHeldItemMainhand();
            replaceItemStack.setCount(replaceItemStack.getCount() - 1);
            event.getPlayer().inventory.setInventorySlotContents(Utils.getIndexOfMainHand(event.getPlayer()), replaceItemStack);
        } else {
            ItemStack itemStack = event.getItemStack();
            int amount = itemStack.getCount();
            World world = event.getWorld();

            ArrayList<ItemStack> items = Crystals.getRandomCrystal(amount);
            for (ItemStack item : items) {
                Utils.dropItemInWorld(event.getPos(), item, world);
            }

            event.getPlayer().inventory.setInventorySlotContents(Utils.getIndexOfMainHand(event.getPlayer()), new ItemStack(Items.AIR));
        }
    }
}
