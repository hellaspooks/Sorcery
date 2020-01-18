package com.root.sorcery.client.gui;

import com.mojang.blaze3d.platform.GlStateManager;
import com.root.sorcery.Constants;
import com.root.sorcery.container.EscritorieContainer;
import com.root.sorcery.item.ModItem;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
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

    private ResourceLocation TOME_ABJURATION = new ResourceLocation(Constants.MODID, "textures/items/tomes/abjuration.png");


    private int tabOffsetX = 28;
    private int smallTabOffsetY = 24;
    private int largeTabOffsetY = 28;

    private int schoolIconStartingX = 6;
    private int schoolIconStartingY = 18;



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
        drawSpellPage(partialTicks, mouseX, mouseY, relX, relY);

        // draw tabs
        drawTabs(partialTicks, mouseX, mouseY, relX, relY);

    }

    protected void drawSpellPage(float partialTicks, int mouseX, int mouseY, int relX, int relY)
    {
        if (this.container.spellPage)
        {
            this.minecraft.getTextureManager().bindTexture(SPELL_PAGE);
            this.blit(relX + 9, relY + 9, 0, 0, 104, 70);
        }
    }

    protected void drawTabs(float partialTicks, int mouseX, int mouseY, int relX, int relY)
    {
        TextureManager texManage = this.minecraft.getTextureManager();
        drawTab(texManage, relX, relY, 0, ModItem.TOME_ABJURATION);
        drawTab(texManage, relX, relY, 1, ModItem.TOME_CONJURATION);
        drawTab(texManage, relX, relY, 2, ModItem.TOME_ENCHANTMENT);
        drawTab(texManage, relX, relY, 3, ModItem.TOME_EVOCATION);
        drawTab(texManage, relX, relY, 4, ModItem.TOME_NECROMANCY);
        drawTab(texManage, relX, relY, 5, ModItem.TOME_TRANSMUTATION);

    }

    protected void drawTab(TextureManager texManage, int relX, int relY, int tabNumber, Item schoolTome)
    {
        int tabOffset = tabOffsetX * tabNumber;

        if (tabNumber == this.container.activeTab)
        {
            if (tabNumber == 0)
            {
               texManage.bindTexture(LARGE_TAB_LEFT);
            } else {
                texManage.bindTexture(LARGE_TAB_MID);
            }
            this.blit(relX + tabOffset, relY - largeTabOffsetY, 0, 0, 28, 32);
            this.itemRenderer.renderItemAndEffectIntoGUI(new ItemStack(schoolTome), relX + schoolIconStartingX + tabOffset, relY - schoolIconStartingY - 4);
        } else {
            if (tabNumber == 0)
            {
                texManage.bindTexture(SMALL_TAB_LEFT);
            } else {
                texManage.bindTexture(SMALL_TAB_MID);
            }
            this.blit(relX + tabOffset, relY - smallTabOffsetY, 0, 0, 28, 26);
            this.itemRenderer.renderItemAndEffectIntoGUI(new ItemStack(schoolTome), relX + schoolIconStartingX + tabOffset, relY - schoolIconStartingY);
        }
    }
}
