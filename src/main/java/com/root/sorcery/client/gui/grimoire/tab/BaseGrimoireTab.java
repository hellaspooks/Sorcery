package com.root.sorcery.client.gui.grimoire.tab;

import com.root.sorcery.Constants;
import lombok.Getter;
import net.voxelindustry.brokkgui.component.GuiNode;
import net.voxelindustry.brokkgui.element.pane.ScrollPane;
import net.voxelindustry.brokkgui.panel.GuiAbsolutePane;
import net.voxelindustry.brokkgui.policy.GuiScrollbarPolicy;
import net.voxelindustry.brokkgui.shape.Rectangle;
import net.voxelindustry.brokkgui.sprite.Texture;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseGrimoireTab extends GuiAbsolutePane implements IGuiTab
{
    @Getter
    private final List<GuiNode> elements = new ArrayList<>();

    public BaseGrimoireTab()
    {
        elements.add(this);

        Rectangle background = new Rectangle();
        background.setBackgroundTexture(new Texture(Constants.MODID + ":textures/gui/grimoire/background_" + getTabName() + ".png"));

        background.setSize(234, 154);
        addChild(background, 9, 18);

        Rectangle starfieldRect = new Rectangle();
        starfieldRect.setSize(512, 512);
        starfieldRect.setID("starfield-background");
        ScrollPane starfield = new ScrollPane(starfieldRect);
        starfield.setPannable(true);
        starfield.setScrollable(false);
        starfield.setPanSpeed(2);
        starfield.setScrollXPolicy(GuiScrollbarPolicy.NEVER);
        starfield.setScrollYPolicy(GuiScrollbarPolicy.NEVER);

        starfield.setSize(234, 154);
        addChild(starfield, 9, 18);

        GuiAbsolutePane researchPane = new GuiAbsolutePane();
        researchPane.setSize(256, 256);

        Rectangle researchNodeNeutral = new Rectangle();
        researchNodeNeutral.setSize(24, 24);
        researchNodeNeutral.addStyleClass("research-node");
        researchPane.addChild(researchNodeNeutral, 128 - researchNodeNeutral.getWidth() / 2, 128 - researchNodeNeutral.getHeight() / 2);

        Rectangle researchNodeDarkened = new Rectangle();
        researchNodeDarkened.setSize(24, 24);
        researchNodeDarkened.addStyleClass("research-node");
        researchNodeDarkened.setDisabled(true);
        researchPane.addChild(researchNodeDarkened, 128 + researchNodeDarkened.getWidth(), 128 - researchNodeDarkened.getHeight() / 2);

        Rectangle researchNodeDarkest = new Rectangle();
        researchNodeDarkest.setSize(24, 24);
        researchNodeDarkest.addStyleClass("research-node");
        researchNodeDarkest.addStyleClass("darker");
        researchNodeDarkest.setDisabled(true);
        researchPane.addChild(researchNodeDarkest, 128 + researchNodeDarkened.getWidth() * 2.5F, 128 - researchNodeDarkest.getHeight() / 2);

        ScrollPane research = new ScrollPane(researchPane);
        research.setPannable(true);
        research.setScrollable(false);
        research.setScrollXPolicy(GuiScrollbarPolicy.NEVER);
        research.setScrollYPolicy(GuiScrollbarPolicy.NEVER);
        research.setID("research-panel-outer");
        research.setSize(234, 154);

        addChild(research, 9, 18);
    }

    public abstract String getTabName();

    @Override
    public String getName()
    {
        return Constants.MODID + ".gui.grimoire." + getTabName() + ".title";
    }
}
