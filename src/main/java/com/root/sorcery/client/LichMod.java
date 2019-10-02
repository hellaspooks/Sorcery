package com.root.sorcery.client;

import net.minecraft.client.Minecraft;

public class LichMod
{
    public static boolean isLichModActivated()
    {
        return Minecraft.getInstance().getSession().getProfile().getName().toLowerCase().contains("lich");
    }
}
