package com.root.sorcery.spell;

import com.root.sorcery.particle.ModParticle;
import com.root.sorcery.particle.ParticleEffects;
import net.minecraft.client.Minecraft;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;
import org.lwjgl.system.CallbackI;

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
        ParticleEffects.poofEffect(ModParticle.TESTPARTICLE, context);
    }
}
