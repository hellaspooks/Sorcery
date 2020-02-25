package com.root.sorcery.particle;

import net.minecraft.particles.IParticleData;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class ParticleEffectContext
{
    public World world;
    public ParticleCollection particleCollection;
    public Vec3d vec1;
    public Vec3d vec2;
    public int numParticles;
    public double speed;
    public double radius;
    public int age;


    public ParticleEffectContext(World world, ParticleCollection particleCollection, Vec3d vec1, Vec3d vec2, int numParticles, double speed, double radius, int age)
    {
        this.world = world;
        this.particleCollection = particleCollection;
        this.vec1 = vec1;
        this.vec2 = vec2;
        this.numParticles = numParticles;
        this.speed = speed;
        this.radius = radius;
        this.age = age;
    }

    public ParticleEffectContext(World world, IParticleData particle, Vec3d vec1, Vec3d vec2, int numParticles, double speed, double radius, int age)
    {
        this.world = world;
        this.particleCollection = new ParticleCollection();
        this.particleCollection.add(100, particle);
        this.vec1 = vec1;
        this.vec2 = vec2;
        this.numParticles = numParticles;
        this.speed = speed;
        this.radius = radius;
        this.age = age;
    }

    public IParticleData getParticle()
    {
        IParticleData particle = this.particleCollection.next();
        return particle;
    }

}
