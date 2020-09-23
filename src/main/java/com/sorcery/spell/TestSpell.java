package com.sorcery.spell;

import com.sorcery.network.PacketHandler;
import com.sorcery.network.packets.ParticleEffectPacket;
import com.sorcery.particle.Particles;
import com.sorcery.utils.Utils;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.StringTextComponent;

public class TestSpell extends Spell
{

    private String debugMessage;

    public TestSpell(String debugMessageIn, int arcanaCost)
    {
        super(arcanaCost);
        this.debugMessage = debugMessageIn;

    }

    @Override
    public ActionResultType doCastFinal(SpellUseContext context)
    {

        this.doParticleEffects(context);
        this.playSound(context);
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
    public void doParticleEffects(SpellUseContext context)
    {

        Vector3d loc = Utils.nBlocksAlongVector(context.getPlayer().getEyePosition(0), context.getPlayer().getLook(0), 1f);
        Vector3d look = context.getPlayer().getLookVec();

        ParticleEffectPacket pkt = new ParticleEffectPacket(0, Particles.getPuff(), loc, look, 20, 0.1, 0.2, 20);

        PacketHandler.sendToAllTrackingPlayer(context.getPlayer(), pkt);
    }
}
