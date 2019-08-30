package com.root.sorcery.utils;

import net.minecraft.entity.MoverType;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class Utils {

    public static void dropItemInWorld(BlockPos source, PlayerEntity player, ItemStack stack, double speedFactor, World world) {
        if (stack == null || stack.isEmpty())
            return;

        int hitOrientation = player == null ? 0 : MathHelper.floor(player.rotationYaw * 4.0F / 360.0F + 0.5D) & 3;
        double stackCoordX = 0.0D, stackCoordY = 0.0D, stackCoordZ = 0.0D;

        switch (hitOrientation) {
            case 0:
                stackCoordX = source.getX() + 0.5D;
                stackCoordY = source.getY() + 0.5D + 1;
                stackCoordZ = source.getZ() - 0.25D;
                break;
            case 1:
                stackCoordX = source.getX() + 1.25D;
                stackCoordY = source.getY() + 0.5D + 1;
                stackCoordZ = source.getZ() + 0.5D;
                break;
            case 2:
                stackCoordX = source.getX() + 0.5D;
                stackCoordY = source.getY() + 0.5D + 1;
                stackCoordZ = source.getZ() + 1.25D;
                break;
            case 3:
                stackCoordX = source.getX() - 0.25D;
                stackCoordY = source.getY() + 0.5D + 1;
                stackCoordZ = source.getZ() + 0.5D;
                break;
            default:
                break;
        }

        ItemEntity droppedEntity = new ItemEntity(world, stackCoordX, stackCoordY, stackCoordZ, stack);

        if (player != null) {
            Vec3d motion = new Vec3d(player.posX - stackCoordX, player.posY - stackCoordY, player.posZ - stackCoordZ);
            motion.normalize();
            droppedEntity.posX = motion.x;
            droppedEntity.posY = motion.y;
            droppedEntity.posZ = motion.z;
            droppedEntity.move(MoverType.SELF, motion);
        }

        droppedEntity.posX *= speedFactor;
        droppedEntity.posY *= speedFactor;
        droppedEntity.posZ *= speedFactor;

        world.addEntity(droppedEntity);
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
