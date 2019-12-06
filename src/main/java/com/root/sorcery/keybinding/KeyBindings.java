package com.root.sorcery.keybinding;

import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.client.registry.ClientRegistry;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_K;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_LEFT_CONTROL;

public class KeyBindings
{
    public static final KeyBinding CYCLE_SPELL_KEY = new KeyBinding("key.cycleSpell", GLFW_KEY_K, "key.categories.sorcery");

    public static final KeyBinding SPELL_SCROLL = new KeyBinding("key.spellScroll", GLFW_KEY_LEFT_CONTROL, "key.categories.sorcery");

    public static void init()
    {
        ClientRegistry.registerKeyBinding(CYCLE_SPELL_KEY);
        ClientRegistry.registerKeyBinding(SPELL_SCROLL);
    }
}
