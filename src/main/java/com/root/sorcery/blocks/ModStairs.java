package com.root.sorcery.blocks;

import com.root.sorcery.setup.ModSetup;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.StairsBlock;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.util.registry.Registry;

public class ModStairs extends StairsBlock {


    protected ModStairs(String registryName, BlockState state, Properties properties) {
        super(state, properties);
        this.setRegistryName(registryName);
        Registry.register(Registry.BLOCK, registryName, this);

    }


    public static ModStairs chondrite_brick_stairs;
    public static BlockItem chondrite_brick_stairs_block;

    public static void init(){
        chondrite_brick_stairs = new ModStairs("chondrite_brick_stairs", ModBlock.chondrite_bricks.getDefaultState(), Block.Properties.from(ModBlock.chondrite_bricks));
        chondrite_brick_stairs_block = (BlockItem) new BlockItem(chondrite_brick_stairs, new Item.Properties().group(ModSetup.sorcery)).setRegistryName("chondrite_brick_stairs_block");
        Registry.register(Registry.ITEM, "chondrite_bricks_block", chondrite_brick_stairs_block);
    }
}
