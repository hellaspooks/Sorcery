package com.root.sorcery.spell;

import com.root.sorcery.particle.ParticleEffects;
import com.root.sorcery.utils.Utils;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.Vec3d;

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
