package com.root.sorcery.setup;

import com.root.sorcery.container.Containers;
import com.root.sorcery.keybinding.KeyBindings;
import com.root.sorcery.keybinding.KeyInputHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.world.World;
import net.minecraftforge.client.model.obj.OBJLoader;
import net.minecraftforge.common.MinecraftForge;

public class ClientProxy implements IProxy
{

    @Override
    public void init()
    {
        OBJLoader.INSTANCE.addDomain("sorcery");
        Containers.registerScreens();

        MinecraftForge.EVENT_BUS.register(new KeyInputHandler());
        KeyBindings.init();
    }

    @Override
    public World getClientWorld()
    {
        return Minecraft.getInstance().world;
    }
}
