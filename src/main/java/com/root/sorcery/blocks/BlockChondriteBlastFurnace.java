package com.root.sorcery.blocks;

import com.root.sorcery.tile_entities.TileChondriteBlastFurnace;
import net.minecraft.block.AbstractFurnaceBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.container.INamedContainerProvider;
//import net.minecraft.stats.Stats;
import net.minecraft.tileentity.BlastFurnaceTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

public class BlockChondriteBlastFurnace extends AbstractFurnaceBlock {

    public BlockChondriteBlastFurnace() {
        super(Properties.create(Material.IRON).hardnessAndResistance(2.0f).sound(SoundType.METAL));
        this.setRegistryName("chondrite_blast_furnace");
        Registry.register(Registry.BLOCK, "chondrite_blast_furnace", this);

    }


    public TileEntity createNewTileEntity(IBlockReader iBlockReader) {
        return new TileChondriteBlastFurnace();
    }

    protected void interactWith(World world, BlockPos blockPos, PlayerEntity playerEntity) {
        TileEntity lvt_4_1_ = world.getTileEntity(blockPos);
        if (lvt_4_1_ instanceof TileChondriteBlastFurnace) {
            playerEntity.openContainer((INamedContainerProvider)lvt_4_1_);
            // playerEntity.addStat(Stats.INTERACT_WITH_BLAST_FURNACE);
        }

    }
}
