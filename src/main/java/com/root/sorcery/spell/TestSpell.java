package com.root.sorcery.spell;

import com.root.sorcery.particle.ModParticle;
import com.root.sorcery.particle.ParticleEffects;
import com.root.sorcery.utils.Utils;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.StringTextComponent;

public class TestSpell extends Spell
{

    private String debugMessage;

    public TestSpell(String debugMessageIn)
    {
        this.debugMessage = debugMessageIn;

    }

    @Override
    public ActionResultType castServer(SpellUseContext context)
    {

        if ( context.getPlayer() != null){
            context.getPlayer().sendMessage(new StringTextComponent(this.debugMessage));
            return ActionResultType.SUCCESS;
        }
        else
        {
            return ActionResultType.FAIL;
        }

    }

    @Override
    public void castClient(SpellUseContext context)
    {
        Vec3d particleLocation = Utils.nBlocksAlongVector(context.getPlayer().getEyePosition(0), context.getPlayer().getLook(0), 1f);
        ParticleEffects.risePoof(context.getWorld(), ModParticle.SIMPLE_PUFF, particleLocation, 0.2, 20, 0.1);
    }
}
