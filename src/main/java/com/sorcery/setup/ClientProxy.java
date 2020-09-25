package com.sorcery.setup;

import com.sorcery.entity.ModEntity;
import com.sorcery.keybinding.KeyBindings;
import com.sorcery.keybinding.KeyInputHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.entity.SpriteRenderer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.RenderingRegistry;

public class ClientProxy implements IProxy
{

    @Override
    public void init()
    {
        MinecraftForge.EVENT_BUS.register(new KeyInputHandler());
        KeyBindings.init();

        ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();

        RenderingRegistry.registerEntityRenderingHandler(ModEntity.SPELL_PROJECTILE, (manager) -> new SpriteRenderer<>(manager, itemRenderer));
        RenderingRegistry.registerEntityRenderingHandler(ModEntity.FIREBOLT, (manager) -> new SpriteRenderer<>(manager, itemRenderer));
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
