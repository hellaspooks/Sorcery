package com.root.sorcery.client.gui;

import com.mojang.blaze3d.platform.GlStateManager;
import com.root.sorcery.Constants;
import com.root.sorcery.container.StaffLatheContainer;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.gui.widget.button.ImageButton;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

public class StaffLatheContainerScreen extends ContainerScreen<StaffLatheContainer> implements Button.IPressable
{

    private ResourceLocation GUI = new ResourceLocation(Constants.MODID, "textures/gui/blocks/staff_lathe_gui.png");
    private ResourceLocation OVERLAY = new ResourceLocation(Constants.MODID, "textures/gui/blocks/staff_lathe_overlay.png");

    private int buttonWidth = 22;
    private int buttonHeight = 22;

    private int buttonTexStartX = 176;
    private int buttonTexStartY = 0;

    private int buttonOffsetY = 22;

    private int craftButtonX = 62;
    private int craftButtonY = 50;

    private int uncraftButtonX = -40;
    private int uncraftButtonY = 50;

    public StaffLatheContainerScreen(StaffLatheContainer screenContainer, PlayerInventory inv, ITextComponent titleIn)
    {
        super(screenContainer, inv, titleIn);
    }

    @Override
    protected void init()
    {
        super.init();
        this.addButton(new ImageButton(this.width / 2 - craftButtonX, this.height / 2 - craftButtonY, buttonWidth, buttonHeight, buttonTexStartX, buttonTexStartY, buttonOffsetY, GUI, button -> {this.craft(button);}));
        this.addButton(new ImageButton(this.width / 2 - uncraftButtonX, this.height / 2 - uncraftButtonY, buttonWidth, buttonHeight, buttonTexStartX + 22, buttonTexStartY, buttonOffsetY, GUI, button -> {this.uncraft(button);}));
    }

    public void craft(Button b)
    {
        System.out.println("Craft button pressed");

    }

    public void uncraft(Button b)
    {
        System.out.println("Craft button pressed");

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
        GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.minecraft.getTextureManager().bindTexture(GUI);
        int relX = (this.width - this.xSize) / 2;
        int relY = (this.height - this.ySize) / 2;
        this.blit(relX, relY, 0, 0, this.xSize, this.ySize);

    }

    @Override
    public void onPress(Button p_onPress_1_)
    {

    }
}
