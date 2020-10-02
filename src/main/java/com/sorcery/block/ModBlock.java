package com.sorcery.block;

import com.sorcery.Constants;
import net.minecraft.block.Block;
import net.minecraft.block.FenceBlock;
import net.minecraft.block.LeavesBlock;
import net.minecraft.block.RotatedPillarBlock;
import net.minecraft.block.OreBlock;
import net.minecraft.block.SlabBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.StairsBlock;
import net.minecraft.block.WallBlock;
import net.minecraft.block.material.Material;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.ObjectHolder;


public class ModBlock
{

    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, Constants.MODID);

    public static void init()
    {
        BLOCKS.register(FMLJavaModLoadingContext.get().getModEventBus());
    }

    // Simple Blocks
    public static final RegistryObject<Block> POLISHED_RUNESTONE = BLOCKS.register("polished_runestone", () -> new Block(Block.Properties.create(Material.ROCK)));
    public static final RegistryObject<Block> RUNESTONE_BRICKS = BLOCKS.register("runestone_bricks", () -> new Block(Block.Properties.create(Material.ROCK)));
    public static final RegistryObject<Block> RUNEWOOD_PLANKS = BLOCKS.register("runewood_planks", () -> new Block(Block.Properties.create(Material.WOOD)));

    // Runestone Variations

    public static final RegistryObject<Block> RUNESTONE_BRICK_STAIRS = BLOCKS.register("runestone_brick_stairs", () -> new StairsBlock(() -> RUNESTONE_BRICKS.get().getDefaultState(), Block.Properties.create(Material.ROCK)));
    public static final RegistryObject<Block> RUNESTONE_BRICK_SLAB = BLOCKS.register("runestone_brick_slab", () -> new SlabBlock(Block.Properties.create(Material.ROCK)));
    public static final RegistryObject<Block> RUNESTONE_BRICK_WALL = BLOCKS.register("runestone_brick_wall", () -> new WallBlock(Block.Properties.create(Material.ROCK)));

    // Runewood Variations

    public static final RegistryObject<Block> RUNEWOOD_PLANK_STAIRS = BLOCKS.register("runewood_plank_stairs", () -> new StairsBlock(() -> RUNEWOOD_PLANKS.get().getDefaultState(), Block.Properties.create(Material.WOOD)));
    public static final RegistryObject<Block> RUNEWOOD_PLANK_SLAB = BLOCKS.register("runewood_plank_slab", () -> new SlabBlock(Block.Properties.create(Material.WOOD)));
    public static final RegistryObject<Block> RUNEWOOD_PLANK_FENCE = BLOCKS.register("runewood_plank_fence", () -> new FenceBlock(Block.Properties.create(Material.WOOD)));

    // Runestones

    public static final RegistryObject<Block> CHISELED_RUNESTONE = BLOCKS.register("chiseled_runestone", () -> new RunestoneBlock(new ResourceLocation(Constants.MODID, "monolith_chiseled")));
    public static final RegistryObject<Block> DARK_RUNESTONE = BLOCKS.register("dark_runestone", () -> new RunestoneBlock(new ResourceLocation(Constants.MODID, "monolith_dark")));
    public static final RegistryObject<Block> LAPIS_RUNESTONE = BLOCKS.register("lapis_runestone", () -> new RunestoneBlock(new ResourceLocation(Constants.MODID, "monolith_lapis")));
    public static final RegistryObject<Block> SOLAR_RUNESTONE = BLOCKS.register("solar_runestone", () -> new RunestoneBlock(new ResourceLocation(Constants.MODID, "monolith_lunar")));
    public static final RegistryObject<Block> LUNAR_RUNESTONE = BLOCKS.register("lunar_runestone", () -> new RunestoneBlock(new ResourceLocation(Constants.MODID, "monolith_solar")));

    // non simple blocks
    public static final RegistryObject<Block> RUNEWOOD_LOG = BLOCKS.register("runewood_log", () -> new RunewoodLogBlock());
    public static final RegistryObject<Block> STRIPPED_RUNEWOOD_LOG = BLOCKS.register("stripped_runewood_log", () -> new StrippedRunewoodLogBlock());
    public static final RegistryObject<Block> RUNEWOOD_SAPLING = BLOCKS.register("runewood_sapling", () -> new RunewoodSaplingBlock());
    public static final RegistryObject<Block> RUNEWOOD_LEAVES = BLOCKS.register("runewood_leaves", () -> new LeavesBlock(Block.Properties.create(Material.LEAVES)));

    // Decor
    public static final RegistryObject<Block> WOLFRAM_LANTERN = BLOCKS.register("wolfram_lantern", () -> new WolframLanternBlock());

    // Monoliths
    public static final RegistryObject<Block> MONOLITH_CHISELED = BLOCKS.register("monolith_chiseled", () -> new MonolithBlock());
    public static final RegistryObject<Block> MONOLITH_DARK = BLOCKS.register("monolith_dark", () -> new DarkMonolithBlock());
    public static final RegistryObject<Block> MONOLITH_LAPIS = BLOCKS.register("monolith_lapis", () -> new LapisMonolithBlock());
    public static final RegistryObject<Block> MONOLITH_LUNAR = BLOCKS.register("monolith_lunar", () -> new LunarMonolithBlock());
    public static final RegistryObject<Block> MONOLITH_SOLAR = BLOCKS.register("monolith_solar", () -> new SolarMonolithBlock());


    // Other Tiles
    public static final RegistryObject<Block> PYLON = BLOCKS.register("pylon", () -> new PylonBlock());

}
