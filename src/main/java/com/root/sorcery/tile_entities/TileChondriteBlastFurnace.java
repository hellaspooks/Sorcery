package com.root.sorcery.tile_entities;

import com.root.sorcery.blocks.ModBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.BlastFurnaceContainer;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.tileentity.AbstractFurnaceTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

import javax.annotation.Nonnull;

public class TileChondriteBlastFurnace extends AbstractFurnaceTileEntity {

    public TileChondriteBlastFurnace(){
        super(ModBlock.TILE_CHONDRITE_BLAST_FURNACE, IRecipeType.BLASTING);
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
