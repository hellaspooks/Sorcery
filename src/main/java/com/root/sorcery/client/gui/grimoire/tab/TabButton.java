package com.root.sorcery.client.gui.grimoire.tab;

import net.voxelindustry.brokkgui.component.GuiNode;
import net.voxelindustry.brokkgui.data.RelativeBindingHelper;
import net.voxelindustry.brokkgui.element.input.GuiToggleButton;
import net.voxelindustry.brokkgui.wrapper.elements.MCTooltip;

public class TabButton extends GuiToggleButton
{
    public TabButton(GuiNode icon, String name)
    {
        addStyleClass("tab");
        setSize(28, 30);

        icon.setSize(18,18);

        addChild(icon);
        RelativeBindingHelper.bindToPos(icon, this, 5, 7);

        setTooltip(MCTooltip.build().line(name).create());
    }
}
