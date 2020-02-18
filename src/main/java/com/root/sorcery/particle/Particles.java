package com.root.sorcery.particle;

import com.root.sorcery.Constants;
import net.minecraft.particles.IParticleData;

import java.util.List;

public class Particles
{
    // get particle data instances for certain common effects
    public static IParticleData getArcanaOrb(int color)
    {
        List<Integer> rgb = Constants.arcanaColors.get(color);
        return new RGBAParticleData(ModParticle.ARCANA_ORB, ((float)rgb.get(0))/255f, ((float)rgb.get(1))/255f, ((float)rgb.get(2))/255f, 0.7f);
    }

    public static IParticleData getArcanaSpark1(int color)
    {
        List<Integer> rgb = Constants.arcanaColors.get(color);
        return new RGBAParticleData(ModParticle.ARCANA_SPARK_1, ((float)rgb.get(0))/255f, ((float)rgb.get(1))/255f, ((float)rgb.get(2))/255f, 0.5f);
    }

    public static IParticleData getArcanaSpark3(int color)
    {
        List<Integer> rgb = Constants.arcanaColors.get(color);
        return new RGBAParticleData(ModParticle.ARCANA_SPARK_3, ((float)rgb.get(0))/255f, ((float)rgb.get(1))/255f, ((float)rgb.get(2))/255f, 0.5f);
    }

    public static IParticleData getSpark()
    {
        return new RGBAParticleData();
    }

    public static IParticleData getPuff()
    {
        return new RGBAParticleData(ModParticle.SIMPLE_PUFF, 1, 1, 1, 1);
    }

    public static IParticleData getSolarSpark()
    {
        List<Integer> rgb = Constants.SOLAR_GOLD_MAIN;
        return new RGBAParticleData(ModParticle.SIMPLE_SPARK, ((float)rgb.get(0))/255f, ((float)rgb.get(1))/255f, ((float)rgb.get(2))/255f, 0.5f);
    }

    public static IParticleData getLunarSpark()
    {
        List<Integer> rgb = Constants.LUNAR_SILVER_MAIN;
        return new RGBAParticleData(ModParticle.SIMPLE_SPARK, ((float)rgb.get(0))/255f, ((float)rgb.get(1))/255f, ((float)rgb.get(2))/255f, 0.5f);
    }

    public static IParticleData getBloodSpark()
    {
        List<Integer> rgb = Constants.BLOOD_RED_MAIN;
        return new RGBAParticleData(ModParticle.SIMPLE_SPARK, ((float)rgb.get(0))/255f, ((float)rgb.get(1))/255f, ((float)rgb.get(2))/255f, 0.5f);
    }
}
