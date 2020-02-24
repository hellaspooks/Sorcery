package com.root.sorcery.particle;

import com.root.sorcery.utils.BasisVectors;
import com.root.sorcery.utils.Utils;
import net.minecraft.util.math.Vec3d;

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
    public static void risePoof(ParticleEffectContext ctx)
    {
        double[] xShifts = ctx.world.getRandom().doubles(ctx.numParticles, -ctx.radius, ctx.radius).toArray();
        double[] yShifts =  ctx.world.getRandom().doubles(ctx.numParticles, -ctx.radius, ctx.radius).toArray();
        double[] zShifts =  ctx.world.getRandom().doubles(ctx.numParticles, -ctx.radius, ctx.radius).toArray();
        for (int i = 0; i < ctx.numParticles; i++)
        {
            Vec3d pos = ctx.loc.add(xShifts[i], yShifts[i], zShifts[i]);
            ctx.world.addParticle(ctx.getParticle(), pos.getX(), pos.getY(), pos.getZ(), 0, ctx.speed, 0);
        }
    }

    // Horizontal ring of evenly space particles around location
    public static void ringHorizontal(ParticleEffectContext ctx)
    {
        for (int i = 0; i < ctx.numParticles; i++)
        {
            double angleRadians = ((2 * Math.PI) / ctx.numParticles) * i;
            double vecX = Math.cos(angleRadians) * ctx.speed;
            double vecZ = Math.sin(angleRadians) * ctx.speed;
            ctx.world.addParticle(ctx.getParticle(), ctx.loc.getX(), ctx.loc.getY(), ctx.loc.getZ(), vecX, 0, vecZ);
        }
    }

    // pulse effect sent by arcana storage tiles
    public static void arcanaPulse(ParticleEffectContext ctx)
    {
        sendTo(new ParticleEffectContext(ctx.world.getWorld(), Particles.arcanaOrbs(), ctx.loc, ctx.look, 1, 1, 0, ctx.age));
        sendTo(new ParticleEffectContext(ctx.world.getWorld(), Particles.arcanaOrbSparks(), ctx.loc, ctx.look, 1, 0.95, 0, ctx.age));
        sendTo(new ParticleEffectContext(ctx.world.getWorld(), Particles.arcanaOrbSparks(), ctx.loc, ctx.look, 1, 0.9, 0, ctx.age));
        sendTo(new ParticleEffectContext(ctx.world.getWorld(), Particles.arcanaOrbSparks(), ctx.loc, ctx.look, 1, 0.85, 0, ctx.age));
        sendTo(new ParticleEffectContext(ctx.world.getWorld(), Particles.arcanaOrbSparks(), ctx.loc, ctx.look, 1, 0.8, 0, ctx.age));
    }

    // line of particles moving towards endpoint
    public static void sendTo(ParticleEffectContext ctx)
    {
        Vec3d ray = ctx.look.subtract(ctx.loc).normalize();
        double distance = ctx.look.distanceTo(ctx.loc);
        double realSpeed = (distance / (double) (ctx.age - 10)) * ctx.speed;
        Vec3d vec = ray.mul(realSpeed, realSpeed, realSpeed);
        ctx.world.addParticle(ctx.getParticle(), ctx.loc.getX(), ctx.loc.getY(), ctx.loc.getZ(), vec.getX(), vec.getY(), vec.getZ());
    }

    // Expanding sphere of ~roughly evenly spaced particles surrounding location
    public static void expandingSphere(ParticleEffectContext ctx)
    {
        double rand = ctx.world.getRandom().nextDouble() * ctx.numParticles;
        double offset = 2.0/ctx.numParticles;
        double increment = Math.PI * (3.0 - Math.sqrt(5.0));
        for ( int i = 0; i < ctx.numParticles; i++)
        {
            double y = ((i * offset) - 1) + (offset / 2);
            double r = Math.sqrt(1 - Math.pow(y, 2));
            double phi = ((i + rand) % ctx.numParticles) * increment;
            double x = Math.cos(phi) * r;
            double z = Math.sin(phi) * r;
            Vec3d pathVec = new Vec3d(x, y, z).normalize();
            ctx.world.addParticle(ctx.getParticle(), ctx.loc.getX(), ctx.loc.getY(), ctx.loc.getZ(), pathVec.getX() * ctx.speed,pathVec.getY() * ctx.speed, pathVec.getZ() * ctx.speed);
        }
    }

    // spray of particles in a cone
    public static void coneSpray(ParticleEffectContext ctx)
    {
        BasisVectors vecs = new BasisVectors(ctx.look);

        double[] rand1 = ctx.world.rand.doubles(ctx.numParticles, -1, 1).toArray();
        double[] rand2 = ctx.world.rand.doubles(ctx.numParticles, -1, 1).toArray();

        for ( int i = 0; i < ctx.numParticles; i++)
        {
            double r1 = rand1[i] * ctx.radius;
            double rMax = Math.sqrt(Math.pow(ctx.radius, 2) - Math.pow(r1, 2));
            double r2 = rand2[i] * rMax;
            Vec3d partVec = ctx.look;
            partVec = partVec.add(vecs.x.mul(r1, r1, r1)).normalize();
            partVec = partVec.add(vecs.y.mul(r2, r2, r2)).normalize();
            partVec = partVec.mul(ctx.speed, ctx.speed, ctx.speed);


            ctx.world.addParticle(ctx.getParticle(), ctx.loc.getX(), ctx.loc.getY(), ctx.loc.getZ(), partVec.getX(), partVec.getY(), partVec.getZ());
        }

    }

    // little fountain or particles
    public static void smallFountain(ParticleEffectContext ctx)
    {
        for ( int i = 0; i < ctx.numParticles; i++)
        {
            double vX = ctx.world.rand.nextDouble() * ctx.radius;
            double vZ = ctx.world.rand.nextDouble() * ctx.radius;
            double vY = ctx.speed;

            ctx.world.addParticle(ctx.getParticle(), ctx.loc.getX(), ctx.loc.getY(), ctx.loc.getZ(), vX, vY, vZ);
        }

    }

    public static void drawIn(ParticleEffectContext ctx)
    {
        BasisVectors basis = new BasisVectors(ctx.look);
        Vec3d startingPoint = Utils.nBlocksAlongVector(ctx.loc, ctx.look, 4);

        double[] rand1 = ctx.world.rand.doubles(ctx.numParticles, -1, 1).toArray();
        double[] rand2 = ctx.world.rand.doubles(ctx.numParticles, -1, 1).toArray();
        double[] rand3 = ctx.world.rand.doubles(ctx.numParticles, -1, 1).toArray();

        for (int i = 0; i < ctx.numParticles; i++)
        {
            double r1 = rand1[i] * ctx.radius;
            double rMax = Math.sqrt(Math.pow(ctx.radius, 2) - Math.pow(r1, 2));
            double r2 = rand2[i] * rMax;
            double r3 = rand3[i] * 0.5;

            Vec3d startPos = basis.addXYZ(startingPoint, r1, r2, r3);

            sendTo(new ParticleEffectContext(ctx.world, ctx.getParticle(), startPos, ctx.loc, 1, 1, 1, ctx.age));
        }
    }

    // particles drawn in to source from dest
    public static void drawInFrom(ParticleEffectContext ctx)
    {
        double[] rand1 = ctx.world.rand.doubles(ctx.numParticles, -1, 1).toArray();
        double[] rand2 = ctx.world.rand.doubles(ctx.numParticles, -1, 1).toArray();
        double[] rand3 = ctx.world.rand.doubles(ctx.numParticles, -1, 1).toArray();

        for (int i = 0; i < ctx.numParticles; i++)
        {
            Vec3d startPos = ctx.look.add(rand1[i] * ctx.radius, rand2[i] * ctx.radius, rand3[i] * ctx.radius);
            sendTo(new ParticleEffectContext(ctx.world, ctx.getParticle(), startPos, ctx.loc, 1, 1, 1, ctx.age));
        }
    }

    public static void staticHorizontalRing(ParticleEffectContext ctx)
    {
        double circumference = ctx.radius * 2 * Math.PI;
        int n = (int)circumference * ctx.numParticles;

        double step = Math.PI * 2 / n;

        for (int i = 0; i < n; i++)
        {
            double angle = step * i;
            double x = ctx.radius * Math.cos(angle);
            double z = ctx.radius * Math.sin(angle);

            ctx.world.addParticle(ctx.getParticle(), ctx.loc.x + x, ctx.loc.y, ctx.loc.z + z, 0, 0, 0);
        }
    }


}
