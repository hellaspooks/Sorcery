package com.root.sorcery.setup;

import com.root.sorcery.Constants;
import com.root.sorcery.block.ModBlock;
import com.root.sorcery.entity.ModEntity;
import com.root.sorcery.item.ModItem;
import com.root.sorcery.potion.ModEffect;
import com.root.sorcery.spell.ModSpell;
import com.root.sorcery.spell.Spell;
import com.root.sorcery.tileentity.ModTile;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.potion.Effect;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.RegistryBuilder;

// Common Registry events
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class CommonRegistryEvents
{
    @SubscribeEvent
    public static void registerRegistry(RegistryEvent.NewRegistry event)
    {
        new RegistryBuilder<Spell>().setType(Spell.class)
                .setName(new ResourceLocation(Constants.MODID, "spell"))
                .create();
    }

    @SubscribeEvent
    public static void registerSpells(RegistryEvent.Register<Spell> event)
    {
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
    }

    @SubscribeEvent
    public static void onTileEntityRegistry(final RegistryEvent.Register<TileEntityType<?>> event)
    {
        ModTile.init(event);
    }

    @SubscribeEvent
    public static void onEffectRegistry(final RegistryEvent.Register<Effect> event)
    {
        ModEffect.init(event);
    }

}
