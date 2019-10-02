package com.root.sorcery.keybinding;

import com.root.sorcery.network.PacketHandler;
import com.root.sorcery.network.packets.KeyPressPKT;
import net.minecraft.client.util.InputMappings;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static org.lwjgl.glfw.GLFW.GLFW_RELEASE;


@Mod.EventBusSubscriber
public class KeyInputHandler
{
    @SubscribeEvent
    public void onKeyInput(InputEvent.KeyInputEvent event)
    {
        // Only Check on Key Release, so only triggers once.
        if (event.getAction() == GLFW_RELEASE)
        {
            Boolean keyPressed = KeyBindings.cycleSpell.isActiveAndMatches(InputMappings.getInputByCode(event.getKey(), event.getScanCode()));

            if (keyPressed)
            {
                    PacketHandler.sendToServer(new KeyPressPKT());
            }
        }
    }
}
