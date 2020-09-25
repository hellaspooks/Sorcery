package com.sorcery.utils;

import net.minecraft.util.math.vector.Vector3d;

// Helper class for finding new basis vectors aligned with given normal vector
public class BasisVectors
{
    public Vector3d x;

    public Vector3d y;

    public Vector3d z;

    public BasisVectors(Vector3d zIn)
    {
        this.z = zIn;
        Vector3d arbitraryVec = new Vector3d(0, 1, 0);

        // Make sure arbitrary vector is not paralell to look vector
        if (zIn.getY() == 1 || zIn.getY() == -1)
        {
            if (zIn.getX() == 0 && zIn.getZ() == 0)
            {
                arbitraryVec = new Vector3d(1, 0, 0);
            }
        }
        this.x = zIn.crossProduct(arbitraryVec).normalize();
        this.y = zIn.crossProduct(this.x).normalize();
    }

    public Vector3d addX(Vector3d vecIn, double xIn)
    {
        return vecIn.add(x.mul(xIn, xIn, xIn));
    }

    public Vector3d addY(Vector3d vecIn, double yIn)
    {
        return vecIn.add(y.mul(yIn, yIn, yIn));
    }

    public Vector3d addZ(Vector3d vecIn, double zIn)
    {
        return vecIn.add(z.mul(zIn, zIn, zIn));
    }

    public Vector3d addXYZ(Vector3d vecIn, double xIn, double yIn, double zIn)
    {
        return vecIn.add(x.mul(xIn, xIn, xIn)).add(y.mul(yIn, yIn, yIn)).add(z.mul(zIn, zIn, zIn));
    }

}
