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
    public static void risePoof(World world, IParticleData particle, Vec3d loc, Vec3d lookVec, int numParticles, double speed, double radius)
    {
        double[] xShifts = world.getRandom().doubles(numParticles, -radius, radius).toArray();
        double[] yShifts =  world.getRandom().doubles(numParticles, -radius, radius).toArray();
        double[] zShifts =  world.getRandom().doubles(numParticles, -radius, radius).toArray();
        for (int i = 0; i < numParticles; i++)
        {
            Vec3d pos = loc.add(xShifts[i], yShifts[i], zShifts[i]);
            world.addParticle(particle, pos.getX(), pos.getY(), pos.getZ(), 0, speed, 0);
        }
    }

    // Horizontal ring of evenly space particles around location
    public static void ringHorizontal(World world, IParticleData particle, Vec3d loc, Vec3d lookVec, int numParticles, double speed, double radius)
    {
        for (int i = 0; i < numParticles; i++)
        {
            double angleRadians = ((2 * Math.PI) / numParticles) * i;
            double vecX = Math.cos(angleRadians) * speed;
            double vecZ = Math.sin(angleRadians) * speed;
            world.addParticle(particle, loc.getX(), loc.getY(), loc.getZ(), vecX, 0, vecZ);
        }
    }

    // line of particles moving towards endpoint
    public static void sendTo(World world, IParticleData particle, Vec3d origin, Vec3d dest, int density, double speed, double radius)
    {
        Vec3d ray = dest.subtract(origin).normalize();
        double distance = dest.distanceTo(origin);
        int age = 20;
        double realSpeed = distance / (double) age;
        Vec3d vec = ray.mul(realSpeed, realSpeed, realSpeed);
        world.addParticle(particle, origin.getX(), origin.getY(), origin.getZ(), vec.getX(), vec.getY(), vec.getZ());
    }

    // Expanding sphere of ~roughly evenly spaced particles surrounding location
    public static void expandingSphere(World world, IParticleData particle, Vec3d loc, Vec3d lookVec, int numParticles, double speed, double radius)
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
            world.addParticle(particle, loc.getX(), loc.getY(), loc.getZ(), pathVec.getX() * speed,pathVec.getY() * speed, pathVec.getZ() * speed);
        }
    }

    public static void coneSpray(World world, IParticleData particle, Vec3d loc, Vec3d lookVec, int numParticles, double speed, double radius)
    {
        Vec3d arbitraryVec = new Vec3d(0, 1, 0);

        // Make sure arbitrary vector is not paralell to look vector
        if (lookVec.getY() == 1 || lookVec.getY() == -1)
        {
            if (lookVec.getX() == 0 && lookVec.getZ() == 0)
            {
                arbitraryVec = new Vec3d(1, 0, 0);
            }
        }

        Vec3d vecP = lookVec.crossProduct(arbitraryVec).normalize();
        Vec3d vecQ = lookVec.crossProduct(vecP).normalize();


        double[] rand1 = world.rand.doubles(numParticles, -1, 1).toArray();
        double[] rand2 = world.rand.doubles(numParticles, -1, 1).toArray();

        for ( int i = 0; i < numParticles; i++)
        {
            double r1 = rand1[i] * radius;
            double rMax = Math.sqrt(Math.pow(radius, 2) - Math.pow(r1, 2));
            double r2 = rand2[i] * rMax;
            Vec3d partVec = lookVec;
            partVec = partVec.add(vecP.mul(r1, r1, r1)).normalize();
            partVec = partVec.add(vecQ.mul(r2, r2, r2)).normalize();
            partVec = partVec.mul(speed, speed, speed);


            world.addParticle(particle, loc.getX(), loc.getY(), loc.getZ(), partVec.getX(), partVec.getY(), partVec.getZ());
        }

    }
}
