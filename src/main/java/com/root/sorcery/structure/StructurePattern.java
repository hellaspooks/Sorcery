package com.root.sorcery.structure;

import net.minecraft.util.CachedBlockInfo;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

import static java.util.stream.Collectors.toList;
import static net.minecraft.util.math.BlockPos.ZERO;

public class StructurePattern
{
    private Map<BlockPos, Predicate<CachedBlockInfo>> points;

    public StructurePattern(Map<BlockPos, Predicate<CachedBlockInfo>> points)
    {
        this.points = points;
    }

    public List<BlockPos> match(World w, BlockPos pos, Direction direction)
    {
        if (points.entrySet().stream()
                .allMatch(predicateByPos -> predicateByPos.getValue().test(new CachedBlockInfo(w, pos.add(mapPosToDirection(predicateByPos.getKey(), direction)), true))))
            return points.keySet().stream().map(offset -> pos.add(mapPosToDirection(offset, direction))).collect(toList());
        return Collections.emptyList();
    }

    private BlockPos mapPosToDirection(BlockPos pos, Direction direction)
    {
        switch (direction)
        {
            case NORTH:
                return pos;
            case SOUTH:
                return new BlockPos(pos.getX(), pos.getY(), -pos.getZ());
            case WEST:
                return new BlockPos(pos.getZ(), pos.getY(), pos.getX());
            case EAST:
                return new BlockPos(-pos.getZ(), pos.getY(), pos.getX());
        }
        return ZERO;
    }

    public static Builder build()
    {
        return new Builder();
    }

    public static class Builder
    {
        private final Map<BlockPos, Predicate<CachedBlockInfo>> points = new HashMap<>();

        public Builder where(BlockPos pos, Predicate<CachedBlockInfo> predicate)
        {
            points.put(pos, predicate);
            return this;
        }

        public StructurePattern create()
        {
            return new StructurePattern(points);
        }
    }
}