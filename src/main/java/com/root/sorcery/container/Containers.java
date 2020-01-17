package com.root.sorcery.container;

import com.root.sorcery.Sorcery;
import com.root.sorcery.client.gui.EscritorieContainerScreen;
import com.root.sorcery.client.gui.StaffLatheContainerScreen;
import com.root.sorcery.client.gui.WolframBlastFurnaceGui;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.extensions.IForgeContainerType;
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
    @ObjectHolder("sorcery:wolfram_blastfurnace")
    public static ContainerType<BuiltContainer> WOLFRAM_BLAST_FURNACE;

    @ObjectHolder("sorcery:staff_lathe_container")
    public static ContainerType<StaffLatheContainer> STAFF_LATHE_CONTAINER;

    @ObjectHolder("sorcery:escritorie_container")
    public static ContainerType<EscritorieContainer> ESCRITORIE_CONTAINER;

    public static void registerScreens()
    {
        ScreenManager.registerFactory(WOLFRAM_BLAST_FURNACE, WolframBlastFurnaceGui::new);
        ScreenManager.registerFactory(STAFF_LATHE_CONTAINER, StaffLatheContainerScreen::new);
        ScreenManager.registerFactory(ESCRITORIE_CONTAINER, EscritorieContainerScreen::new);
    }

    @SubscribeEvent
    public static void onContainerRegister(Register<ContainerType<?>> event)
    {
        event.getRegistry().register(SteamLayerContainerFactory.create().setRegistryName("sorcery:wolfram_blastfurnace"));

        event.getRegistry().register(IForgeContainerType.create((windowId, inv, data) -> {
            BlockPos pos = data.readBlockPos();
            return new StaffLatheContainer(windowId, Sorcery.proxy.getClientWorld(), pos, inv, Sorcery.proxy.getClientPlayer());
        }).setRegistryName("sorcery:staff_lathe_container"));

        event.getRegistry().register(IForgeContainerType.create((windowId, inv, data) -> {
            BlockPos pos = data.readBlockPos();
            return new EscritorieContainer(windowId, Sorcery.proxy.getClientWorld(), pos, inv, Sorcery.proxy.getClientPlayer());
        }).setRegistryName("sorcery:escritorie_container"));
    }
}
