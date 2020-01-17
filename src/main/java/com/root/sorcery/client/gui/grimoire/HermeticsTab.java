package com.root.sorcery.client.gui.grimoire;

import com.root.sorcery.Constants;
import com.root.sorcery.item.ModItem;
import lombok.Getter;
import net.minecraft.item.ItemStack;
import net.voxelindustry.brokkgui.component.GuiNode;
import net.voxelindustry.brokkgui.panel.GuiAbsolutePane;

import java.util.ArrayList;
import java.util.List;

public class HermeticsTab extends GuiAbsolutePane implements IGuiTab
{
    @Getter
    private final List<GuiNode> elements = new ArrayList<>();

    public HermeticsTab()
    {
        elements.add(this);
    }

    @Override
    public ItemStack getIconStack()
    {
        return new ItemStack(ModItem.geode);
    }

    @Override
    public String getName()
    {
        return Constants.MODID + ".gui.grimoire.hermetics.title";
    }
}
