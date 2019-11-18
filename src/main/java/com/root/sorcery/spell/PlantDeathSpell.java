package com.root.sorcery.spell;

import com.root.sorcery.Config;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Collections;
import java.util.IdentityHashMap;
import java.util.Set;
import java.util.stream.Stream;

public class PlantDeathSpell extends Spell
{
    private double spellRange;

    public PlantDeathSpell() {
        super(Config.PLANT_DEATH_SPELL_COST.get());
        this.spellRange = Config.PLANT_DEATH_SPELL_RANGE.get();
    }

    @Override
    public ActionResultType castServer(SpellUseContext context)
    {

        // IdentityHashSet of plant blocks.
        Set<Block> plantBlocks = Collections.newSetFromMap(new IdentityHashMap<>());

        // Grass
        plantBlocks.add(Blocks.GRASS_BLOCK);
        plantBlocks.add(Blocks.GRASS);
        plantBlocks.add(Blocks.TALL_GRASS);
        plantBlocks.add(Blocks.FERN);
        plantBlocks.add(Blocks.LARGE_FERN);

        // Leaves
        plantBlocks.add(Blocks.ACACIA_LEAVES);
        plantBlocks.add(Blocks.BIRCH_LEAVES);
        plantBlocks.add(Blocks.OAK_LEAVES);
        plantBlocks.add(Blocks.SPRUCE_LEAVES);
        plantBlocks.add(Blocks.DARK_OAK_LEAVES);
        plantBlocks.add(Blocks.JUNGLE_LEAVES);

        // Flowers
        plantBlocks.add(Blocks.ROSE_BUSH);
        plantBlocks.add(Blocks.ORANGE_TULIP);
        plantBlocks.add(Blocks.PINK_TULIP);
        plantBlocks.add(Blocks.RED_TULIP);
        plantBlocks.add(Blocks.WHITE_TULIP);
        plantBlocks.add(Blocks.DANDELION);
        plantBlocks.add(Blocks.POPPY);
        plantBlocks.add(Blocks.BLUE_ORCHID);
        plantBlocks.add(Blocks.ALLIUM);
        plantBlocks.add(Blocks.AZURE_BLUET);
        plantBlocks.add(Blocks.OXEYE_DAISY);
        plantBlocks.add(Blocks.CORNFLOWER);
        plantBlocks.add(Blocks.LILY_OF_THE_VALLEY);
        plantBlocks.add(Blocks.SUNFLOWER);
        plantBlocks.add(Blocks.LILAC);
        plantBlocks.add(Blocks.PEONY);

        // Misc
        plantBlocks.add(Blocks.VINE);

        // Extra Data
        World world = context.getWorld();
        PlayerEntity player = context.getPlayer();
        BlockPos playerPos = player.getPosition();
        BlockPos blockStart = playerPos.add(-spellRange, -spellRange, -spellRange);
        BlockPos blockEnd = playerPos.add(spellRange, spellRange + 1, spellRange);

        // List of all blocks in the affected area.
        Stream<BlockPos> AOE = BlockPos.getAllInBox(blockStart, blockEnd);

        AOE.forEach((blockPosInAOE) ->
        {
            Block blockInAOE = world.getBlockState(blockPosInAOE).getBlock();

            if (plantBlocks.contains(blockInAOE) && (blockInAOE == Blocks.GRASS_BLOCK))
            {
                world.setBlockState(blockPosInAOE, Blocks.DIRT.getDefaultState());
            }
            else if (plantBlocks.contains(blockInAOE))
            {
                world.setBlockState(blockPosInAOE, Blocks.AIR.getDefaultState());
            }
        });

        return ActionResultType.SUCCESS;
    }
}
