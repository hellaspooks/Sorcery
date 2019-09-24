package com.root.sorcery.block;

import com.root.sorcery.setup.ModSetup;
import net.minecraft.block.Block;
import net.minecraft.block.SlabBlock;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.util.registry.Registry;

public class ModSlab extends SlabBlock {
    public ModSlab(String registryName, Properties properties) {
        super(properties);
        this.setRegistryName(registryName);
        Registry.register(Registry.BLOCK, registryName, this);
    }


    public static ModSlab chondrite_brick_slab;
    public static BlockItem chondrite_brick_slab_block;


    public static void init(){
        chondrite_brick_slab = new ModSlab("chondrite_brick_slab", Block.Properties.from(ModBlock.chondrite_bricks));
        chondrite_brick_slab_block = (BlockItem) new BlockItem(chondrite_brick_slab, new Item.Properties().group(ModSetup.sorcery)).setRegistryName("chondrite_brick_slab_block");
        Registry.register(Registry.ITEM, "chondrite_bricks_block", chondrite_brick_slab_block);
    }
}
