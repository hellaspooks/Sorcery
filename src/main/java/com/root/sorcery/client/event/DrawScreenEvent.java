package com.root.sorcery.client.event;

import com.mojang.blaze3d.platform.GlStateManager;
import com.root.sorcery.Constants;
import com.root.sorcery.arcana.ArcanaCapability;
import com.root.sorcery.arcana.IArcanaStorage;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.lwjgl.opengl.GL11;
import org.lwjgl.system.CallbackI;


@Mod.EventBusSubscriber(value = Dist.CLIENT, modid = Constants.MODID)
public class DrawScreenEvent
{

    private static final ResourceLocation barTexture = new ResourceLocation(Constants.MODID, "textures/gui/hud/bar.png");

    @SubscribeEvent
    public static void onDrawScreenPost(RenderGameOverlayEvent.Post event)
    {
        if (event.getType() != RenderGameOverlayEvent.ElementType.HOTBAR)
        {
            return;
        }

        Minecraft mc = Minecraft.getInstance();

        IArcanaStorage playerCap = mc.player.getCapability(ArcanaCapability.ARCANA, null).orElseThrow(NullPointerException::new);

        if (playerCap == null)
        {
            return;
        }

        int posX = mc.mainWindow.getScaledWidth() / 2 - 91;
        int posY = mc.mainWindow.getScaledHeight() / 2 - 10;

        mc.textureManager.bindTexture(barTexture);


        GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);

        GlStateManager.enableAlphaTest();
        GlStateManager.enableBlend();
        GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

        // Background
        mc.ingameGUI.blit(posX, posY, 0, 0, 91,5);

        int barWidth =(int)((((double)playerCap.getArcanaStored())/((double)playerCap.getMaxArcanaStored())) * 91.0);

        // Foreground
        mc.ingameGUI.blit(posX, posY, 0, 5, barWidth, 5);

        // Overlay
        mc.ingameGUI.blit(posX, posY, 0, 10, 91,5);

        GlStateManager.disableBlend();
        GlStateManager.disableAlphaTest();

    }



}
