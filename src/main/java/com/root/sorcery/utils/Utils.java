package com.root.sorcery.utils;

import net.minecraft.entity.MoverType;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import static net.minecraft.block.Block.spawnAsEntity;

public class Utils {

    public static void dropItemInWorld(BlockPos source, ItemStack stack, World world) {
        if (stack == null || stack.isEmpty())
            return;

        spawnAsEntity(world, source, stack);
    }

    public static int getIndexOfMainHand(PlayerEntity player) {
        ItemStack mainHandItem = player.getHeldItemMainhand();
        int index = -1;

        for (int i = 0; i < 9; i++) {
            if (mainHandItem == player.inventory.getStackInSlot(i))
                index = i;
        }

        return index;
    }
}
