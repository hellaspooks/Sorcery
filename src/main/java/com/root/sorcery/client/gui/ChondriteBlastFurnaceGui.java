package com.root.sorcery.client.gui;

import com.mojang.blaze3d.platform.GlStateManager;
import com.root.sorcery.tileentity.ChondriteBlastFurnaceTile;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.voxelindustry.steamlayer.container.BuiltContainer;

public class ChondriteBlastFurnaceGui extends BaseGuiContainer<ChondriteBlastFurnaceTile>
{
    private static final ResourceLocation BACKGROUND = new ResourceLocation("sorcery",
            "textures/gui/blocks/chondrite_blastfurnace_background.png");

    public ChondriteBlastFurnaceGui(BuiltContainer container, PlayerInventory inv, ITextComponent title)
    {
        super(container, inv, title);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
    {
        GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.minecraft.getTextureManager().bindTexture(BACKGROUND);
        int xStart = this.guiLeft;
        int yStart = this.guiTop;
        this.blit(xStart, yStart, 0, 0, this.xSize, this.ySize);
        if (getTile().isBurning())
        {
            int k = getBurnLeftScaled();
            this.blit(xStart + 56, yStart + 36 + 12 - k, 176, 12 - k, 14, k + 1);
        }

        int l = getCookProgressionScaled();
        this.blit(xStart + 79, yStart + 34, 176, 14, l + 1, 16);
    }

    public int getBurnLeftScaled()
    {
        int i = getTile().getMaxBurnTime();
        if (i == 0)
        {
            i = 200;
        }

        return getTile().getBurnTimeLeft() * 13 / i;
    }

    public int getCookProgressionScaled()
    {
        int i = getTile().getCookProgression();
        int j = getTile().getMaxCookProgression();
        return j != 0 && i != 0 ? i * 24 / j : 0;
    }
}
