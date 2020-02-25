package com.root.sorcery.particle;

import com.root.sorcery.Constants;
import net.minecraft.particles.IParticleData;
import net.minecraft.particles.ParticleType;

import java.util.List;

/**
 * Helper methods to get common particles and sets of particles.
 *
 * This is structured this way to help keep the size of particle effect packets as small as possible.
 * Instead of a more descriptive name, they only need to store a single int to refer to a given particle collection.
 *
 * Additional helper methods exist for collections that will be called often by client-side-only constructs, such as passive generation monoliths.
 */
public class Particles
{

    public static IParticleData getSpark()
    {
        return new RGBAParticleData();
    }

    public static IParticleData getPuff()
    {
        return new RGBAParticleData(ModParticle.SIMPLE_PUFF, 1, 1, 1, 1);
    }

    public static IParticleData getColoredParticle(List<Integer> rgb, ParticleType type, float alpha)
    {
        return new RGBAParticleData(type, ((float)rgb.get(0))/255f, ((float)rgb.get(1))/255f, ((float)rgb.get(2))/255f, alpha);
    }

    public static ParticleCollection getArcanaOrbs()
    {
        return getParticleSet(6);
    }

    public static ParticleCollection getArcanaOrbSparks()
    {
        return getParticleSet(5);
    }

    public static ParticleCollection getLunarSparks()
    {
        return getParticleSet(2);
    }

    public static ParticleCollection getSolarSparks()
    {
        return getParticleSet(1);
    }

    public static ParticleCollection getParticleSet(int set)
    {
        ParticleCollection collection = new ParticleCollection();
        switch (set)
        {
            case 0:
                collection.add(50, getColoredParticle(Constants.ARCANA_PURPLE_MAIN, ModParticle.SIMPLE_SPARK, 1.0f));
                collection.add(25, getColoredParticle(Constants.ARCANA_PURPLE_LOWLIGHT, ModParticle.SIMPLE_SPARK, 1.0f));
                collection.add(25, getColoredParticle(Constants.ARCANA_PURPLE_HIGHLIGHT, ModParticle.SIMPLE_SPARK, 1.0f));
                return collection;
            case 1:
                collection.add(50, getColoredParticle(Constants.SOLAR_GOLD_MAIN, ModParticle.SIMPLE_SPARK, 1.0f));
                collection.add(25, getColoredParticle(Constants.SOLAR_GOLD_LOWLIGHT, ModParticle.SIMPLE_SPARK, 1.0f));
                collection.add(25, getColoredParticle(Constants.SOLAR_GOLD_HIGHLIGHT, ModParticle.SIMPLE_SPARK, 1.0f));
                return collection;
            case 2:
                collection.add(50, getColoredParticle(Constants.LUNAR_SILVER_MAIN, ModParticle.LIT_SPARK, 1.0f));
                collection.add(25, getColoredParticle(Constants.LUNAR_SILVER_LOWLIGHT, ModParticle.LIT_SPARK, 1.0f));
                collection.add(25, getColoredParticle(Constants.LUNAR_SILVER_HIGHLIGHT, ModParticle.LIT_SPARK, 1.0f));
                return collection;
            case 3:
                collection.add(50, getColoredParticle(Constants.LAPIS_BLUE_MAIN, ModParticle.SIMPLE_SPARK, 1.0f));
                collection.add(25, getColoredParticle(Constants.LAPIS_BLUE_LOWLIGHT, ModParticle.SIMPLE_SPARK, 1.0f));
                collection.add(25, getColoredParticle(Constants.LAPIS_BLUE_HIGHLIGHT, ModParticle.SIMPLE_SPARK, 1.0f));
                return collection;
            case 4:
                collection.add(50, getColoredParticle(Constants.BLOOD_RED_MAIN, ModParticle.SIMPLE_SPARK, 1.0f));
                collection.add(25, getColoredParticle(Constants.BLOOD_RED_LOWLIGHT, ModParticle.SIMPLE_SPARK, 1.0f));
                collection.add(25, getColoredParticle(Constants.BLOOD_RED_HIGHLIGHT, ModParticle.SIMPLE_SPARK, 1.0f));
                return collection;
            case 5:
                collection.add(50, getColoredParticle(Constants.ARCANA_PURPLE_MAIN, ModParticle.ARCANA_SPARK_1, 0.7f));
                collection.add(25, getColoredParticle(Constants.ARCANA_PURPLE_LOWLIGHT, ModParticle.ARCANA_SPARK_1, 0.7f));
                collection.add(25, getColoredParticle(Constants.ARCANA_PURPLE_HIGHLIGHT, ModParticle.ARCANA_SPARK_1, 0.7f));
                collection.add(50, getColoredParticle(Constants.ARCANA_PURPLE_MAIN, ModParticle.ARCANA_SPARK_3, 0.7f));
                collection.add(25, getColoredParticle(Constants.ARCANA_PURPLE_LOWLIGHT, ModParticle.ARCANA_SPARK_3, 0.7f));
                collection.add(25, getColoredParticle(Constants.ARCANA_PURPLE_HIGHLIGHT, ModParticle.ARCANA_SPARK_3, 0.7f));
                return collection;
            case 6:
                collection.add(50, getColoredParticle(Constants.ARCANA_PURPLE_MAIN, ModParticle.ARCANA_ORB, 1.0f));
                collection.add(25, getColoredParticle(Constants.ARCANA_PURPLE_LOWLIGHT, ModParticle.ARCANA_ORB, 1.0f));
                collection.add(25, getColoredParticle(Constants.ARCANA_PURPLE_HIGHLIGHT, ModParticle.ARCANA_ORB, 1.0f));
                return collection;
            default:
                collection.add(100, getSpark());
                return collection;
        }

    }

}
