package com.root.sorcery.item.tool;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import com.root.sorcery.setup.ModMaterial;
import com.root.sorcery.setup.ModSetup;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.AxeItem;
import net.minecraft.item.HoeItem;
import net.minecraft.item.Item;
import net.minecraft.item.PickaxeItem;
import net.minecraft.item.ShovelItem;
import net.minecraft.item.ToolItem;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.registries.ObjectHolder;

import javax.annotation.Nullable;
import java.util.Set;

/**
 * Registration for tool items.
 */
public class ModTool
{


    private static final Set<Block> PICKAXE = ImmutableSet.of(Blocks.ACTIVATOR_RAIL, Blocks.COAL_ORE, Blocks.COBBLESTONE, Blocks.DETECTOR_RAIL, Blocks.DIAMOND_BLOCK, Blocks.DIAMOND_ORE, Blocks.POWERED_RAIL, Blocks.GOLD_BLOCK, Blocks.GOLD_ORE, Blocks.ICE, Blocks.IRON_BLOCK, Blocks.IRON_ORE, Blocks.LAPIS_BLOCK, Blocks.LAPIS_ORE, Blocks.MOSSY_COBBLESTONE, Blocks.NETHERRACK, Blocks.PACKED_ICE, Blocks.BLUE_ICE, Blocks.RAIL, Blocks.REDSTONE_ORE, Blocks.SANDSTONE, Blocks.CHISELED_SANDSTONE, Blocks.CUT_SANDSTONE, Blocks.CHISELED_RED_SANDSTONE, Blocks.CUT_RED_SANDSTONE, Blocks.RED_SANDSTONE, Blocks.STONE, Blocks.GRANITE, Blocks.POLISHED_GRANITE, Blocks.DIORITE, Blocks.POLISHED_DIORITE, Blocks.ANDESITE, Blocks.POLISHED_ANDESITE, Blocks.STONE_SLAB, Blocks.SMOOTH_STONE_SLAB, Blocks.SANDSTONE_SLAB, Blocks.PETRIFIED_OAK_SLAB, Blocks.COBBLESTONE_SLAB, Blocks.BRICK_SLAB, Blocks.STONE_BRICK_SLAB, Blocks.NETHER_BRICK_SLAB, Blocks.QUARTZ_SLAB, Blocks.RED_SANDSTONE_SLAB, Blocks.PURPUR_SLAB, Blocks.SMOOTH_QUARTZ, Blocks.SMOOTH_RED_SANDSTONE, Blocks.SMOOTH_SANDSTONE, Blocks.SMOOTH_STONE, Blocks.STONE_BUTTON, Blocks.STONE_PRESSURE_PLATE, Blocks.POLISHED_GRANITE_SLAB, Blocks.SMOOTH_RED_SANDSTONE_SLAB, Blocks.MOSSY_STONE_BRICK_SLAB, Blocks.POLISHED_DIORITE_SLAB, Blocks.MOSSY_COBBLESTONE_SLAB, Blocks.END_STONE_BRICK_SLAB, Blocks.SMOOTH_SANDSTONE_SLAB, Blocks.SMOOTH_QUARTZ_SLAB, Blocks.GRANITE_SLAB, Blocks.ANDESITE_SLAB, Blocks.RED_NETHER_BRICK_SLAB, Blocks.POLISHED_ANDESITE_SLAB, Blocks.DIORITE_SLAB, Blocks.SHULKER_BOX, Blocks.BLACK_SHULKER_BOX, Blocks.BLUE_SHULKER_BOX, Blocks.BROWN_SHULKER_BOX, Blocks.CYAN_SHULKER_BOX, Blocks.GRAY_SHULKER_BOX, Blocks.GREEN_SHULKER_BOX, Blocks.LIGHT_BLUE_SHULKER_BOX, Blocks.LIGHT_GRAY_SHULKER_BOX, Blocks.LIME_SHULKER_BOX, Blocks.MAGENTA_SHULKER_BOX, Blocks.ORANGE_SHULKER_BOX, Blocks.PINK_SHULKER_BOX, Blocks.PURPLE_SHULKER_BOX, Blocks.RED_SHULKER_BOX, Blocks.WHITE_SHULKER_BOX, Blocks.YELLOW_SHULKER_BOX);
    private static final Set<Block> AXE = Sets.newHashSet(Blocks.OAK_PLANKS, Blocks.SPRUCE_PLANKS, Blocks.BIRCH_PLANKS, Blocks.JUNGLE_PLANKS, Blocks.ACACIA_PLANKS, Blocks.DARK_OAK_PLANKS, Blocks.BOOKSHELF, Blocks.OAK_WOOD, Blocks.SPRUCE_WOOD, Blocks.BIRCH_WOOD, Blocks.JUNGLE_WOOD, Blocks.ACACIA_WOOD, Blocks.DARK_OAK_WOOD, Blocks.OAK_LOG, Blocks.SPRUCE_LOG, Blocks.BIRCH_LOG, Blocks.JUNGLE_LOG, Blocks.ACACIA_LOG, Blocks.DARK_OAK_LOG, Blocks.CHEST, Blocks.PUMPKIN, Blocks.CARVED_PUMPKIN, Blocks.JACK_O_LANTERN, Blocks.MELON, Blocks.LADDER, Blocks.SCAFFOLDING, Blocks.OAK_BUTTON, Blocks.SPRUCE_BUTTON, Blocks.BIRCH_BUTTON, Blocks.JUNGLE_BUTTON, Blocks.DARK_OAK_BUTTON, Blocks.ACACIA_BUTTON, Blocks.OAK_PRESSURE_PLATE, Blocks.SPRUCE_PRESSURE_PLATE, Blocks.BIRCH_PRESSURE_PLATE, Blocks.JUNGLE_PRESSURE_PLATE, Blocks.DARK_OAK_PRESSURE_PLATE, Blocks.ACACIA_PRESSURE_PLATE);
    private static final Set<Block> SHOVEL = Sets.newHashSet(Blocks.CLAY, Blocks.DIRT, Blocks.COARSE_DIRT, Blocks.PODZOL, Blocks.FARMLAND, Blocks.GRASS_BLOCK, Blocks.GRAVEL, Blocks.MYCELIUM, Blocks.SAND, Blocks.RED_SAND, Blocks.SNOW_BLOCK, Blocks.SNOW, Blocks.SOUL_SAND, Blocks.GRASS_PATH, Blocks.WHITE_CONCRETE_POWDER, Blocks.ORANGE_CONCRETE_POWDER, Blocks.MAGENTA_CONCRETE_POWDER, Blocks.LIGHT_BLUE_CONCRETE_POWDER, Blocks.YELLOW_CONCRETE_POWDER, Blocks.LIME_CONCRETE_POWDER, Blocks.PINK_CONCRETE_POWDER, Blocks.GRAY_CONCRETE_POWDER, Blocks.LIGHT_GRAY_CONCRETE_POWDER, Blocks.CYAN_CONCRETE_POWDER, Blocks.PURPLE_CONCRETE_POWDER, Blocks.BLUE_CONCRETE_POWDER, Blocks.BROWN_CONCRETE_POWDER, Blocks.GREEN_CONCRETE_POWDER, Blocks.RED_CONCRETE_POWDER, Blocks.BLACK_CONCRETE_POWDER);



