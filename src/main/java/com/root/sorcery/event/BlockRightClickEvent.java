package com.root.sorcery.event;

import com.root.sorcery.item.CrystalItem;
import com.root.sorcery.item.GeodeItem;
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

public class BlockRightClickEvent {

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
        if (!GeodeItem.isGeode(itemUsed))
            return;

        if (!event.getPlayer().isSneaking()) {
            Utils.dropItemInWorld(event.getPos(), CrystalItem.getRandomCrystal(), event.getWorld());
            ItemStack replaceItemStack = event.getPlayer().getHeldItemMainhand();
            replaceItemStack.setCount(replaceItemStack.getCount() - 1);
            event.getPlayer().inventory.setInventorySlotContents(Utils.getIndexOfMainHand(event.getPlayer()), replaceItemStack);
        } else {
            ItemStack itemStack = event.getItemStack();
            int amount = itemStack.getCount();
            World world = event.getWorld();

            ArrayList<ItemStack> items = CrystalItem.getRandomCrystal(amount);
            for (ItemStack item : items) {
                Utils.dropItemInWorld(event.getPos(), item, world);
            }

            event.getPlayer().inventory.setInventorySlotContents(Utils.getIndexOfMainHand(event.getPlayer()), new ItemStack(Items.AIR));
        }
    }
}
