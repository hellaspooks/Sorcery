package com.root.sorcery.container;

import com.root.sorcery.block.ModBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IWorldPosCallable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import net.minecraftforge.items.wrapper.InvWrapper;


public class EscritorieContainer extends Container
{
    private TileEntity tileEntity;
    private IItemHandler playerInventory;
    private IWorldPosCallable posCallable;

    private int resultSlotX = 0;
    private int resultSlotY = 0;


    public EscritorieContainer(int id, World world, BlockPos pos, PlayerInventory playerInventory, PlayerEntity player)
    {
        super(Containers.ESCRITORIE_CONTAINER, id);
        tileEntity = world.getTileEntity(pos);

        this.playerInventory = new InvWrapper(playerInventory);

        this.posCallable = IWorldPosCallable.of(tileEntity.getWorld(), tileEntity.getPos());


        layoutPlayerInventorySlots(8, 84);

    }

    private void layoutResultSlot(IItemHandler handler)
    {
        addSlot(new SlotItemHandler(handler, 0, resultSlotX, resultSlotY));
    }

    @Override
    public boolean canInteractWith(PlayerEntity playerIn)
    {
        return isWithinUsableDistance(this.posCallable, playerIn, ModBlock.ESCRITORIE);
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
}
