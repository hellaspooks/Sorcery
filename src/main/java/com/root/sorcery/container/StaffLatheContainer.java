package com.root.sorcery.container;

import com.root.sorcery.block.ModBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.CraftResultInventory;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.CraftingResultSlot;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IWorldPosCallable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import net.minecraftforge.items.wrapper.InvWrapper;


public class StaffLatheContainer extends Container
{

    private TileEntity tileEntity;
    private PlayerEntity playerEntity;
    private IItemHandler playerInventory;
    private IItemHandler craftingInventoryHandler;
    private CraftingInventory craftingInventory;
    private CraftResultInventory craftingOutput;
    private IWorldPosCallable posCallable;

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

        this.craftingInventory = new StaffCraftingInventory(this, 3, 1);
        this.craftingInventoryHandler = new InvWrapper(this.craftingInventory);
        this.craftingOutput = new CraftResultInventory();




        layoutPlayerInventorySlots(8, 84);

        layoutComponentSlots(this.craftingInventoryHandler);
        layoutResultSlot();
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

    }

    private void layoutResultSlot()
    {
        addSlot(new CraftingResultSlot(this.playerEntity, this.craftingInventory, this.craftingOutput, 0, resultX, resultY));
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

    public void onContainerClosed(PlayerEntity playerIn) {
        super.onContainerClosed(playerIn);
        this.posCallable.consume((p_217068_2_, p_217068_3_) -> {
            this.clearContainer(playerIn, p_217068_2_, this.craftingInventory);
        });
    }
}
