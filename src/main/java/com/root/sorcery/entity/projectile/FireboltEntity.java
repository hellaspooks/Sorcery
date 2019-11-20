package com.root.sorcery.entity.projectile;

import com.root.sorcery.Constants;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.world.World;

public class FireboltEntity extends SpellProjectileEntity
{
    public int damage;
    public int fireDuration;

    public FireboltEntity(EntityType<? extends FireboltEntity> entityType, World world)
    {
        super(entityType, world);
        this.projectileTexture = new ResourceLocation(Constants.MODID, "textures/entity/fire_charge.png");
        this.setNoGravity(false);
        this.damage = 0;
        this.fireDuration = 0;
    }

    @Override
    public void onEntityHit(EntityRayTraceResult result)
    {
        if (result.getEntity() instanceof LivingEntity)
        {
            LivingEntity hitEntity = (LivingEntity) result.getEntity();

            hitEntity.attackEntityFrom(DamageSource.IN_FIRE, this.damage);
            hitEntity.setFire(this.fireDuration);
        }
    }

    public void setDamageAndDuration(int damage, int fireDuration)
    {
        this.damage = damage;
        this.fireDuration = fireDuration;
    }
}
