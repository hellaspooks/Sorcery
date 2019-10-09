package com.root.sorcery.container;

import com.root.sorcery.client.gui.ChondriteBlastFurnaceGui;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.inventory.container.ContainerType;
import net.minecraftforge.event.RegistryEvent.Register;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.registries.ObjectHolder;
import net.voxelindustry.steamlayer.container.BuiltContainer;
import net.voxelindustry.steamlayer.container.SteamLayerContainerFactory;

import static net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus.MOD;

@EventBusSubscriber(bus = MOD)
public class Containers
{
    @ObjectHolder("sorcery:chondrite_blastfurnace")
    public static ContainerType<BuiltContainer> CHONDRITE_BLAST_FURNACE;

    public static void registerScreens()
    {
        ScreenManager.registerFactory(CHONDRITE_BLAST_FURNACE, ChondriteBlastFurnaceGui::new);
    }

    @SubscribeEvent
    public static void onContainerRegister(Register<ContainerType<?>> event)
    {
        event.getRegistry().register(SteamLayerContainerFactory.create().setRegistryName("sorcery:chondrite_blastfurnace"));
    }
}
