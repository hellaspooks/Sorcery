package com.root.sorcery.entity;

import com.root.sorcery.Constants;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.registries.ObjectHolder;

public class ModEntity
{

    @ObjectHolder("sorcery:toad")
    public static EntityType<ToadEntity> TOAD;



    public static void init()
    {
        TOAD = (EntityType<ToadEntity>) EntityType.Builder.create(ToadEntity::new, EntityClassification.CREATURE)
            .size(1,1)
            .setShouldReceiveVelocityUpdates(false)
            .build("toad")
            .setRegistryName(Constants.MODID, "toad");
    }


    public static void register(RegistryEvent.Register<EntityType<?>> event)
    {
        event.getRegistry().register(TOAD);

    }
}
