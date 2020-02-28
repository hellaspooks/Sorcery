package com.root.sorcery.particle;

import net.minecraft.particles.IParticleData;

import java.util.NavigableMap;
import java.util.Random;
import java.util.TreeMap;

/**
 * Used to supply a particle effect with a weighted random assortment of particles.
 */
public class ParticleCollection
{
    private final NavigableMap<Double, IParticleData> particles = new TreeMap<>();
    private final Random random;
    private double total = 0;


    public ParticleCollection()
    {
        this(new Random());
    }

    public ParticleCollection(Random random)
    {
        this.random = random;
    }

    public void add(double weight, IParticleData particle)
    {
        total += weight;
        particles.put(total, particle);
    }

    public IParticleData next()
    {
        double value = random.nextDouble() * total;
        return particles.higherEntry(value).getValue();
    }
}
