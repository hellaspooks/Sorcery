package com.root.sorcery.spell;

        import com.root.sorcery.Config;
        import com.root.sorcery.network.PacketHandler;
        import com.root.sorcery.network.packets.ParticleEffectPacket;
        import com.root.sorcery.particle.ParticleEffects;
        import net.minecraft.entity.player.PlayerEntity;
        import net.minecraft.util.ActionResultType;
        import net.minecraft.util.math.Vec3d;

public class RaiseZombieSpell extends DurationSpell
{
    public RaiseZombieSpell()
    {

    }

    @Override
    public ActionResultType castServer(SpellUseContext context) {

        PlayerEntity player = context.getPlayer();

        return ActionResultType.SUCCESS;
    }

    @Override
    public void doParticleEffects(SpellUseContext context)
    {
        Vec3d loc = context.getPlayer().getPositionVec().add(0,1, 0);
        Vec3d look = context.getPlayer().getLookVec();

        ParticleEffectPacket pkt = new ParticleEffectPacket(2, ParticleEffects.getSpark(), loc, look, 100, 0.5, 0.2);
        PacketHandler.sendToAllTracking(context.getPlayer(), pkt);
    }
}
