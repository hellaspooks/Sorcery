package com.root.sorcery.particle;

import net.minecraft.client.particle.IAnimatedSprite;
import net.minecraft.client.particle.IParticleFactory;
import net.minecraft.client.particle.IParticleRenderType;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.SpriteTexturedParticle;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;

public class RGBAParticle extends SpriteTexturedParticle
{
    IAnimatedSprite spriteSet;

    public RGBAParticle(World world, double x, double y, double z, double vX, double vY, double vZ, IAnimatedSprite spriteSetIn, float r, float g, float b, float a)
    {
        super(world, x, y, z, vX, vY, vZ);

        // Override motion randomization
        this.motionX = vX;
        this.motionY = vY;
        this.motionZ = vZ;
        this.spriteSet = spriteSetIn;
        this.particleRed = r;
        this.particleBlue = b;
        this.particleGreen = g;
        this.particleAlpha = a;

        this.canCollide = false;
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
    public static class Factory implements IParticleFactory<RGBAParticleData>
    {
        private final IAnimatedSprite spriteSet;

        public Factory(IAnimatedSprite spriteSet)
        {
            this.spriteSet = spriteSet;

        }

        @Nullable
        @Override
        public Particle makeParticle(RGBAParticleData data, World worldIn, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed)
        {
            RGBAParticle simpleParticle = new RGBAParticle(worldIn, x, y, z, xSpeed, ySpeed, zSpeed, spriteSet, data.r, data.g, data.b, data.a);
            simpleParticle.setMaxAge(data.t);
            simpleParticle.selectSpriteWithAge(spriteSet);
            return simpleParticle;
        }
    }

}
