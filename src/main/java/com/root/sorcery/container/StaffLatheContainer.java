package com.root.sorcery.container;

import com.root.sorcery.block.ModBlock;
import com.root.sorcery.tileentity.StaffLatheTile;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IWorldPosCallable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import net.minecraftforge.items.wrapper.InvWrapper;

import java.util.List;


public class StaffLatheContainer extends Container
{

    private TileEntity tileEntity;
    private PlayerEntity playerEntity;
    private IItemHandler playerInventory;
    private IWorldPosCallable posCallable;

    private StaffCraftingInventory tileInventory;
    private IItemHandler inventory;


    private int comp1X = 59;
    private int comp1Y = 31;

    private int comp2X = 80;
    private int comp2Y = 20;

    private int comp3X = 101;
    private int comp3Y = 31;

    private int resultX = 80;
    private int resultY = 46;


    public StaffLatheContainer(int id, World world, BlockPos pos, PlayerInventory playerInventory, PlayerEntity player)
    {
        super(Containers.STAFF_LATHE_CONTAINER, id);
        tileEntity = world.getTileEntity(pos);
        this.playerEntity = player;

        this.posCallable = IWorldPosCallable.of(tileEntity.getWorld(), tileEntity.getPos());

        this.playerInventory = new InvWrapper(playerInventory);


        this.tileInventory = ((StaffLatheTile) tileEntity).getInventory();
        this.inventory = new InvWrapper(this.tileInventory);

        layoutPlayerInventorySlots(8, 84);

        layoutComponentSlots(this.inventory);
    }

    public boolean isCraftReady()
    {
        return this.tileInventory.craftReady;
    }

    public void startCraft()
    {
        this.tileInventory.startPendingCraft();
    }

    public void uncraft()
    {
        this.tileInventory.sendUncraft();
    }

    @Override
    public ItemStack transferStackInSlot(PlayerEntity playerIn, int index)
    {
        // TO-DO: Proper shift-click behavior
        return ItemStack.EMPTY;
    }

    @Override
    public boolean canInteractWith(PlayerEntity playerIn)
    {
        return isWithinUsableDistance(this.posCallable, playerEntity, ModBlock.STAFF_LATHE);
    }

    private void layoutComponentSlots(IItemHandler handler)
    {
        addSlot(new SlotItemHandler(handler, 0, comp1X, comp1Y));
        addSlot(new SlotItemHandler(handler, 1, comp2X, comp2Y));
        addSlot(new SlotItemHandler(handler, 2, comp3X, comp3Y));

        addSlot(new SlotItemHandler(handler, 3, resultX, resultY));

    }

    private int addSlotRange(IItemHandler handler, int index, int x, int y, int amount, int dx) {
        for (int i = 0 ; i < amount ; i++) {
            addSlot(new SlotItemHandler(handler, index, x, y));
            x += dx;
            index++;
        }
        return index;
    }

    private int addSlotBox(IItemHandler handler, int index, int x, int y, int horAmount, int dx, int verAmount, int dy) {
        for (int j = 0 ; j < verAmount ; j++) {
            index = addSlotRange(handler, index, x, y, horAmount, dx);
            y += dy;
        }
        return index;
    }

    private void layoutPlayerInventorySlots(int leftCol, int topRow) {
        // Player inventory
        addSlotBox(playerInventory, 9, leftCol, topRow, 9, 18, 3, 18);

        // Hotbar
        topRow += 58;
        addSlotRange(playerInventory, 0, leftCol, topRow, 9, 18);
    }

    @Override
    public void onContainerClosed(PlayerEntity playerIn)
    {
    }

    public double getProgressPercent()
    {
        return ((StaffLatheTile)this.tileEntity).getProgressPercent();
    }
}
