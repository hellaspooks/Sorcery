package com.root.sorcery.block;

import com.root.sorcery.arcana.ArcanaCapability;
import com.root.sorcery.arcana.IArcanaStorage;
import com.root.sorcery.network.PacketHandler;
import com.root.sorcery.network.packets.ArcanaCapSyncPacket;
import com.root.sorcery.structure.StructurePattern;
import com.root.sorcery.tileentity.ReliquaryTile;
import com.root.sorcery.utils.Utils;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.pattern.BlockStateMatcher;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.CachedBlockInfo;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;
import java.util.function.Predicate;

import static com.root.sorcery.block.ModBlock.RELIQUARY;
import static net.minecraft.block.Blocks.BOOKSHELF;
import static net.minecraft.block.Blocks.GLOWSTONE;
import static net.minecraft.block.Blocks.LECTERN;

public class ReliquaryBlock extends Block
{

    private static Float hardness = 3.0F;
    private static Float resistance = 6.0F;

    public static final DirectionProperty FACING = HorizontalBlock.HORIZONTAL_FACING;
    private static StructurePattern RELIQUARY_PATTERN;

    public ReliquaryBlock()
    {
        super(Properties.create(Material.ROCK).hardnessAndResistance( hardness, resistance).sound(SoundType.STONE));

        setDefaultState(stateContainer.getBaseState().with(FACING, Direction.NORTH));
    }

    @Override
    public boolean onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit)
    {
        if (!worldIn.isRemote) {
            TileEntity tile = worldIn.getTileEntity(pos);

            if (tile instanceof ReliquaryTile) {
                IArcanaStorage playerCap = Utils.getArcanaCap(player);
                int arcanaExtracted = ((ReliquaryTile) tile).getArcanaForPlayer();
                playerCap.receiveArcana(arcanaExtracted, false);

                // Sync arcana with client
                ServerPlayerEntity serverPlayer = worldIn.getServer().getPlayerList().getPlayerByUUID(player.getUniqueID());
                PacketHandler.sendToPlayer(serverPlayer, new ArcanaCapSyncPacket(ArcanaCapability.ARCANA.writeNBT(playerCap, null)));

                return true;
            }
        }
        return false;
    }


    @Override
    public int getOpacity(BlockState state, IBlockReader worldIn, BlockPos pos)
    {
        return 0;
    }

    @Override
    public boolean isNormalCube(BlockState state, IBlockReader worldIn, BlockPos pos)
    {
        return false;
    }

    @Override
    public boolean isSolid(BlockState state)
    {
        return false;
    }

    @Override
    public BlockRenderType getRenderType(BlockState state)
    {
        return BlockRenderType.MODEL;
    }

    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context)
    {
        return getDefaultState().with(FACING, context.getPlacementHorizontalFacing().getOpposite());
    }

    @Override
    public BlockState rotate(BlockState state, Rotation rot)
    {
        return state.with(FACING, rot.rotate(state.get(FACING)));
    }

    @Override
    public BlockState mirror(BlockState state, Mirror mirror)
    {
        return state.rotate(mirror.toRotation(state.get(FACING)));
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> stateBuilder)
    {
        stateBuilder.add(FACING);
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
        System.out.println("creating tile entity");
        return new ReliquaryTile();
    }

    public static void checkAndTryPlacement(World w, BlockPos pos, Direction direction)
    {
        List<BlockPos> points = getReliquaryPattern().match(w, pos, direction);

        if (points.isEmpty())
            return;

        points.forEach(point ->
        {
            BlockState previousState = w.getBlockState(point);
            w.setBlockState(point, Blocks.AIR.getDefaultState(), 2);
            w.playEvent(2001, point, Block.getStateId(previousState));
        });

        w.setBlockState(pos, RELIQUARY.getDefaultState().with(FACING, direction.getOpposite()));
    }

    private static StructurePattern getReliquaryPattern()
    {
        if (RELIQUARY_PATTERN == null)
        {
            Predicate<CachedBlockInfo> bookshelfPredicate =
                    CachedBlockInfo.hasState(BlockStateMatcher.forBlock(BOOKSHELF));
            Predicate<CachedBlockInfo> glowstonePredicate =
                    CachedBlockInfo.hasState(BlockStateMatcher.forBlock(GLOWSTONE));
            Predicate<CachedBlockInfo> lecternPredicate = CachedBlockInfo.hasState(BlockStateMatcher.forBlock(LECTERN));

            RELIQUARY_PATTERN = StructurePattern.build()
                    .where(new BlockPos(-1, 0, 0), bookshelfPredicate)
                    .where(new BlockPos(0, 0, 0), lecternPredicate)
                    .where(new BlockPos(1, 0, 0), bookshelfPredicate)
                    .where(new BlockPos(-1, 0, -1), bookshelfPredicate)
                    .where(new BlockPos(1, 0, -1), bookshelfPredicate)
                    .where(new BlockPos(0, 0, -1), glowstonePredicate)
                    .where(new BlockPos(0, 1, -1), glowstonePredicate)
                    .where(new BlockPos(0, 2, -1), glowstonePredicate)
                    .create();
        }
        return RELIQUARY_PATTERN;
    }
}
