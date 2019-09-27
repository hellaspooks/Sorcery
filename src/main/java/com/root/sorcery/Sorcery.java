package com.root.sorcery;

import com.root.sorcery.block.ModBlock;
import com.root.sorcery.event.BlockRightClickEvent;
import com.root.sorcery.event.StructureFormHandlerEvent;
import com.root.sorcery.item.ModItem;
import com.root.sorcery.item.tool.ModTool;
import com.root.sorcery.setup.ClientProxy;
import com.root.sorcery.setup.IProxy;
import com.root.sorcery.setup.ModSetup;
import com.root.sorcery.setup.ServerProxy;
import com.root.sorcery.spell.ModSpell;
import com.root.sorcery.tileentity.ModTile;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.RegistryBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.root.sorcery.spell.Spell;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(Constants.MODID)
public class Sorcery
{

    public static IProxy   proxy = DistExecutor.runForDist(() -> () -> new ClientProxy(), () -> () -> new ServerProxy());
    public static ModSetup setup = new ModSetup();


    private static final Logger LOGGER = LogManager.getLogger();

    public Sorcery()
    {
        // Register the setup method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);

        // Register the doClientStuff method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::doClientStuff);

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);
        MinecraftForge.EVENT_BUS.register(BlockRightClickEvent.class);
        MinecraftForge.EVENT_BUS.register(StructureFormHandlerEvent.class);
    }

    private void setup(final FMLCommonSetupEvent event)
    {
        setup.init();
        proxy.init();
    }

    private void doClientStuff(final FMLClientSetupEvent event)
    {
        // do something that can only be done on the client
        LOGGER.info("Got game settings {}", event.getMinecraftSupplier().get().gameSettings);
    }


    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(FMLServerStartingEvent event)
    {
        // do something when the server starts
        LOGGER.info("HELLO from server starting");
    }

    // You can use EventBusSubscriber to automatically subscribe events on the contained class (this is subscribing to the MOD
    // Event bus for receiving Registry Events)
    @Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class RegistryEvents
    {

        @SubscribeEvent
        public static void registerRegistry(RegistryEvent.NewRegistry event){
            new RegistryBuilder<Spell>().setType(Spell.class)
                    .setName(new ResourceLocation(Constants.MODID, "spell"))
                    .create();

        }

        @SubscribeEvent
        public static void registerSpells(RegistryEvent.Register<Spell> event){
            ModSpell.registerSpells(event);
        }

        @SubscribeEvent
        public static void onBlocksRegistry(final RegistryEvent.Register<Block> event)
        {
            ModBlock.init(event);
        }

        @SubscribeEvent
        public static void onItemsRegistry(final RegistryEvent.Register<Item> event)
        {

            ModItem.init(event);
            ModTool.init(event);
        }

        @SubscribeEvent
        public static void onTileEntityRegistry(final RegistryEvent.Register<TileEntityType<?>> event)
        {
            ModTile.init(event);
        }

    }

    public static Logger getLogger()
    {
        return LOGGER;
    }

}

