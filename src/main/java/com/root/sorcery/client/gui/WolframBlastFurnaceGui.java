package com.root.sorcery.client.gui;

import com.mojang.blaze3d.platform.GlStateManager;
import com.root.sorcery.tileentity.WolframBlastFurnaceTile;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.voxelindustry.steamlayer.container.BuiltContainer;

public class WolframBlastFurnaceGui extends BaseGuiContainer<WolframBlastFurnaceTile>
{
    private static final ResourceLocation BACKGROUND = new ResourceLocation("sorcery",
            "textures/gui/blocks/wolfram_blastfurnace_background.png");

    public WolframBlastFurnaceGui(BuiltContainer container, PlayerInventory inv, ITextComponent title)
    {
        super(container, inv, title);

        xSize = 184;
        ySize = 174;
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
    {
        GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        minecraft.getTextureManager().bindTexture(BACKGROUND);
        int xStart = guiLeft;
        int yStart = guiTop;
        blit(xStart, yStart, 0, 0, xSize, ySize);
        if (getTile().isBurning())
        {
            int k = getBurnLeftScaled();
            blit(xStart + 58, yStart + 38 + 12 - k, 184, 12 - k, 14, k + 1);
        }

        int l = getCookProgressionScaled();
        blit(xStart + 81, yStart + 36, 184, 16, l + 1, 16);
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
