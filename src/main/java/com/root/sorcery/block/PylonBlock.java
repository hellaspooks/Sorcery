package com.root.sorcery.block;

import com.root.sorcery.arcana.ArcanaCapability;
import com.root.sorcery.arcana.IArcanaStorage;
import com.root.sorcery.network.PacketHandler;
import com.root.sorcery.network.packets.ArcanaCapSyncPacket;
import com.root.sorcery.tileentity.PhylacteryTile;
import com.root.sorcery.tileentity.PylonTile;
import com.root.sorcery.utils.Utils;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class PylonBlock extends Block
{
    private static Float hardness = 3.0F;
    private static Float resistance = 6.0F;

    public PylonBlock()
    {
        super(Properties.create(Material.ROCK).hardnessAndResistance( hardness, resistance).sound(SoundType.STONE));
    }

    @Override
    public boolean hasTileEntity(BlockState state)
    {
        return true;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world)
    {
        return new PylonTile();
    }

}
