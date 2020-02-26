package com.root.sorcery.spell;

import com.root.sorcery.Config;
import com.root.sorcery.entity.ModEntity;
import com.root.sorcery.entity.projectile.FireboltEntity;
import com.root.sorcery.utils.Utils;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class FireboltSpell extends Spell
{
    private int velocity;

    public FireboltSpell()
    {
        super(Config.FIREBOLT_SPELL_COST.get());
        this.velocity = 3;
    }


    public ActionResultType doCastFinal(SpellUseContext context)
    {
        this.doParticleEffects(context);
        this.playSound(context);
        World world = context.getWorld();
        FireboltEntity entity = new FireboltEntity(ModEntity.FIREBOLT, world);
        entity.setDamageAndDuration(Config.FIREBOLT_SPELL_DAMAGE.get(), Config.FIREBOLT_SPELL_FIRE_DURATION.get());
        Vec3d eyePos = context.getPlayer().getEyePosition(1);
        Vec3d playerLook = context.getPlayer().getLookVec();
        Vec3d entityPos = Utils.nBlocksAlongVector(eyePos, playerLook, 2.0f);
        entity.setPosition(entityPos.x, entityPos.y, entityPos.z);
        entity.addVelocity(playerLook.x * velocity, playerLook.y * velocity, playerLook.z * velocity);
        world.addEntity(entity);
        return ActionResultType.SUCCESS;
    }
}
