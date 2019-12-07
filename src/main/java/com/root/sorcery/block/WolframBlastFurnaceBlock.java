package com.root.sorcery.block;

import com.root.sorcery.tileentity.WolframBlastFurnaceTile;
import net.minecraft.block.AbstractFurnaceBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

public class WolframBlastFurnaceBlock extends AbstractFurnaceBlock {

    private static Float hardness = 2.0F;
    private static Float resistance = 2.0F;

    public WolframBlastFurnaceBlock() {
        super(Properties
                .create(Material.IRON)
                .hardnessAndResistance(hardness, resistance)
                .sound(SoundType.METAL));
    }

    @Override
    public TileEntity createNewTileEntity(IBlockReader world)
    {
        return new WolframBlastFurnaceTile();
    }

    @Override
    protected void interactWith(World world, BlockPos pos, PlayerEntity player)
    {
        NetworkHooks.openGui((ServerPlayerEntity) player, (INamedContainerProvider) world.getTileEntity(pos), pos);
    }
}
