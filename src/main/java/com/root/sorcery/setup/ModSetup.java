package com.root.sorcery.setup;

import com.root.sorcery.blocks.ModBlock;
import com.root.sorcery.items.ModItem;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.loot.LootEntry;
import net.minecraft.world.storage.loot.LootPool;
import net.minecraft.world.storage.loot.LootTable;
import net.minecraftforge.event.LootTableLoadEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.network.FMLPlayMessages;

import java.util.Random;

public class ModSetup {

    public static ItemGroup sorcery = new ItemGroup("Sorcery"){
        @Override
        public ItemStack createIcon(){
            return new ItemStack(ModItem.chondrite_chunk);

        }

    };


        public void init(){

        }

}
