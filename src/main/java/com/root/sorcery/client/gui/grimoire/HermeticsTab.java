package com.root.sorcery.client.gui.grimoire;

import com.root.sorcery.Constants;
import com.root.sorcery.item.ModItem;
import lombok.Getter;
import net.minecraft.item.ItemStack;
import net.voxelindustry.brokkgui.component.GuiNode;
import net.voxelindustry.brokkgui.element.pane.ScrollPane;
import net.voxelindustry.brokkgui.paint.Texture;
import net.voxelindustry.brokkgui.panel.GuiAbsolutePane;
import net.voxelindustry.brokkgui.policy.GuiScrollbarPolicy;
import net.voxelindustry.brokkgui.shape.Rectangle;

import java.util.ArrayList;
import java.util.List;

public class HermeticsTab extends GuiAbsolutePane implements IGuiTab
{
    @Getter
    private final List<GuiNode> elements = new ArrayList<>();

    public HermeticsTab()
    {
        elements.add(this);

        Rectangle backgroundRect = new Rectangle();
        backgroundRect.setSize(256, 256);
        backgroundRect.setBackgroundTexture(new Texture(Constants.MODID + ":textures/gui/grimoire/background_1.png"));
        ScrollPane background = new ScrollPane(backgroundRect);
        background.setPannable(true);
        background.setScrollable(false);
        background.setPanSpeed(0.25F);
        background.setScrollXPolicy(GuiScrollbarPolicy.NEVER);
        background.setScrollYPolicy(GuiScrollbarPolicy.NEVER);

        background.setSize(234, 154);
        addChild(background, 9, 18);

        GuiAbsolutePane researchPane = new GuiAbsolutePane();
        researchPane.setSize(256, 256);

        Rectangle researchNode = new Rectangle();
        researchNode.setSize(16, 16);
        researchNode.setStyle("border-color: pink; border-width: 1; background-color: white;");
        researchPane.addChild(researchNode, 128 - 8, 128 - 8);

        ScrollPane research = new ScrollPane(researchPane);
        research.setPannable(true);
        research.setScrollable(false);
        research.setScrollXPolicy(GuiScrollbarPolicy.NEVER);
        research.setScrollYPolicy(GuiScrollbarPolicy.NEVER);
        research.setID("research-panel-outer");
        research.setSize(234, 154);

        addChild(research, 9, 18);
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
