package com.root.sorcery.particle;

import net.minecraft.particles.IParticleData;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

/**
 * These methods are arrangements and starting velocities of particles.
 * The Particle passed in to each determines what happens after the initial positioning and velocity.
 * IE: passing a SimpleParticle to ringHorizontal will get a ring that expand with constant speed, but passing a SlowOut
 * particle will result in a ring which's expansion slows over time.
 */
public class ParticleEffects
{

    // Bunch of particles within random radius of location, that rise with speed riseSpeed
    public static void risePoof(World world, IParticleData particle, Vec3d location, double radius, long numParticles, double riseSpeed)
    {
        double[] xShifts = world.getRandom().doubles(numParticles, -radius, radius).toArray();
        double[] yShifts =  world.getRandom().doubles(numParticles, -radius, radius).toArray();
        double[] zShifts =  world.getRandom().doubles(numParticles, -radius, radius).toArray();
        for (int i = 0; i < numParticles; i++)
        {
            Vec3d pos = location.add(xShifts[i], yShifts[i], zShifts[i]);
            world.addParticle(particle, pos.getX(), pos.getY(), pos.getZ(), 0, riseSpeed, 0);
        }
    }

    // Horizontal ring of evenly space particles around location
    public static void ringHorizontal(World world, IParticleData particle, Vec3d location, double numParticles, double speed)
    {
        for (int i = 0; i < numParticles; i++)
        {
            double angleRadians = ((2 * Math.PI) / numParticles) * i;
            double vecX = Math.cos(angleRadians) * speed;
            double vecZ = Math.sin(angleRadians) * speed;
            world.addParticle(particle, location.getX(), location.getY(), location.getZ(), vecX, 0, vecZ);
        }
    }

    // Expanding sphere of ~roughly evenly spaced particles surrounding location
    public static void expandingSphere(World world, IParticleData particle, Vec3d location, double numParticles, double speed)
    {
        double rand = world.getRandom().nextDouble() * numParticles;
        double offset = 2.0/numParticles;
        double increment = Math.PI * (3.0 - Math.sqrt(5.0));
        for ( int i = 0; i < numParticles; i++)
        {
            double y = ((i * offset) - 1) + (offset / 2);
            double r = Math.sqrt(1 - Math.pow(y, 2));
            double phi = ((i + rand) % numParticles) * increment;
            double x = Math.cos(phi) * r;
            double z = Math.sin(phi) * r;
            Vec3d pathVec = new Vec3d(x, y, z).normalize();
            world.addParticle(particle, location.getX(), location.getY(), location.getZ(), pathVec.getX() * speed,pathVec.getY() * speed, pathVec.getZ() * speed);
        }
    }
}
