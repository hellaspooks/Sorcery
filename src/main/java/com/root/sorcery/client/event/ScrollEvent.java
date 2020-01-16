package com.root.sorcery.client.event;


import com.root.sorcery.Constants;
import com.root.sorcery.item.SpellcastingItem;
import com.root.sorcery.keybinding.KeyBindings;
import com.root.sorcery.network.PacketHandler;
import com.root.sorcery.network.packets.KeyPressPacket;
import com.root.sorcery.spellcasting.ISpellcasting;
import com.root.sorcery.utils.Utils;
import net.minecraft.client.Minecraft;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;


@Mod.EventBusSubscriber(value = Dist.CLIENT, modid = Constants.MODID)
public class ScrollEvent
{
    @SubscribeEvent
    public static void onMouseScrollEvent(InputEvent.MouseScrollEvent event)
    {
        Minecraft mc = Minecraft.getInstance();
        ItemStack heldItemStack = mc.player.getHeldItemMainhand();
        Item heldItem = heldItemStack.getItem();

        if (heldItem == null || !(heldItem instanceof SpellcastingItem) || !(KeyBindings.SPELL_SCROLL.isKeyDown()))
        {
            return;
        }

        DrawScreenEvent.setShowSpellSelection(true);

        ISpellcasting spellCap = Utils.getSpellCap(heldItemStack);

        Double delta = event.getScrollDelta();

        if (delta > 0)
        {
            ResourceLocation selectedSpell = spellCap.getNextSpell();
            DrawScreenEvent.setSelectedSpell(selectedSpell);
            PacketHandler.sendToServer(new KeyPressPacket(1));

        } else {
            ResourceLocation selectedSpell = spellCap.getPreviousSpell();
            DrawScreenEvent.setSelectedSpell(selectedSpell);
            PacketHandler.sendToServer(new KeyPressPacket(2));
        }

        event.setCanceled(true);
    }
}
