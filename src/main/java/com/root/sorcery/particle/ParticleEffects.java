package com.root.sorcery.particle;

import com.root.sorcery.utils.BasisVectors;
import com.root.sorcery.utils.Utils;
import net.minecraft.particles.IParticleData;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

/**
 * These methods are arrangements and starting velocities of particles.
 * The Particle passed in to each determines what happens after the initial positioning and velocity.
 * IE: passing a SimpleParticle to ringHorizontal will get a ring that expand with constant speed, but passing a SlowOut
 * particle will result in a ring which's expansion slows over time.
 *
 */
public class ParticleEffects
{


    // Bunch of particles within random radius of location, that rise with speed riseSpeed
    public static void risePoof(World world, IParticleData particle, Vec3d loc, Vec3d lookVec, int numParticles, double speed, double radius, int age)
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
    public static void ringHorizontal(World world, IParticleData particle, Vec3d loc, Vec3d lookVec, int numParticles, double speed, double radius, int age)
    {
        for (int i = 0; i < numParticles; i++)
        {
            double angleRadians = ((2 * Math.PI) / numParticles) * i;
            double vecX = Math.cos(angleRadians) * speed;
            double vecZ = Math.sin(angleRadians) * speed;
            world.addParticle(particle, loc.getX(), loc.getY(), loc.getZ(), vecX, 0, vecZ);
        }
    }

    // pulse effect sent by arcana storage tiles
    public static void arcanaPulse(World world, IParticleData particle, Vec3d origin, Vec3d dest, int density, double speed, double radius, int age)
    {
        int color1 = world.rand.nextInt(2);
        int color2 = world.rand.nextInt(2);
        int color3 = world.rand.nextInt(2);
        sendTo(world.getWorld(), Particles.getArcanaOrb(color1), origin, dest, 1, 1, 0, age);
        sendTo(world.getWorld(), Particles.getArcanaSpark1(color2), origin, dest, 1, 0.95, 0, age);
        sendTo(world.getWorld(), Particles.getArcanaSpark3(color3), origin, dest, 1, 0.9, 0, age);
        sendTo(world.getWorld(), Particles.getArcanaSpark3(color2), origin, dest, 1, 0.85, 0, age);
        sendTo(world.getWorld(), Particles.getArcanaSpark1(color1), origin, dest, 1, 0.8, 0, age);
    }

    // line of particles moving towards endpoint
    public static void sendTo(World world, IParticleData particle, Vec3d origin, Vec3d dest, int density, double speed, double radius, int age)
    {
        Vec3d ray = dest.subtract(origin).normalize();
        double distance = dest.distanceTo(origin);
        double realSpeed = (distance / (double) (age - 10)) * speed;
        Vec3d vec = ray.mul(realSpeed, realSpeed, realSpeed);
        world.addParticle(particle, origin.getX(), origin.getY(), origin.getZ(), vec.getX(), vec.getY(), vec.getZ());
    }

    // Expanding sphere of ~roughly evenly spaced particles surrounding location
    public static void expandingSphere(World world, IParticleData particle, Vec3d loc, Vec3d lookVec, int numParticles, double speed, double radius, int age)
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

    // spray of particles in a cone
    public static void coneSpray(World world, IParticleData particle, Vec3d loc, Vec3d lookVec, int numParticles, double speed, double radius, int age)
    {
        BasisVectors vecs = new BasisVectors(lookVec);

        double[] rand1 = world.rand.doubles(numParticles, -1, 1).toArray();
        double[] rand2 = world.rand.doubles(numParticles, -1, 1).toArray();

        for ( int i = 0; i < numParticles; i++)
        {
            double r1 = rand1[i] * radius;
            double rMax = Math.sqrt(Math.pow(radius, 2) - Math.pow(r1, 2));
            double r2 = rand2[i] * rMax;
            Vec3d partVec = lookVec;
            partVec = partVec.add(vecs.x.mul(r1, r1, r1)).normalize();
            partVec = partVec.add(vecs.y.mul(r2, r2, r2)).normalize();
            partVec = partVec.mul(speed, speed, speed);


            world.addParticle(particle, loc.getX(), loc.getY(), loc.getZ(), partVec.getX(), partVec.getY(), partVec.getZ());
        }

    }

    // little fountain or particles
    public static void smallFountain(World world, IParticleData particle, Vec3d loc, Vec3d lookVec, int numParticles, double speed, double radius, int age)
    {
        for ( int i = 0; i < numParticles; i++)
        {
            double vX = world.rand.nextDouble() * radius;
            double vZ = world.rand.nextDouble() * radius;
            double vY = speed;

            world.addParticle(particle, loc.getX(), loc.getY(), loc.getZ(), vX, vY, vZ);
        }

    }

    // particles drawn in to source from dest
    public static void drawIn(World world, IParticleData particle, Vec3d loc, Vec3d lookVec, int numParticles, double speed, double radius, int age)
    {
        BasisVectors basis = new BasisVectors(lookVec);
        Vec3d startingPoint = Utils.nBlocksAlongVector(loc, lookVec, 4);

        double[] rand1 = world.rand.doubles(numParticles, -1, 1).toArray();
        double[] rand2 = world.rand.doubles(numParticles, -1, 1).toArray();
        double[] rand3 = world.rand.doubles(numParticles, -1, 1).toArray();

        for (int i = 0; i < numParticles; i++)
        {
            double r1 = rand1[i] * radius;
            double rMax = Math.sqrt(Math.pow(radius, 2) - Math.pow(r1, 2));
            double r2 = rand2[i] * rMax;
            double r3 = rand3[i] * 0.5;

            Vec3d startPos = basis.addXYZ(startingPoint, r1, r2, r3);

            sendTo(world, particle, startPos, loc, 1, 1, 1, age);
        }
    }

    public static void drawInFrom(World world, IParticleData particle, Vec3d loc, Vec3d loc2, int numParticles, double speed, double radius, int age)
    {
        double[] rand1 = world.rand.doubles(numParticles, -1, 1).toArray();
        double[] rand2 = world.rand.doubles(numParticles, -1, 1).toArray();
        double[] rand3 = world.rand.doubles(numParticles, -1, 1).toArray();

        for (int i = 0; i < numParticles; i++)
        {
            Vec3d startPos = loc2.add(rand1[i] * radius, rand2[i] * radius, rand3[i] * radius);
            sendTo(world, particle, startPos, loc, 1, 1, 1, age);
        }
    }


}
