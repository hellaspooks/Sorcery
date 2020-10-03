package com.sorcery;

import com.sorcery.arcana.ArcanaCapability;
import com.sorcery.block.ModBlock;
import com.sorcery.item.ModItem;
import com.sorcery.network.PacketHandler;
import com.sorcery.setup.ClientProxy;
import com.sorcery.setup.IProxy;
import com.sorcery.setup.ModSetup;
import com.sorcery.setup.ServerProxy;
import com.sorcery.spell.ModSpell;
import com.sorcery.spellcasting.SpellcastingCapability;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLPaths;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(Constants.MODID)
public class Sorcery
{

    public static IProxy proxy = DistExecutor.runForDist(() -> () -> new ClientProxy(), () -> () -> new ServerProxy());
    public static ModSetup setup = new ModSetup();


    private static final Logger LOGGER = LogManager.getLogger();


    public Sorcery()
    {
        loadConfig();

        // Registration
        ModBlock.init();
        ModItem.init();
        ModSpell.init();


        // Register the setup method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);

        // Register the doClientStuff method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::doClientStuff);

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);
    }

    private void loadConfig()
    {
        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, Config.CLIENT_CONFIG);
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Config.COMMON_CONFIG);

        Config.loadConfig(Config.CLIENT_CONFIG, FMLPaths.CONFIGDIR.get().resolve(Constants.MODID+ "-client" + ".toml"));
        Config.loadConfig(Config.COMMON_CONFIG, FMLPaths.CONFIGDIR.get().resolve(Constants.MODID + "-common" + ".toml"));
    }

    public void setup(final FMLCommonSetupEvent event)
    {
        proxy.init();

        ArcanaCapability.register();
        SpellcastingCapability.register();
        PacketHandler.init();
    }

    public void doClientStuff(final FMLClientSetupEvent event)
    {
        LOGGER.info("Got game settings {}", event.getMinecraftSupplier().get().gameSettings);
    }

    @SubscribeEvent
    public void onServerStarting(FMLServerStartingEvent event)
    {
        // do something when the server starts
        LOGGER.info("HELLO from server starting");
    }

    public static Logger getLogger()
    {
        return LOGGER;
    }

}

