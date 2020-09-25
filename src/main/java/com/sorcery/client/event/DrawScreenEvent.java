package com.sorcery.client.event;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.GlStateManager;
import com.sorcery.Constants;
import com.sorcery.arcana.IArcanaStorage;
import com.sorcery.item.PortableArcanaItem;
import com.sorcery.item.SpellcastingItem;
import com.sorcery.tileentity.ArcanaStorageTile;
import com.sorcery.utils.Utils;
import net.minecraft.client.Minecraft;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.lwjgl.opengl.GL11;


@Mod.EventBusSubscriber(value = Dist.CLIENT, modid = Constants.MODID)
public class DrawScreenEvent
{

    private static final ResourceLocation barTexture = new ResourceLocation(Constants.MODID, "textures/gui/hud/bar.png");

    public static boolean showSpellSelection = false;

    private static long showSpellSelectionStart = 0;

    private static ResourceLocation selectedSpell = null;

    private static ResourceLocation spellIcon = null;

    private static long showSelectionTicks = 40;


    private static int backgroundWidth = 131;
    private static int backgroundHeight = 38;

    private static int barWidth = 127;
    private static int barHeight = 5;

    private static int barXOffset = 2;
    private static int barYOffset = 2;

    private static int barTexXOffset = 2;
    private static int barTexYOffset = 38;

    private static int overlayWidth = 125;
    private static int overlayHeight = 3;

    private static int overlayXOffset = 3;
    private static int overlayYOffset = 3;

    private static int overlayTexXOffset = 3;
    private static int overlayTexYOffset = 43;

    private static int spellIconXOffset = 3;
    private static int spellIconYOffset = 9;

    private static int spellIconWidth = 26;
    private static int spellIconHeight = 26;




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
        MatrixStack matrixStack = event.getMatrixStack();


        try
        {
            if (mc.player.isSneaking())
            {
                Vector3d playerPos = mc.player.getEyePosition(1.0f);
                Vector3d endVec = playerPos.add(mc.player.getLook(1.0f).mul(16, 16, 16));
                BlockRayTraceResult rtr =  mc.world.rayTraceBlocks(new RayTraceContext(playerPos, endVec, RayTraceContext.BlockMode.COLLIDER, RayTraceContext.FluidMode.NONE, mc.player.getEntity()));
                BlockPos pos = rtr.getPos();
                TileEntity tile = mc.world.getTileEntity(pos);
                if (tile instanceof ArcanaStorageTile)
                {
                    int arcanaMax = ((ArcanaStorageTile)tile).getMaxArcana();
                    int currentArcana = ((ArcanaStorageTile)tile).getStoredArcana();
                    String monoString = String.format("%.2f/%.2f", ((float)currentArcana) / 100, ((float)arcanaMax) / 100);

                    GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);
                    GlStateManager.enableAlphaTest();
                    GlStateManager.enableBlend();
                    GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

                    mc.fontRenderer.drawString(matrixStack, monoString, 44, 34, 16777215);
                }
            }

            if (heldItem == null || !(heldItem instanceof SpellcastingItem || heldItem instanceof PortableArcanaItem))
            {
                return;
            }

            IArcanaStorage arcanaSource = Utils.getArcanaCap(heldItemStack);

            if (arcanaSource == null)
            {
                return;
            }


            int posX = 4;
            int posY = 4;

            String arcanaString = String.format("%.2f / %d", ((double)arcanaSource.getArcanaStored())/100, arcanaSource.getMaxArcanaStored()/100);

            mc.textureManager.bindTexture(barTexture);


            GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);

            GlStateManager.enableAlphaTest();
            GlStateManager.enableBlend();
            GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

            // Blits take the form placementX, placementY, texture start x, texture start y, tex width, tex height

            // Background
            mc.ingameGUI.blit(matrixStack, posX, posY, 0, 0, backgroundWidth, backgroundHeight);

            int barLength =(int)((((double)arcanaSource.getArcanaStored())/((double)arcanaSource.getMaxArcanaStored())) * barWidth);

            // Foreground
            mc.ingameGUI.blit(matrixStack, posX + barXOffset, posY + barYOffset, barTexXOffset, barTexYOffset, barLength, barHeight);

            // Overlay
            mc.ingameGUI.blit(matrixStack, posX + overlayXOffset, posY + overlayYOffset, overlayTexXOffset, overlayTexYOffset, overlayWidth, overlayHeight);

            // Numbers
            mc.fontRenderer.drawString(matrixStack, arcanaString, posX + 40, posY + 20, 16777215);

            if (spellIcon != null)
            {
                // Spell Icon
                mc.getTextureManager().bindTexture(spellIcon);
                mc.ingameGUI.blit(matrixStack, posX + spellIconXOffset, posY + spellIconYOffset, 0, 0, spellIconWidth, spellIconHeight, 26, 26);
            }

            if (showSpellSelection)
            {
                long tDelta = mc.world.getGameTime() - showSpellSelectionStart;
                if (tDelta < showSelectionTicks)
                {
                    mc.fontRenderer.drawStringWithShadow(matrixStack, selectedSpell.toString(), posX + 40 , posY + 30, 16777215);

                } else {
                    showSpellSelection = false;
                }
            }

            GlStateManager.disableBlend();
            GlStateManager.disableAlphaTest();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }

    }

    public static void setShowSpellSelection(boolean bool)
    {
        showSpellSelection = bool;
    }

    public static void setSelectedSpell(ResourceLocation spell)
    {
        selectedSpell = spell;
        spellIcon = new ResourceLocation(Constants.MODID, "textures/spells/" + spell.getPath() + ".png");
        showSpellSelectionStart = Minecraft.getInstance().world.getGameTime();
    }
}
