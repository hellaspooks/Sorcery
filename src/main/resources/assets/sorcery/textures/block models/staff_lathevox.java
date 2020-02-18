Stream.of(
Block.makeCuboidShape(0, 10, 0, 32, 16, 16),
Block.makeCuboidShape(1, 4, 1, 31, 10, 15),
Block.makeCuboidShape(0, 0, 0, 32, 4, 16),
Block.makeCuboidShape(26, 16, 1, 30, 18, 15),
Block.makeCuboidShape(2, 16, 1, 6, 18, 15),
Block.makeCuboidShape(2, 18, 5, 6, 20, 11),
Block.makeCuboidShape(26, 18, 5, 30, 20, 11),
Block.makeCuboidShape(26, 20, 5, 30, 22, 7),
Block.makeCuboidShape(26, 20, 9, 30, 22, 11),
Block.makeCuboidShape(2, 20, 5, 6, 22, 7),
Block.makeCuboidShape(2, 20, 9, 6, 22, 11),
Block.makeCuboidShape(8, 17, 0, 24, 17, 16)
).reduce((v1, v2) -> {return VoxelShapes.combineAndSimplify(v1, v2, IBooleanFunction.OR);});