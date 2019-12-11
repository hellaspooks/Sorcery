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

public class SimpleParticle extends SpriteTexturedParticle
{
    IAnimatedSprite spriteSet;

    public SimpleParticle(World world, double x, double y, double z, double vX, double vY, double vZ, IAnimatedSprite spriteSetIn)
    {
        super(world, x, y, z, vX, vY, vZ);

        // Override motion randomization
        this.motionX = vX;
        this.motionY = vY;
        this.motionZ = vZ;
        this.spriteSet = spriteSetIn;
    }


    @Override
    public void tick()
    {
        super.tick();
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
            SimpleParticle simpleParticle = new SimpleParticle(worldIn, x, y, z, xSpeed, ySpeed, zSpeed, spriteSet);
            simpleParticle.setMaxAge(20);
            simpleParticle.selectSpriteWithAge(spriteSet);
            return simpleParticle;
        }
    }

}
