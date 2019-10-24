package com.root.sorcery.spell;

import com.root.sorcery.particle.ParticleEffects;
import com.root.sorcery.utils.Utils;
import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.lwjgl.system.CallbackI;

import java.util.List;

public class CombustionSpell extends Spell
{

    public CombustionSpell()
    {
        this.arcanaCost = 1;
        this.castDuration = 100;
        this.castType = CastType.CHANNELED;
        this.sound = SoundEvents.ITEM_FIRECHARGE_USE;
    }

    @Override
    public ActionResultType castServer(SpellUseContext context)
    {
        List<Entity> entList = Utils.entitiesInCone(context.getWorld(), context.getPos(), context.getPlayer(), context.getPlayer().getEyePosition(1), context.getPlayer().getLook(1), 8, 0.2);

        for ( Entity entity : entList)
        {
            if (entity instanceof CreatureEntity)
            {
                entity.setFire(3);
                entity.attackEntityFrom(DamageSource.ON_FIRE, 1.0F);
            }
        }

        return ActionResultType.SUCCESS;
    }

    @Override
    public void castClient(SpellUseContext context)
    {
        Vec3d particleLocation = Utils.nBlocksAlongVector(context.getPlayer().getEyePosition(0), context.getPlayer().getLook(0), 1f).add(0, -.1, 0);
        ParticleEffects.coneSpray(context.getWorld(), ParticleTypes.FLAME, particleLocation, context.getPlayer().getLookVec(), 40, 0.5, 0.2);
        ParticleEffects.coneSpray(context.getWorld(), ParticleTypes.SMOKE, particleLocation, context.getPlayer().getLookVec(), 10, 0.3, 0.2);
    }
}
