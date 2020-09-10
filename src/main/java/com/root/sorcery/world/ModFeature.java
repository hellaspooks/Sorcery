package com.root.sorcery.world;

import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.TreeFeature;
import net.minecraft.world.gen.feature.TreeFeatureConfig;


public abstract class ModFeature
{

    public static final Feature<TreeFeatureConfig> RUNEWOOD_TREE = new TreeFeature(TreeFeatureConfig::func_227338_a_);

}
