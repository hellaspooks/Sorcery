package com.root.sorcery.setup;

import com.root.sorcery.container.Containers;
import com.root.sorcery.entity.ToadEntity;
import com.root.sorcery.entity.renderer.ToadEntityRenderer;
import com.root.sorcery.keybinding.KeyBindings;
import com.root.sorcery.keybinding.KeyInputHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.world.World;
import net.minecraftforge.client.model.obj.OBJLoader;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.RenderingRegistry;

public class ClientProxy implements IProxy
{

    @Override
    public void init()
    {
        OBJLoader.INSTANCE.addDomain("sorcery");
        Containers.registerScreens();

        MinecraftForge.EVENT_BUS.register(new KeyInputHandler());
        KeyBindings.init();

        RenderingRegistry.registerEntityRenderingHandler(ToadEntity.class, ToadEntityRenderer::new);
    }

    @Override
    public World getClientWorld()
    {
        return Minecraft.getInstance().world;
    }
}
