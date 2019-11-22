package com.root.sorcery.entity;

import com.root.sorcery.Constants;
import com.root.sorcery.entity.projectile.FireboltEntity;
import com.root.sorcery.entity.projectile.SpellProjectileEntity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.registries.ObjectHolder;

@ObjectHolder("sorcery")
public class ModEntity
{

    @ObjectHolder("toad")
    public static EntityType<ToadEntity> TOAD;

    @ObjectHolder("spell_projectile")
    public static EntityType<SpellProjectileEntity> SPELL_PROJECTILE;

    @ObjectHolder("firebolt")
    public static EntityType<FireboltEntity> FIREBOLT;



    public static void init()
    {
        // Mob Entities
        TOAD = (EntityType<ToadEntity>) EntityType.Builder.create(ToadEntity::new, EntityClassification.CREATURE)
            .size(1,1)
            .setShouldReceiveVelocityUpdates(false)
            .build("toad")
            .setRegistryName(Constants.MODID, "toad");

        // Other Entities
        SPELL_PROJECTILE = (EntityType<SpellProjectileEntity>) EntityType.Builder.create(SpellProjectileEntity::new, EntityClassification.MISC)
                .size(1,1)
                .setShouldReceiveVelocityUpdates(true)
                .setCustomClientFactory(((spawnEntity, world) -> new SpellProjectileEntity(SPELL_PROJECTILE, world)))
                .build("spell_projectile")
                .setRegistryName(Constants.MODID, "spell_projectile");

        FIREBOLT = (EntityType<FireboltEntity>) EntityType.Builder.create(FireboltEntity::new, EntityClassification.MISC)
                .size(1,1)
                .setShouldReceiveVelocityUpdates(true)
                .setCustomClientFactory(((spawnEntity, world) -> new FireboltEntity(FIREBOLT, world)))
                .build("firebolt")
                .setRegistryName(Constants.MODID, "firebolt");
    }


    public static void register(RegistryEvent.Register<EntityType<?>> event)
    {
        event.getRegistry().register(TOAD);
        event.getRegistry().register(SPELL_PROJECTILE);
        event.getRegistry().register(FIREBOLT);

    }
}
