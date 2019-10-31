package com.root.sorcery;

import com.google.common.collect.ImmutableMap;
import com.root.sorcery.arcana.ArcanaCapability;
import com.root.sorcery.arcana.ArcanaProvider;
import com.root.sorcery.arcana.IArcanaStorage;
import com.root.sorcery.block.ModBlock;
import com.root.sorcery.client.model.StaffModelLoader;
import com.root.sorcery.entity.ModEntity;
import com.root.sorcery.event.DurationSpellEvent;
import com.root.sorcery.event.StructureFormHandlerEvent;
import com.root.sorcery.item.ModItem;
import com.root.sorcery.item.SpellGrantingItem;
import com.root.sorcery.item.tool.ModTool;
import com.root.sorcery.network.PacketHandler;
import com.root.sorcery.network.packets.ArcanaCapSyncPacket;
import com.root.sorcery.network.packets.SpellCapSyncPacket;
import com.root.sorcery.particle.ModParticle;
import com.root.sorcery.particle.SimpleParticle;
import com.root.sorcery.particle.SlowOutParticle;
import com.root.sorcery.setup.ClientProxy;
import com.root.sorcery.setup.IProxy;
import com.root.sorcery.setup.ModSetup;
import com.root.sorcery.setup.ServerProxy;
import com.root.sorcery.spell.ModSpell;
import com.root.sorcery.spellcasting.ISpellcasting;
import com.root.sorcery.spellcasting.SpellcastingCapability;
import com.root.sorcery.spellcasting.SpellcastingProvider;
import com.root.sorcery.tileentity.ModTile;
import com.root.sorcery.tileentity.MonolithTile;
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
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.event.ParticleFactoryRegisterEvent;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.client.model.BasicState;
import net.minecraftforge.client.model.ForgeBlockStateV1;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.client.model.ModelLoaderRegistry;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.model.TRSRTransformation;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
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

import java.util.Collection;
import java.util.Collections;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(Constants.MODID)
public class Sorcery
{

    public static IProxy   proxy = DistExecutor.runForDist(() -> () -> new ClientProxy(), () -> () -> new ServerProxy());
    public static ModSetup setup = new ModSetup();


    private static final Logger LOGGER = LogManager.getLogger();

    private static final TRSRTransformation THIRD_PERSON_BLOCK = ForgeBlockStateV1.Transforms.convert(0, 2.5f, 0, 75, 45, 0, 0.375f);

