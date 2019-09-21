package com.root.sorcery.tile_entities;

import com.mojang.datafixers.DataFixUtils;
import com.mojang.datafixers.types.Type;
import com.root.sorcery.blocks.ModBlock;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraft.util.SharedConstants;
import net.minecraft.util.datafix.DataFixesManager;
import net.minecraft.util.datafix.TypeReferences;
import net.minecraft.util.registry.Registry;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;
//import org.checkerframework.checker.nullness.qual.NonNull;
//import org.checkerframework.checker.nullness.qual.Nullable;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

//import static com.root.sorcery.blocks.ModBlock.chondrite_furnace_entity;

public class ChondriteBlastFurnaceEntity extends TileEntity implements ITickableTileEntity {


   private ItemStackHandler handler;
 public boolean lit;

    public ChondriteBlastFurnaceEntity() {
        super(null);

    }

    @Override
    public void tick() {

    }

    @Override
    public void read(CompoundNBT tag){
        CompoundNBT invTag = tag.getCompound("inv");
        getHandler().deserializeNBT(invTag);
            super.read(tag);
    }

    @Override
    public CompoundNBT write(CompoundNBT tag){
     CompoundNBT compound = getHandler().serializeNBT();
     tag.put("inv", compound);
            return super.write(tag);
    }


    public TileEntityType<?> getType(){
        return this.getType();
    }

 private ItemStackHandler getHandler(){
      if(handler == null){
          handler = new ItemStackHandler(3);
      }

        return handler;
    }

    @Nonnull
    @Override
    public<T>LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side){

        if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY){
            return LazyOptional.of(() -> (T) getHandler());
        }
        return super.getCapability(cap, side);
    }



//        public static final TileEntityType<ChondriteBlastFurnaceEntity> cbf_entity = register("chondrite_blast_furnace", TileEntityType.Builder.create(ChondriteBlastFurnaceEntity::new, ModBlock.chondrite_blast_furnace));



  //  private static <T extends TileEntity> TileEntityType<T> register(String key, TileEntityType.Builder<T> builder) {
    //    Type<?> type = null;

      //  try {
        //    type = DataFixesManager.getDataFixer().getSchema(DataFixUtils.makeKey(SharedConstants.getVersion().getWorldVersion())).getChoiceType(TypeReferences.BLOCK_ENTITY, key);
      //  } catch (IllegalArgumentException illegalstateexception) {
       //     if (SharedConstants.developmentMode) {
        //        throw illegalstateexception;
         //   }

      //  }

       // return Registry.register(Registry.BLOCK_ENTITY_TYPE, key, builder.build(type));
   // }


}
