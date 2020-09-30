package com.sorcery.datagen;

import com.sorcery.block.ModBlock;
import net.minecraft.data.DataGenerator;

public class LootTables extends BaseLootTableProvider {

    public LootTables(DataGenerator dataGeneratorIn) {
        super(dataGeneratorIn);
    }

    @Override
    protected void addTables() {
        lootTables.put(ModBlock.RUNESTONE_BRICKS.get(), createStandardTable("runestone_bricks", ModBlock.RUNESTONE_BRICKS.get()));
    }
}