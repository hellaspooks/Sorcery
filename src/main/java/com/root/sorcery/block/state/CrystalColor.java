package com.root.sorcery.block.state;

import net.minecraft.util.IStringSerializable;

public enum CrystalColor implements IStringSerializable
{
    NONE("none", "none"),
    BLACK("black", "nuummite"),
    BLUE("blue", "chalcedony"),
    GREEN("green", "serpentine"),
    PURPLE("purple", "sugilite"),
    RED("red", "carnelian"),
    YELLOW("yellow", "jasper");

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
