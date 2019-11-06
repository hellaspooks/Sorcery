package com.root.sorcery.client.event;

import com.mojang.blaze3d.platform.GlStateManager;
import com.root.sorcery.Constants;
import com.root.sorcery.arcana.IArcanaStorage;
import com.root.sorcery.item.PortableArcanaItem;
import com.root.sorcery.item.SpellcastingItem;
import com.root.sorcery.utils.Utils;
import net.minecraft.client.Minecraft;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.lwjgl.opengl.GL11;


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
        ItemStack heldItemStack = mc.player.getHeldItemMainhand();
        Item heldItem = heldItemStack.getItem();


        try
        {
            if (heldItem == null || !(heldItem instanceof SpellcastingItem || heldItem instanceof PortableArcanaItem))
            {
                return;
            }

            IArcanaStorage arcanaSource = Utils.getArcanaCap(heldItemStack);

            if (arcanaSource == null)
            {
                return;
            }


            int posX = 5;
            int posY = mc.mainWindow.getScaledHeight() - 10;

            String arcanaString = String.format("%.2f / %d", ((double)arcanaSource.getArcanaStored())/100, arcanaSource.getMaxArcanaStored()/100);

            mc.textureManager.bindTexture(barTexture);


            GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);

            GlStateManager.enableAlphaTest();
            GlStateManager.enableBlend();
            GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

            // Background
            mc.ingameGUI.blit(posX, posY, 0, 0, 91,5);

            int barWidth =(int)((((double)arcanaSource.getArcanaStored())/((double)arcanaSource.getMaxArcanaStored())) * 91.0);

            // Foreground
            mc.ingameGUI.blit(posX, posY, 0, 5, barWidth, 5);

            // Overlay
            mc.ingameGUI.blit(posX, posY, 0, 10, 91,5);

            mc.fontRenderer.drawString(arcanaString, posX + 20, posY -10, 16777215);

            GlStateManager.disableBlend();
            GlStateManager.disableAlphaTest();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }

    }



}
