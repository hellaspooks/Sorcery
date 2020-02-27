package com.root.sorcery;

import com.root.sorcery.arcana.ArcanaCapability;
import com.root.sorcery.arcana.ArcanaProvider;
import com.root.sorcery.arcana.IArcanaStorage;
import com.root.sorcery.block.ModBlock;
import com.root.sorcery.client.model.StaffModelLoader;
import com.root.sorcery.entity.ModEntity;
import com.root.sorcery.event.DurationSpellEvent;
import com.root.sorcery.event.EntityDeathEvent;
import com.root.sorcery.event.StructureFormHandlerEvent;
import com.root.sorcery.item.ModItem;
import com.root.sorcery.item.PortableArcanaItem;
import com.root.sorcery.item.SpellGrantingItem;
import com.root.sorcery.item.SpellcastingItem;
import com.root.sorcery.network.PacketHandler;
import com.root.sorcery.network.packets.ArcanaCapSyncPacket;
import com.root.sorcery.network.packets.SpellCapSyncPacket;
import com.root.sorcery.particle.ModParticle;
import com.root.sorcery.particle.RGBAParticle;
import com.root.sorcery.particle.RGBLitParticle;
import com.root.sorcery.potion.ModEffect;
import com.root.sorcery.setup.ClientProxy;
import com.root.sorcery.setup.IProxy;
import com.root.sorcery.setup.ModSetup;
import com.root.sorcery.setup.ServerProxy;
import com.root.sorcery.spell.ModSpell;
import com.root.sorcery.spell.Spell;
import com.root.sorcery.spellcasting.ISpellcasting;
import com.root.sorcery.spellcasting.SpellcastingCapability;
import com.root.sorcery.spellcasting.SpellcastingProvider;
import com.root.sorcery.tileentity.ModTile;
import com.root.sorcery.tileentity.AbstractMonolithTile;
import com.root.sorcery.tileentity.ReliquaryTile;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.model.IUnbakedModel;
import net.minecraft.client.renderer.model.ModelResourceLocation;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.potion.Effect;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.event.ParticleFactoryRegisterEvent;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.client.model.BasicState;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.client.model.ModelLoaderRegistry;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
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
import net.minecraftforge.registries.RegistryBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(Constants.MODID)
public class Sorcery
{

    public static IProxy   proxy = DistExecutor.runForDist(() -> () -> new ClientProxy(), () -> () -> new ServerProxy());
    public static ModSetup setup = new ModSetup();


    private static final Logger LOGGER = LogManager.getLogger();

    public Sorcery()
    {
        loadConfig();

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
        setup.init();
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

