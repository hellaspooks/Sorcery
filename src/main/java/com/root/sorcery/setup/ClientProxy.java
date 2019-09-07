package com.root.sorcery.setup;

import net.minecraft.client.Minecraft;
import net.minecraft.world.World;
import net.minecraftforge.client.model.obj.OBJLoader;

public class ClientProxy implements IProxy
{

    @Override
    public void init()
    {
        OBJLoader.INSTANCE.addDomain("sorcery");
    }

    @Override
    public World getClientWorld()
    {
        return Minecraft.getInstance().world;
    }
}
