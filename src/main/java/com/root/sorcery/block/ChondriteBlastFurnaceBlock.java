package com.root.sorcery.block;

import com.root.sorcery.tileentity.ChondriteBlastFurnaceTileEntity;
import net.minecraft.block.AbstractFurnaceBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

public class ChondriteBlastFurnaceBlock extends AbstractFurnaceBlock {

    private static Material material = Material.IRON;
    private static SoundType sound = SoundType.METAL;
    private static Float hardness = 2.0F;
    private static Float resistance = 2.0F;

    public ChondriteBlastFurnaceBlock(String registryName) {
        super(Properties.create(material).hardnessAndResistance(hardness, resistance).sound(sound));
        this.setRegistryName(registryName);

    }


    public TileEntity createNewTileEntity(IBlockReader iBlockReader) {
        return new ChondriteBlastFurnaceTileEntity();
    }

    protected void interactWith(World world, BlockPos blockPos, PlayerEntity playerEntity) {
        TileEntity lvt_4_1_ = world.getTileEntity(blockPos);
        if (lvt_4_1_ instanceof ChondriteBlastFurnaceTileEntity) {
            playerEntity.openContainer((INamedContainerProvider)lvt_4_1_);
            // playerEntity.addStat(Stats.INTERACT_WITH_BLAST_FURNACE);
        }

    }
}
