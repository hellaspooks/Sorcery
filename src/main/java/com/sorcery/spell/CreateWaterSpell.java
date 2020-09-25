package com.sorcery.spell;

import com.sorcery.Config;
import com.sorcery.network.PacketHandler;
import com.sorcery.network.packets.ParticleEffectPacket;
import com.sorcery.utils.Utils;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.FlowingFluidBlock;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;

public class CreateWaterSpell extends Spell
{
    public CreateWaterSpell()
    {
        super(Config.CREATE_WATER_SPELL_COST.get());
        this.sound = SoundEvents.ITEM_BUCKET_EMPTY;
    }

    @Override
    public ActionResultType doCastFinal(SpellUseContext context)
    {
        if (context.getHitPos() != null)
        {
            BlockPos waterPos = context.getFacePos();

            if (!context.getWorld().isAirBlock(waterPos))
            {
                if (!(context.getWorld().getBlockState(waterPos).getBlock() instanceof FlowingFluidBlock))
                {
                    return ActionResultType.FAIL;
                }
            }
            this.doParticleEffects(context);
            this.playSound(context);

            BlockState blockState = Blocks.WATER.getDefaultState();
            context.getWorld().setBlockState(waterPos, blockState, 11);
            return ActionResultType.SUCCESS;
        }
        return ActionResultType.FAIL;
    }

    @Override
    public boolean allowCast(SpellUseContext context)
    {
        return context.wasUsedOnBlock();
    }

    @Override
    public void doParticleEffects(SpellUseContext context)
    {
        BlockPos waterPos = context.getFacePos();
        Vector3d loc = Utils.getVectorFromPos(waterPos).add(0.5, 1,0.5);
        ParticleEffectPacket pkt1 = new ParticleEffectPacket(5, ParticleTypes.SPLASH, loc, loc, 20, 2, 2, 20);
        PacketHandler.sendToAllTrackingPlayer(context.getPlayer(), pkt1);
    }
}
