package com.root.sorcery.block;

import com.root.sorcery.setup.ModSetup;
import net.minecraft.block.Block;
import net.minecraft.block.WallBlock;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.util.registry.Registry;

public class ModWall extends WallBlock {
    public ModWall(String registryName, Properties properties) {
        super(properties);
        this.setRegistryName(registryName);
        Registry.register(Registry.BLOCK, registryName, this);
    }


    public static ModWall chondrite_brick_wall;
    public static BlockItem chondrite_brick_wall_block;

    public static void init(){
        chondrite_brick_wall = new ModWall("chondrite_brick_wall", Block.Properties.from(ModBlock.chondrite_bricks));
        chondrite_brick_wall_block = (BlockItem) new BlockItem(chondrite_brick_wall, new Item.Properties().group(ModSetup.sorcery)).setRegistryName("chondrite_brick_wall_block");
        Registry.register(Registry.ITEM, "chondrite_brick_wall_block", chondrite_brick_wall_block);
    }

}
