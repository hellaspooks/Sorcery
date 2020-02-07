package com.root.sorcery.utils;

import net.minecraft.util.math.Vec3d;

// Helper class for finding new basis vectors aligned with given normal vector
public class BasisVectors
{
    public Vec3d x;

    public Vec3d y;

    public Vec3d z;

    public BasisVectors(Vec3d zIn)
    {
        this.z = zIn;
        Vec3d arbitraryVec = new Vec3d(0, 1, 0);

        // Make sure arbitrary vector is not paralell to look vector
        if (zIn.getY() == 1 || zIn.getY() == -1)
        {
            if (zIn.getX() == 0 && zIn.getZ() == 0)
            {
                arbitraryVec = new Vec3d(1, 0, 0);
            }
        }
        this.x = zIn.crossProduct(arbitraryVec).normalize();
        this.y = zIn.crossProduct(this.x).normalize();
    }
}