    @ObjectHolder("sorcery:wolfram_pickaxe")
    public static ToolItem wolfram_pickaxe;

    @ObjectHolder("sorcery:mythril_pickaxe")
    public static ToolItem mythril_pickaxe;

    @ObjectHolder("sorcery:wolfram_axe")
    public static ToolItem wolfram_axe;

    @ObjectHolder("sorcery:mythril_axe")
    public static ToolItem mythril_axe;

    @ObjectHolder("sorcery:wolfram_shovel")
    public static ToolItem wolfram_shovel;

    @ObjectHolder("sorcery:mythril_shovel")
    public static ToolItem mythril_shovel;

    @ObjectHolder("sorcery:wolfram_hoe")
    public static ToolItem wolfram_hoe;

    @ObjectHolder("sorcery:mythril_hoe")
    public static ToolItem mythril_hoe;


    public static void init(RegistryEvent.Register<Item> event)
    {
        // Pickaxes
        wolfram_pickaxe = toolFactory(ToolType.PICKAXE, ModMaterial.WOLFRAM, "wolfram_pickaxe" , 1.0F, 1.5F, event);
        mythril_pickaxe = toolFactory(ToolType.PICKAXE, ModMaterial.MYTHRIL, "mythril_pickaxe", 2.0F, 2.5F, event);

        // Axes
        wolfram_axe = toolFactory(ToolType.AXE, ModMaterial.WOLFRAM, "wolfram_axe", 2.0f, 1.5f, event);
        mythril_axe = toolFactory(ToolType.AXE, ModMaterial.MYTHRIL, "mythril_axe", 2.5f, 3.0f, event);

        // Shovels
        wolfram_shovel = toolFactory(ToolType.SHOVEL, ModMaterial.WOLFRAM, "wolfram_shovel", 1.0F, 1.0F, event);
        mythril_shovel = toolFactory(ToolType.SHOVEL, ModMaterial.MYTHRIL, "mythril_shovel", 1.5F, 2.0F, event);

        // Hoes
        wolfram_hoe = toolFactory(null, ModMaterial.WOLFRAM, "wolfram_hoe", 0.0F, 1.0F, event);
        mythril_hoe = toolFactory(null, ModMaterial.MYTHRIL, "mythril_hoe", 0.0F, 2.0F, event);
    }

    public static ToolItem toolFactory(@Nullable ToolType toolType, ModMaterial material, String registryName, float attackDamage, float attackSpeed, RegistryEvent.Register<Item> event)
    {
        ToolItem toolItem = null;

        {
            if (toolType == ToolType.PICKAXE)
            {
                toolItem = new ToolMod(attackDamage, attackSpeed, material, PICKAXE, toolType, new PickaxeItem.Properties().group(ModSetup.sorcery));
            }
            else if (toolType == ToolType.AXE)
            {
               toolItem = new ToolMod(attackDamage, attackSpeed, material, AXE, toolType, new AxeItem.Properties().group(ModSetup.sorcery));
            }
            else if (toolType == ToolType.SHOVEL){

                toolItem = new ToolMod(attackDamage, attackSpeed, material, SHOVEL, toolType, new ShovelItem.Properties().group(ModSetup.sorcery));
            }
            else if (toolType == null)
            {
                toolItem = new ToolMod(attackDamage, attackSpeed, material, null, null, new HoeItem.Properties().group(ModSetup.sorcery));
            }
            else
            {
                // Error
            }

        }
        toolItem.setRegistryName(registryName);
        event.getRegistry().register(toolItem);
        return toolItem;

    }





}