    public Sorcery()
    {
        // Register the setup method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);

        // Register the doClientStuff method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::doClientStuff);

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);
        MinecraftForge.EVENT_BUS.register(StructureFormHandlerEvent.class);
        MinecraftForge.EVENT_BUS.register(DurationSpellEvent.class);
    }


    private void setup(final FMLCommonSetupEvent event)
    {
        setup.init();
        proxy.init();
        ArcanaCapability.register();
        SpellcastingCapability.register();

        PacketHandler.init();
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

    // Common Registry events
    @Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class CommonRegistryEvents
    {
        @SubscribeEvent
        public static void registerRegistry(RegistryEvent.NewRegistry event){
            new RegistryBuilder<Spell>().setType(Spell.class)
                    .setName(new ResourceLocation(Constants.MODID, "spell"))
                    .create();
        }

        @SubscribeEvent
        public static void registerSpells(RegistryEvent.Register<Spell> event){
            ModSpell.init(event);
        }

        @SubscribeEvent
        public static void onBlocksRegistry(final RegistryEvent.Register<Block> event)
        {
            ModBlock.init(event);
        }


        @SubscribeEvent
        public static void onEntityTypeRegistry(final RegistryEvent.Register<EntityType<?>> event)
        {
            ModEntity.register(event);
        }

        @SubscribeEvent
        public static void onItemsRegistry(final RegistryEvent.Register<Item> event)
        {

            // Have to init this here for spawn eggs
            ModEntity.init();

            ModItem.init(event);
            ModTool.init(event);
        }

        @SubscribeEvent
        public static void onTileEntityRegistry(final RegistryEvent.Register<TileEntityType<?>> event)
        {
            ModTile.init(event);
        }

    }

    // Client-side registry events
    @Mod.EventBusSubscriber(modid = Constants.MODID, value= Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ClientRegistryEvents
    {

        @SubscribeEvent
        public static void registerParticleFactories(final ParticleFactoryRegisterEvent event)
        {
            Minecraft mc = Minecraft.getInstance();

            mc.particles.registerFactory(ModParticle.SIMPLE_PUFF, SimpleParticle.Factory::new);
            mc.particles.registerFactory(ModParticle.SPARK_SLOW, SlowOutParticle.Factory::new);
        }

        @SubscribeEvent
        public static void registerCustomModels(ModelRegistryEvent event)
        {
            // Registers Custom Model Loader
            ModelLoaderRegistry.registerLoader(new StaffModelLoader());
        }

        @SubscribeEvent
        public static void modelBakeEvent(ModelBakeEvent event)
        {
            try {
                // Loads model from registered model loader
                IUnbakedModel model = ModelLoaderRegistry.getModel(new ResourceLocation(Constants.MODID, "item/initiate_staff"));

                IBakedModel bakedModel = model.bake(event.getModelLoader(), ModelLoader.defaultTextureGetter(),
                        new BasicState(model.getDefaultState(), false), DefaultVertexFormats.ITEM);

                event.getModelRegistry().put(new ModelResourceLocation("sorcery:initiate_staff", "inventory"), bakedModel);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @SubscribeEvent
        public static void textureStitch(TextureStitchEvent.Pre event)
        {
            // texture dependencies not working, so adding needed textures here
            event.addSprite(new ResourceLocation(Constants.MODID, "staves/acacia_rod"));
            event.addSprite(new ResourceLocation(Constants.MODID, "staves/initiate_gold_fittings"));
            event.addSprite(new ResourceLocation(Constants.MODID, "staves/initiate_catalyst"));
            event.addSprite(new ResourceLocation(Constants.MODID, "staves/jungle_rod"));
        }


    }

    // Event Handlers
    @Mod.EventBusSubscriber
    public static class EventHandlers
    {

        @SubscribeEvent
        public static void attachCapabilitiesEntities(AttachCapabilitiesEvent<Entity> event)
        {
            if (event.getObject() instanceof PlayerEntity)
            {
                event.addCapability(SpellcastingCapability.SPELLCASTING_LOC, new SpellcastingProvider());
                event.addCapability(ArcanaCapability.ARCANA_LOC, new ArcanaProvider());
            }
        }

        @SubscribeEvent
        public static void attachCapabilitiesItems(AttachCapabilitiesEvent<ItemStack> event)
        {
            if (event.getObject().getItem() instanceof SpellGrantingItem)
            {
                event.addCapability(SpellcastingCapability.SPELLCASTING_LOC, new SpellcastingProvider());
            }
        }

        @SubscribeEvent
        public static void attachCapabilitiesTileEntities(AttachCapabilitiesEvent<TileEntity> event)
        {
            if (event.getObject().getTileEntity() instanceof ReliquaryTile)
            {
                event.addCapability(ArcanaCapability.ARCANA_LOC, new ArcanaProvider());
            } else if (event.getObject().getTileEntity() instanceof MonolithTile)
            {
                event.addCapability(ArcanaCapability.ARCANA_LOC, new ArcanaProvider());
            }

        }

        @SubscribeEvent
        public static void playerCloneEvent(PlayerEvent.Clone event)
        {
            if (event.isWasDeath())
            {
                ISpellcasting origCap = event.getOriginal().getCapability(SpellcastingCapability.SPELLCASTING).orElseThrow(NullPointerException::new);
                ISpellcasting newCap = event.getPlayer().getCapability(SpellcastingCapability.SPELLCASTING).orElseThrow(NullPointerException::new);
                newCap.setActiveSpell(origCap.getActiveSpell());
                newCap.setPreparedSpells(origCap.getPreparedSpells());
                newCap.setKnownSpells(origCap.getKnownSpells());
            }
        }

        @SubscribeEvent
        public static void playerLoginEvent(PlayerEvent.PlayerLoggedInEvent event)
        {
            IArcanaStorage playerArcanaCap = event.getPlayer().getCapability(ArcanaCapability.ARCANA, null).orElseThrow(NullPointerException::new);
            ISpellcasting playerSpellCap = event.getPlayer().getCapability(SpellcastingCapability.SPELLCASTING, null).orElseThrow(NullPointerException::new);

            ServerPlayerEntity serverPlayer = event.getPlayer().getServer().getPlayerList().getPlayerByUUID(event.getPlayer().getUniqueID());

            PacketHandler.sendToPlayer(serverPlayer, new SpellCapSyncPacket((CompoundNBT) SpellcastingCapability.SPELLCASTING.writeNBT(playerSpellCap, null)));
            PacketHandler.sendToPlayer(serverPlayer, new ArcanaCapSyncPacket(ArcanaCapability.ARCANA.writeNBT(playerArcanaCap, null)));
        }

    }

    public static Logger getLogger()
    {
        return LOGGER;
    }

}

