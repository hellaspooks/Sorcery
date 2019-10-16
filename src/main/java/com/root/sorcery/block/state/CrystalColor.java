package com.root.sorcery.block.state;

import net.minecraft.util.IStringSerializable;

public enum CrystalColor implements IStringSerializable
{
    NONE("none"),
    BLACK("black"),
    BLUE("blue"),
    GREEN("green"),
    PURPLE("purple"),
    RED("red"),
    YELLOw("yellow");

    private final String name;

    private CrystalColor(String name) {
        this.name = name;
    }

    public String toString() {
        return this.name;
    }

    public String getName()
    {
        return this.name;
    }
}
