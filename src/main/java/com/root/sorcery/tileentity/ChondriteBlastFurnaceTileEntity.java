package com.root.sorcery.tileentity;

import com.root.sorcery.block.ModBlock;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.tileentity.AbstractFurnaceTileEntity;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

import javax.annotation.Nonnull;

public class ChondriteBlastFurnaceTileEntity extends AbstractFurnaceTileEntity {

    public ChondriteBlastFurnaceTileEntity(){
        super(ModBlock.CHONDRITE_BLAST_FURNACE_TILE, IRecipeType.BLASTING);
    }

    @Override
    @Nonnull
    public ITextComponent getDefaultName() {
        return new TranslationTextComponent("container.blast_furnace", new Object[0]);
    }

    //@Nonnull
    @Override
    public Container createMenu(int num, PlayerInventory playerInventory) {
        //return new BlastFurnaceContainer(num, playerInventory);
        return null;
    }

    /* createMenu should look like this somehow maybe
    @Nullable
   public Container createMenu(int p_createMenu_1_, PlayerInventory p_createMenu_2_, PlayerEntity p_createMenu_3_) {
      return this.canOpen(p_createMenu_3_) ? this.createMenu(p_createMenu_1_, p_createMenu_2_) : null;
   }
     */
}
