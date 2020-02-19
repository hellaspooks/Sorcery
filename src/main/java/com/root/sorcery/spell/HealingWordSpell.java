package com.root.sorcery.spell;

import java.util.List;

import com.root.sorcery.Config;
import com.root.sorcery.network.PacketHandler;
import com.root.sorcery.network.packets.ParticleEffectPacket;
// import com.root.sorcery.particle.ModParticle;
import com.root.sorcery.particle.ParticleEffects;
import com.root.sorcery.utils.Utils;

// import net.minecraft.potion.Effect;
// import net.minecraft.potion.EffectInstance;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;

public class HealingWordSpell extends Spell
{
    private String debugMessage = "Healed";

    public HealingWordSpell() {
        super(Config.HEALING_WORD_COST.get());
        this.sound = SoundEvents.BLOCK_BEACON_ACTIVATE;
    }

    @Override
    public ActionResultType castServer(SpellUseContext context) {
        
        LivingEntity player = context.getPlayer();
        
        if (doAltCast(context))
        {
            if (context.getTargetEntity() != null)
            {
                if (context.getTargetEntity().isAlive())
                {
                    LivingEntity livingEntity = ((LivingEntity)context.getTargetEntity());
                    livingEntity.heal(Config.HEALING_WORD_DAMAGE.get());
                    player.sendMessage(new StringTextComponent(livingEntity.getEntityString()));
                    player.sendMessage(new StringTextComponent(livingEntity.getCachedUniqueIdString()));
                    player.sendMessage(new StringTextComponent(this.debugMessage + " that guy."));
                    return ActionResultType.SUCCESS;
                }
                return ActionResultType.FAIL;
            }   
        }
        if ( context.getPlayer() != null){
            player.heal(Config.HEALING_WORD_DAMAGE.get());
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

        Vec3d loc = Utils.nBlocksAlongVector(context.getPlayer().getEyePosition(0), context.getPlayer().getLook(0), 1f);
        Vec3d look = context.getPlayer().getLookVec();

        ParticleEffectPacket pkt = new ParticleEffectPacket(0, ParticleEffects.getPuff(), loc, look, 20, 0.1, 0.2);

        PacketHandler.sendToAllTracking(context.getPlayer(), pkt);
    }
}