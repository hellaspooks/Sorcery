package com.root.sorcery.keybinding;

import com.root.sorcery.network.PacketHandler;
import com.root.sorcery.network.packets.KeyPressPacket;
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
        // For single key-press checks, check on release to avoid duplication
        if (event.getAction() == GLFW_RELEASE)
        {
            // Input for use in checks below
            InputMappings.Input keyInput = InputMappings.getInputByCode(event.getKey(), event.getScanCode());

            // Add checks for keypresses here
            if (KeyBindings.CYCLE_SPELL_KEY.isActiveAndMatches(keyInput))
            {
                    PacketHandler.sendToServer(new KeyPressPacket(1));
            }
        }
    }
}
