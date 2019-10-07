package com.root.sorcery.spell;

import com.root.sorcery.particle.ModParticle;
import net.minecraft.client.Minecraft;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;

public class TestSpell extends Spell
{

    private String debugMessage;

    public TestSpell(String debugMessageIn)
    {
        this.debugMessage = debugMessageIn;

    }

    @Override
    public ActionResultType cast(SpellUseContext context)
    {
        if (!drainArcana(context, 0))
            return ActionResultType.FAIL;

        World world = context.getWorld();
        if ( context.getPlayer() != null){

            if (!world.isRemote())
            {
                context.getPlayer().sendMessage(new StringTextComponent(this.debugMessage));
            }
            return ActionResultType.SUCCESS;
        }
        else
        {
            return ActionResultType.FAIL;
        }

    }

    @Override
    public void doSpellEffects(SpellUseContext context)
    {
        System.out.println("In do FX");
        BlockPos pos = context.getPos();

        context.getWorld().addParticle(ModParticle.TESTPARTICLE, pos.getX(), pos.getY(), pos.getZ(), 0, 0, 0);

    }
}
