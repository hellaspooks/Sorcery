package com.root.sorcery.setup;

import com.root.sorcery.container.Containers;
import com.root.sorcery.entity.ModEntity;
import com.root.sorcery.entity.projectile.FireboltEntity;
import com.root.sorcery.entity.projectile.SpellProjectileEntity;
import com.root.sorcery.entity.ToadEntity;
import com.root.sorcery.entity.renderer.SpellProjectileRenderer;
import com.root.sorcery.entity.renderer.ToadRenderer;
import com.root.sorcery.keybinding.KeyBindings;
import com.root.sorcery.keybinding.KeyInputHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import net.minecraftforge.client.model.obj.OBJLoader;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.RenderingRegistry;

public class ClientProxy implements IProxy
{

    @Override
    public void init()
    {
        Containers.registerScreens();

        MinecraftForge.EVENT_BUS.register(new KeyInputHandler());
        KeyBindings.init();

        RenderingRegistry.registerEntityRenderingHandler(ModEntity.TOAD, ToadRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(ModEntity.SPELL_PROJECTILE, SpellProjectileRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(ModEntity.FIREBOLT, SpellProjectileRenderer::new);
    }

    @Override
    public World getClientWorld()
    {
        return Minecraft.getInstance().world;
    }

    @Override
    public PlayerEntity getClientPlayer()
    {
        return Minecraft.getInstance().player;
    }
}
