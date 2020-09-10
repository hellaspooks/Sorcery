package com.root.sorcery.container;

import com.root.sorcery.Sorcery;
import com.root.sorcery.client.gui.StaffLatheContainerScreen;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.event.RegistryEvent.Register;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.registries.ObjectHolder;

import static net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus.MOD;

@EventBusSubscriber(bus = MOD)
public class Containers
{

    @ObjectHolder("sorcery:staff_lathe_container")
    public static ContainerType<StaffLatheContainer> STAFF_LATHE_CONTAINER;


    public static void registerScreens()
    {
        ScreenManager.registerFactory(STAFF_LATHE_CONTAINER, StaffLatheContainerScreen::new);
    }

    @SubscribeEvent
    public static void onContainerRegister(Register<ContainerType<?>> event)
    {

        event.getRegistry().register(IForgeContainerType.create((windowId, inv, data) -> {
            BlockPos pos = data.readBlockPos();
            return new StaffLatheContainer(windowId, Sorcery.proxy.getClientWorld(), pos, inv, Sorcery.proxy.getClientPlayer());
        }).setRegistryName("sorcery:staff_lathe_container"));

    }
}
