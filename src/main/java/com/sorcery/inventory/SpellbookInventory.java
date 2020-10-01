package com.sorcery.inventory;

import com.sorcery.item.ModItem;
import com.sorcery.item.SpellScrollItem;
import com.sorcery.network.PacketHandler;
import com.sorcery.network.packets.SpellCapSyncPacket;
import com.sorcery.spellcasting.ISpellcasting;
import com.sorcery.spellcasting.SpellcastingCapability;
import com.sorcery.utils.Utils;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.system.CallbackI;

public class SpellbookInventory extends Inventory
{

    private final ItemStack stack;

    public SpellbookInventory(ItemStack stack, int count) {
        super(count);
        this.stack = stack;
        readItemStack();
    }

    public ItemStack getStack() {
        return stack;
    }

    public void readItemStack() {
        if (stack.getTag() != null) {
            readNBT(stack.getTag());
        }
    }

    public void writeItemStack(PlayerInventory playerInventory) {
        if (isEmpty()) {
            stack.removeChildTag("Items");
        } else {
            writeNBT(stack.getOrCreateTag(), stack, playerInventory);
        }
    }

    private void readNBT(CompoundNBT compound) {
        final NonNullList<ItemStack> list = NonNullList.withSize(getSizeInventory(), ItemStack.EMPTY);
        ItemStackHelper.loadAllItems(compound, list);
        for (int index = 0; index < list.size(); index++) {
            setInventorySlotContents(index, list.get(index));
        }
    }

    private void writeNBT(CompoundNBT compound, ItemStack stack, PlayerInventory playerInventory) {
        final NonNullList<ItemStack> list = NonNullList.withSize(getSizeInventory(), ItemStack.EMPTY);
        ISpellcasting spellCasting = Utils.getSpellCap(stack);
        System.out.println("existing spell cap");
        System.out.println(spellCasting);
        spellCasting.clearPreparedSpells();
        System.out.println("in write NBT in spellbook inventory");
        for (int index = 0; index < list.size(); index++) {
            ItemStack stack1 = getStackInSlot(index);
            list.set(index, getStackInSlot(index));
            if (stack1.getItem() instanceof SpellScrollItem)
            {
                System.out.println("adding spell from scroll");
                ResourceLocation spellLoc = ((SpellScrollItem) stack1.getItem()).getSpell();
                System.out.println(spellLoc.toString());
                spellCasting.addPreparedSpell(spellLoc);
            }
        }
        System.out.println("final spellcasting:");
        System.out.println(spellCasting.serializeNBT());
        ItemStackHelper.saveAllItems(compound, list, true);
        PacketHandler.sendToPlayer((ServerPlayerEntity) playerInventory.player, new SpellCapSyncPacket((CompoundNBT) SpellcastingCapability.SPELLCASTING.writeNBT(spellCasting, null)));
    }
}
