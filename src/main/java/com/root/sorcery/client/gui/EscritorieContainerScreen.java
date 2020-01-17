package com.root.sorcery.client.gui;

import com.mojang.blaze3d.platform.GlStateManager;
import com.root.sorcery.Constants;
import com.root.sorcery.container.EscritorieContainer;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

public class EscritorieContainerScreen extends ContainerScreen<EscritorieContainer>
{
    private ResourceLocation BG = new ResourceLocation(Constants.MODID, "textures/gui/blocks/escritorie_bg.png");
    private ResourceLocation LARGE_BUTTON_DARK = new ResourceLocation(Constants.MODID, "textures/gui/blocks/escritorie_large_button_dark.png");
    private ResourceLocation LARGE_BUTTON_LIGHT = new ResourceLocation(Constants.MODID, "textures/gui/blocks/escritorie_large_button_light.png");
    private ResourceLocation LARGE_TAB_LEFT = new ResourceLocation(Constants.MODID, "textures/gui/blocks/escritorie_large_tab_left.png");
    private ResourceLocation LARGE_TAB_MID = new ResourceLocation(Constants.MODID, "textures/gui/blocks/escritorie_large_tab_mid.png");
    private ResourceLocation LARGE_TAB_RIGHT = new ResourceLocation(Constants.MODID, "textures/gui/blocks/escritorie_large_tab_right.png");
    private ResourceLocation PROGRESS = new ResourceLocation(Constants.MODID, "textures/gui/blocks/escritorie_progress.png");
    private ResourceLocation SMALL_BUTTON_DARK = new ResourceLocation(Constants.MODID, "textures/gui/blocks/escritorie_small_button_dark.png");
    private ResourceLocation SMALL_BUTTON_LIGHT = new ResourceLocation(Constants.MODID, "textures/gui/blocks/escritorie_small_button_light.png");
    private ResourceLocation SMALL_TAB_LEFT = new ResourceLocation(Constants.MODID, "textures/gui/blocks/escritorie_small_tab_left.png");
    private ResourceLocation SMALL_TAB_MID = new ResourceLocation(Constants.MODID, "textures/gui/blocks/escritorie_small_tab_mid.png");
    private ResourceLocation SMALL_TAB_RIGHT = new ResourceLocation(Constants.MODID, "textures/gui/blocks/escritorie_small_tab_right.png");
    private ResourceLocation SPELL_PAGE = new ResourceLocation(Constants.MODID, "textures/gui/blocks/escritorie_spell_page.png");


    private boolean spellPage = true;
    private int activeTab = 0;

    private int tabOffsetX = 28;
    private int smallTabOffsetY = 24;
    private int largeTabOffsetY = 26;



    public EscritorieContainerScreen(EscritorieContainer screenContainer, PlayerInventory inv, ITextComponent titleIn)
    {
        super(screenContainer, inv, titleIn);
    }

    @Override
    public void render(int mouseX, int mouseY, float partialTicks)
    {
        this.renderBackground();
        super.render(mouseX, mouseY, partialTicks);
        this.renderHoveredToolTip(mouseX, mouseY);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
    {

        // draw background
        GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.minecraft.getTextureManager().bindTexture(BG);
        int relX = (this.width - this.xSize) / 2;
        int relY = (this.height - this.ySize) / 2;
        this.blit(relX, relY, 0, 0, this.xSize, this.ySize);

        // draw spell page
        if (spellPage)
        {
            this.minecraft.getTextureManager().bindTexture(SPELL_PAGE);
            this.blit(relX + 9, relY + 9, 0, 0, 104, 70);
        }

        //draw tabs
        drawTabs(partialTicks, mouseX, mouseY, relX, relY);

        //draw active tab
        drawActiveTab(partialTicks, mouseX, mouseY, relX, relY);



    }

    protected void drawTabs(float partialTicks, int mouseX, int mouseY, int relX, int relY)
    {
        // left most tab
        this.minecraft.getTextureManager().bindTexture(SMALL_TAB_LEFT);
        this.blit(relX, relY - smallTabOffsetY, 0, 0, 28, 26);
        // all the rest
        this.minecraft.getTextureManager().bindTexture(SMALL_TAB_MID);
        this.blit(relX + tabOffsetX, relY - smallTabOffsetY, 0, 0, 28, 26);
        this.blit(relX + tabOffsetX * 2, relY - smallTabOffsetY, 0, 0, 28, 26);
        this.blit(relX + tabOffsetX * 3, relY - smallTabOffsetY, 0, 0, 28, 26);
        this.blit(relX + tabOffsetX * 4, relY - smallTabOffsetY, 0, 0, 28, 26);
        this.blit(relX + tabOffsetX * 5, relY - smallTabOffsetY, 0, 0, 28, 26);
    }

    protected void drawActiveTab(float partialTicks, int mouseX, int mouseY, int relX, int relY)
    {
        if (activeTab == 0)
        {
            this.minecraft.getTextureManager().bindTexture(LARGE_TAB_LEFT);
        } else
        {
            this.minecraft.getTextureManager().bindTexture(LARGE_TAB_MID);
        }
        this.blit(relX + tabOffsetX * activeTab, relY - largeTabOffsetY, 0, 0, 28, 32);
    }

}
