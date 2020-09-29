package com.sorcery.client.event.gui.inventory;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import com.sorcery.inventory.SpellbookContainer;
import net.minecraft.client.gui.IHasContainer;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class SpellbookScreen extends ContainerScreen<SpellbookContainer> implements IHasContainer<SpellbookContainer> {
    /** The ResourceLocation containing the chest GUI texture. */
    private static final ResourceLocation CHEST_GUI_TEXTURE = new ResourceLocation("textures/gui/container/generic_54.png");
    /** Window height is calculated with these values; the more rows, the higher */
    private final int inventoryRows;

    public SpellbookScreen(SpellbookContainer container, PlayerInventory playerInventory, ITextComponent title) {
        super(container, playerInventory, title);
        this.passEvents = false;
        int i = 222;
        int j = 114;
        this.inventoryRows = container.getNumRows();
        this.ySize = 114 + this.inventoryRows * 18;
        this.playerInventoryTitleY = this.ySize - 94;
    }

    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(matrixStack);
        super.render(matrixStack, mouseX, mouseY, partialTicks);
        this.renderHoveredTooltip(matrixStack, mouseX, mouseY);
    }

    protected void drawGuiContainerBackgroundLayer(MatrixStack matrixStack, float partialTicks, int x, int y) {
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.minecraft.getTextureManager().bindTexture(CHEST_GUI_TEXTURE);
        int i = (this.width - this.xSize) / 2;
        int j = (this.height - this.ySize) / 2;
        this.blit(matrixStack, i, j, 0, 0, this.xSize, this.inventoryRows * 18 + 17);
        this.blit(matrixStack, i, j + this.inventoryRows * 18 + 17, 0, 126, this.xSize, 96);
    }
}
