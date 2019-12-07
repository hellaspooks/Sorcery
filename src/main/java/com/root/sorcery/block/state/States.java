package com.root.sorcery.block.state;

import net.minecraft.state.BooleanProperty;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.IntegerProperty;

public class States
{
    public static final EnumProperty<CrystalColor> CRYSTAL_COLOR = EnumProperty.create("color", CrystalColor.class);
    public static final BooleanProperty ACTIVE = BooleanProperty.create("active");
    public static final IntegerProperty NORTH_RUNE = IntegerProperty.create("north_rune", 0, 3);
    public static final IntegerProperty EAST_RUNE = IntegerProperty.create("east_rune", 0, 3);
    public static final IntegerProperty SOUTH_RUNE = IntegerProperty.create("south_rune", 0, 3);
    public static final IntegerProperty WEST_RUNE = IntegerProperty.create("west_rune", 0, 3);
}
