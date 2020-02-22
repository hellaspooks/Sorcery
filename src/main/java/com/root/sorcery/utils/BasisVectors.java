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

    public Vec3d addX(Vec3d vecIn, double xIn)
    {
        return vecIn.add(x.mul(xIn, xIn, xIn));
    }

    public Vec3d addY(Vec3d vecIn, double yIn)
    {
        return vecIn.add(y.mul(yIn, yIn, yIn));
    }

    public Vec3d addZ(Vec3d vecIn, double zIn)
    {
        return vecIn.add(z.mul(zIn, zIn, zIn));
    }

    public Vec3d addXYZ(Vec3d vecIn, double xIn, double yIn, double zIn)
    {
        return vecIn.add(x.mul(xIn, xIn, xIn)).add(y.mul(yIn, yIn, yIn)).add(z.mul(zIn, zIn, zIn));
    }

}
