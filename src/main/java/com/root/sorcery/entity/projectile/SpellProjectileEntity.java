package com.root.sorcery.entity.projectile;

import com.root.sorcery.Constants;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.projectile.DamagingProjectileEntity;
import net.minecraft.entity.projectile.ProjectileHelper;
import net.minecraft.network.IPacket;
import net.minecraft.particles.IParticleData;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

public class SpellProjectileEntity extends DamagingProjectileEntity
{
    public ResourceLocation projectileTexture;
    public int ticksInAir;
    public IParticleData baseParticle;

    public SpellProjectileEntity(EntityType<? extends SpellProjectileEntity> entityType, World world)
    {
        super(entityType, world);
        this.projectileTexture = new ResourceLocation(Constants.MODID, "textures/entity/spell_projectile.png");
        this.baseParticle = ParticleTypes.SMOKE;
        this.setNoGravity(true);
    }

    // Called when projectile hits block
    public void onBlockHit(BlockRayTraceResult result)
    {
    }

    // Called when projectile hits entity
    public void onEntityHit(EntityRayTraceResult result)
    {
    }

    // called once per tick
    public void doPerMotionTick()
    {
        this.world.addParticle(this.getParticle(), this.getPosX(), this.getPosY() + 0.5D, this.getPosZ(), 0.0D, 0.0D, 0.0D);
    }



    // Override of base onImpact, and dispatch to onBlockHit and onEntity Hit
    @Override
    protected void onImpact(RayTraceResult result)
    {
        if (!this.world.isRemote) {
            if (result.getType() == RayTraceResult.Type.ENTITY)
            {
                this.onEntityHit((EntityRayTraceResult) result);
            }
            if (result.getType() == RayTraceResult.Type.BLOCK)
            {
                this.onBlockHit((BlockRayTraceResult) result);
            }
            this.world.setEntityState(this, (byte)3);
            this.remove();
        }
    }

    // add gravity, remove default particle spawning from base class
    @Override
    public void tick()
    {
        if (this.world.isRemote || (this.shootingEntity == null || !this.shootingEntity.removed) && this.world.isBlockLoaded(new BlockPos(this))) {
            super.tick();

            if (this.isFireballFiery()) {
                this.setFire(1);
            }

            ++this.ticksInAir;
            RayTraceResult raytraceresult = ProjectileHelper.rayTrace(this, true, this.ticksInAir >= 10, this.shootingEntity, RayTraceContext.BlockMode.COLLIDER);
            if (raytraceresult.getType() != RayTraceResult.Type.MISS && !net.minecraftforge.event.ForgeEventFactory.onProjectileImpact(this, raytraceresult)) {
                this.onImpact(raytraceresult);
            }

            Vec3d vec3d = this.getMotion();


            double d0 = this.getPosX() + vec3d.x;
            double d1 = this.getPosY() + vec3d.y;
            double d2 = this.getPosZ() + vec3d.z;
            this.setPosition(d0, d1, d2);
            ProjectileHelper.rotateTowardsMovement(this, 0.2F);

            float f = this.getMotionFactor();

            if (this.isInWater()) {

            }

            if (!this.hasNoGravity())
            {
                vec3d = vec3d.add(0,-0.05, 0);
            }

            this.setMotion(vec3d.add(this.accelerationX, this.accelerationY, this.accelerationZ).scale((double)f));

            this.doPerMotionTick();

            this.setPosition(this.getPosX(), this.getPosY(), this.getPosZ());
        } else {
            this.remove();
        }
    }


    @Override
    protected IParticleData getParticle() {
        return this.baseParticle;
    }

    @Override
    public boolean isFireballFiery() {
        return false;
    }

    public IPacket<?> createSpawnPacket() {
       return NetworkHooks.getEntitySpawningPacket(this);
    }

    public ResourceLocation getEntityTexture()
    {
       return this.projectileTexture;
    }
}
