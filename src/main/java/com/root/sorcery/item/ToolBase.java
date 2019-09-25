package com.root.sorcery.item;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.root.sorcery.setup.ModSetup;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.IItemTier;
import net.minecraft.item.Item;
import net.minecraft.item.ItemUseContext;
import net.minecraft.item.ToolItem;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import net.minecraftforge.common.ToolType;

import javax.annotation.Nullable;
import java.util.Map;
import java.util.Set;

import static com.root.sorcery.item.ModTools.chondrite_hoe;
import static com.root.sorcery.item.ModTools.siderite_hoe;

public class ToolBase extends ToolItem {

    protected static final Map<Block, BlockState> HOE_LOOKUP = Maps.newHashMap(ImmutableMap.of(Blocks.GRASS_BLOCK, Blocks.FARMLAND.getDefaultState(), Blocks.GRASS_PATH, Blocks.FARMLAND.getDefaultState(), Blocks.DIRT, Blocks.FARMLAND.getDefaultState(), Blocks.COARSE_DIRT, Blocks.DIRT.getDefaultState()));

    public ToolBase(@Nullable float attackDamageIn, float attackSpeedIn, IItemTier tier, @Nullable Set<Block> set, @Nullable ToolType type, Item.Properties properties, String registryName) {
        super(attackDamageIn, attackSpeedIn, tier, set, properties.group(ModSetup.sorcery).addToolType(type, tier.getHarvestLevel()));
        this.setRegistryName(registryName);
        Registry.register(Registry.ITEM, registryName, this);


    }

    public ActionResultType onItemUse(ItemUseContext context) {
        if(context.getItem().getItem() == chondrite_hoe.getItem() || context.getItem().getItem() == siderite_hoe.getItem()){
            World world = context.getWorld();
            BlockPos blockpos = context.getPos();
            int hook = net.minecraftforge.event.ForgeEventFactory.onHoeUse(context);
            if (hook != 0) return hook > 0 ? ActionResultType.SUCCESS : ActionResultType.FAIL;
            if (context.getFace() != Direction.DOWN && world.isAirBlock(blockpos.up())) {
                BlockState blockstate = HOE_LOOKUP.get(world.getBlockState(blockpos).getBlock());
                if (blockstate != null) {
                    PlayerEntity playerentity = context.getPlayer();
                    world.playSound(playerentity, blockpos, SoundEvents.ITEM_HOE_TILL, SoundCategory.BLOCKS, 1.0F, 1.0F);
                    if (!world.isRemote) {
                        world.setBlockState(blockpos, blockstate, 11);
                        if (playerentity != null) {
                            context.getItem().damageItem(1, playerentity, (p_220043_1_) -> {
                                p_220043_1_.sendBreakAnimation(context.getHand());
                            });
                        }
                    }

                    return ActionResultType.SUCCESS;
                }
            }

            return ActionResultType.PASS;
        }
        else
            return ActionResultType.PASS;
    }
}
