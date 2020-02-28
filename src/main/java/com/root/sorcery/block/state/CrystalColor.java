package com.root.sorcery.block.state;

import net.minecraft.util.IStringSerializable;

public enum CrystalColor implements IStringSerializable
{
    NONE("none", "none"),
    BLACK("black", "crystal_nuummite"),
    BLUE("blue", "crystal_chalcedony"),
    GREEN("green", "crystal_serpentine"),
    PURPLE("purple", "crystal_sugilite"),
    RED("red", "crystal_carnelian"),
    YELLOW("yellow", "crystal_jasper");

    private final String name;

    private final String crystalName;

    private CrystalColor(String name, String crystalName) {
        this.name = name;
        this.crystalName = crystalName;
    }

    public String toString() {
        return this.name;
    }

    public String getName()
    {
        return this.name;
    }

    public String getCrystalName()
    {
        return this.crystalName;
    }
}
