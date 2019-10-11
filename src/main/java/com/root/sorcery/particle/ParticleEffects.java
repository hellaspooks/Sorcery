package com.root.sorcery.particle;

import com.root.sorcery.spell.SpellUseContext;
import com.root.sorcery.utils.Utils;
import net.minecraft.particles.IParticleData;
import net.minecraft.util.math.Vec3d;


public class ParticleEffects
{

    public static void poofEffect(IParticleData particle, SpellUseContext context)
    {

        Vec3d baseVec = Utils.nBlocksAlongVector(context.getPlayer().getEyePosition(0), context.getPlayer().getLook(0), 1f);

        double radius = 0.2;

        long n = 20;

        double[] xShifts = context.getWorld().getRandom().doubles(n, -radius, radius).toArray();
        double[] yShifts =  context.getWorld().getRandom().doubles(n, -radius, radius).toArray();
        double[] zShifts =  context.getWorld().getRandom().doubles(n, -radius, radius).toArray();

        for (int i = 0; i < n; i++)
        {
            Vec3d pos = baseVec.add(xShifts[i], yShifts[i], zShifts[i]);

            context.getWorld().addParticle(particle, pos.getX(), pos.getY(), pos.getZ(), 0, 0.1, 0);
        }


    }
}
