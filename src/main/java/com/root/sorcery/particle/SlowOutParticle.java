package com.root.sorcery.particle;

import net.minecraft.client.particle.IAnimatedSprite;
import net.minecraft.client.particle.IParticleFactory;
import net.minecraft.client.particle.IParticleRenderType;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.SpriteTexturedParticle;
import net.minecraft.particles.BasicParticleType;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;

public class SlowOutParticle extends SpriteTexturedParticle
{
    IAnimatedSprite spriteSet;
    int hangTicks;

    public SlowOutParticle(World world, double x, double y, double z, double vX, double vY, double vZ, IAnimatedSprite spriteSetIn, int hangTicksIn)
    {
        super(world, x, y, z, vX, vY, vZ);
        // Override motion randomization
        this.motionX = vX;
        this.motionY = vY;
        this.motionZ = vZ;
        this.spriteSet = spriteSetIn;
        this.hangTicks = hangTicksIn;
    }

    @Override
    public void tick()
    {
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;

        if (this.age++ >= this.maxAge) {
            this.setExpired();
        } else  if  (this.maxAge - this.age > this.hangTicks)
        {
            int motionDamp = this.maxAge - this.hangTicks - this.age;
            this.motionX -= this.motionX / motionDamp;
            this.motionY -= this.motionY / motionDamp;
            this.motionZ -= this.motionZ / motionDamp;
        } else
        {
           this.motionX = 0;
           this.motionY = 0;
           this.motionZ = 0;
        }
        this.move(motionX, motionY, motionZ);

        this.selectSpriteWithAge(this.spriteSet);
    }

    @Override
    public IParticleRenderType getRenderType()
    {
        return IParticleRenderType.PARTICLE_SHEET_OPAQUE;
    }


    @OnlyIn(Dist.CLIENT)
    public static class Factory implements IParticleFactory<BasicParticleType>
    {
        private final IAnimatedSprite spriteSet;

        public Factory(IAnimatedSprite spriteSet)
        {
            this.spriteSet = spriteSet;
        }

        @Nullable
        @Override
        public Particle makeParticle(BasicParticleType typeIn, World worldIn, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed)
        {
            SlowOutParticle simpleParticle = new SlowOutParticle(worldIn, x, y, z, xSpeed, ySpeed, zSpeed, spriteSet, 10);
            simpleParticle.setMaxAge(40);
            simpleParticle.selectSpriteWithAge(spriteSet);
            return simpleParticle;
        }
    }

}
