package com.sorcery.entity.projectile;

import com.sorcery.Constants;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.IRendersAsItem;
import net.minecraft.entity.projectile.DamagingProjectileEntity;
import net.minecraft.entity.projectile.ProjectileHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.IPacket;
import net.minecraft.particles.IParticleData;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

public class SpellProjectileEntity extends DamagingProjectileEntity implements IRendersAsItem
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

    public void tick() {
        Entity entity = this.func_234616_v_();
        if (this.world.isRemote || (entity == null || !entity.removed) && this.world.isBlockLoaded(this.getPosition())) {
            super.tick();
            if (this.isFireballFiery()) {
                this.setFire(1);
            }

            RayTraceResult raytraceresult = ProjectileHelper.func_234618_a_(this, this::func_230298_a_);
            if (raytraceresult.getType() != RayTraceResult.Type.MISS && !net.minecraftforge.event.ForgeEventFactory.onProjectileImpact(this, raytraceresult)) {
                this.onImpact(raytraceresult);
            }

            this.doBlockCollisions();
            Vector3d vector3d = this.getMotion();
            double d0 = this.getPosX() + vector3d.x;
            double d1 = this.getPosY() + vector3d.y;
            double d2 = this.getPosZ() + vector3d.z;
            ProjectileHelper.rotateTowardsMovement(this, 0.2F);
            float f = this.getMotionFactor();
            if (this.isInWater()) {
                for(int i = 0; i < 4; ++i) {
                    float f1 = 0.25F;
                    this.world.addParticle(ParticleTypes.BUBBLE, d0 - vector3d.x * 0.25D, d1 - vector3d.y * 0.25D, d2 - vector3d.z * 0.25D, vector3d.x, vector3d.y, vector3d.z);
                }

                f = 0.8F;
            }

            if (!this.hasNoGravity())
            {
                vector3d = vector3d.add(0,-0.05, 0);
            }

            this.setMotion(vector3d.add(this.accelerationX, this.accelerationY, this.accelerationZ).scale((double)f));
            this.setPosition(d0, d1, d2);
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

    @Override
    public ItemStack getItem()
    {
        return new ItemStack(Items.FIRE_CHARGE);
    }
}
