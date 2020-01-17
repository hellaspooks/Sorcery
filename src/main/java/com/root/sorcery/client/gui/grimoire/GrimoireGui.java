package com.root.sorcery.client.gui.grimoire;

import com.root.sorcery.Constants;
import com.root.sorcery.item.ModItem;
import net.minecraft.item.ItemStack;
import net.voxelindustry.brokkgui.component.GuiNode;
import net.voxelindustry.brokkgui.data.RelativeBindingHelper;
import net.voxelindustry.brokkgui.element.input.GuiToggleGroup;
import net.voxelindustry.brokkgui.gui.BrokkGuiScreen;
import net.voxelindustry.brokkgui.paint.Texture;
import net.voxelindustry.brokkgui.panel.GuiAbsolutePane;

import java.util.List;

import static java.util.Collections.emptyList;

public class GrimoireGui extends BrokkGuiScreen implements IGuiTab
{
    public static final float TAB_HEIGHT = 30;
    public static final float GUI_WIDTH  = 252;
    public static final float GUI_HEIGHT = 219;

    private static final Texture BACKGROUND = new Texture(Constants.MODID + ":textures/gui/grimoire/window.png");

    protected GuiAbsolutePane tabHeaders;
    protected GuiAbsolutePane body;
    private   GuiAbsolutePane mainPanel;

    private final IGuiTab[] tabs;

    private IGuiTab lastTab = null;

    public GrimoireGui()
    {
        super(GUI_WIDTH, GUI_HEIGHT);

        mainPanel = new GuiAbsolutePane();
        setMainPanel(mainPanel);

        body = new GuiAbsolutePane();
        body.setBackgroundTexture(getBackgroundTexture());
        mainPanel.addChild(body, 0, TAB_HEIGHT);

        // Setup tabs last
        tabs = new IGuiTab[]{new HermeticsTab()};
        setupTabs();

        addStylesheet("/assets/" + Constants.MODID + "/css/tabheader.css");
        addStylesheet("/assets/" + Constants.MODID + "/css/grimoire.css");
    }

    @Override
    public void initGui()
    {
        super.initGui();

        if (lastTab == null)
            refreshOffset(getTabOffsetX());
    }

    protected Texture getBackgroundTexture()
    {
        return BACKGROUND;
    }

    private void refreshSize(float width, float height, float xOffset)
    {
        body.setSize(width - xOffset, height);
        setSize(width, height + TAB_HEIGHT);

        mainPanel.setChildPos(tabHeaders, xOffset, 0);
        body.setxTranslate(xOffset);
        setxOffset((int) -xOffset / 2);
    }

    public void refreshOffset(float xOffset)
    {
        refreshSize(GUI_WIDTH + xOffset, GUI_HEIGHT, xOffset);
    }

    @Override
    public GuiAbsolutePane getMainPanel()
    {
        return mainPanel;
    }

    @Override
    public List<GuiNode> getElements()
    {
        return emptyList();
    }

    @Override
    public ItemStack getIconStack()
    {
        return new ItemStack(ModItem.carnelian);
    }

    @Override
    public String getName()
    {
        return Constants.MODID + ".gui.grimoire.title";
    }

    public IGuiTab getCurrentTab()
    {
        return lastTab == null ? this : lastTab;
    }

    private void setupTabs()
    {
        tabHeaders = new GuiAbsolutePane();
        tabHeaders.setSize(176, 30);

        GuiToggleGroup tabHeaderGroup = new GuiToggleGroup();

        TabButton mainTabButton = new TabButton(getIcon(), getName());
        mainTabButton.setToggleGroup(tabHeaderGroup);
        tabHeaderGroup.setSelectedButton(mainTabButton);
        tabHeaders.addChild(mainTabButton, 0, 4);

        mainTabButton.setOnSelectEvent(e ->
        {
            if (!e.isSelected())
                return;
            getElements().forEach(node -> node.setVisible(true));
            lastTab.getElements().forEach(node -> node.setVisible(false));

            if (lastTab.getTabOffsetX() != getTabOffsetX())
                refreshOffset(getTabOffsetX());
            lastTab = null;
        });

        for (int i = 0; i < tabs.length; i++)
        {
            TabButton tabButton = new TabButton(tabs[i].getIcon(), tabs[i].getName());
            tabButton.setToggleGroup(tabHeaderGroup);
            tabs[i].setButton(tabButton);

            int finalIndex = i;
            tabButton.setOnSelectEvent(e ->
            {
                if (!e.isSelected())
                    return;

                IGuiTab newTab = tabs[finalIndex];

                if (lastTab == null)
                {
                    getElements().forEach(node -> node.setVisible(false));

                    if (newTab.getTabOffsetX() != getTabOffsetX())
                        refreshOffset(newTab.getTabOffsetX());
                }
                else
                {
                    lastTab.getElements().forEach(node -> node.setVisible(false));

                    if (newTab.getTabOffsetX() != lastTab.getTabOffsetX())
                        refreshOffset(newTab.getTabOffsetX());
                }
                newTab.getElements().forEach(node -> node.setVisible(true));

                lastTab = newTab;
            });

            tabHeaders.addChild(tabButton, (i + 1) * 28, 4);

            tabs[i].getElements().forEach(element ->
            {
                mainPanel.addChild(element, 0, 0);
                RelativeBindingHelper.bindToPos(element, body);
            });

            RelativeBindingHelper.bindSizeRelative((GuiNode) tabs[i], body, 1, 1);
            tabs[i].getElements().forEach(node -> node.setVisible(false));
        }
        tabHeaderGroup.setAllowNothing(false);

        mainPanel.addChild(tabHeaders);
    }
}
