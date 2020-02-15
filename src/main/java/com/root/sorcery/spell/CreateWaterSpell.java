package com.root.sorcery.spell;

import com.root.sorcery.Config;
import com.root.sorcery.network.PacketHandler;
import com.root.sorcery.network.packets.ParticleEffectPacket;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.FlowingFluidBlock;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

public class CreateWaterSpell extends Spell
{
    public CreateWaterSpell()
    {
        super(Config.CREATE_WATER_SPELL_COST.get());
        this.sound = SoundEvents.ITEM_BUCKET_EMPTY;
    }

    @Override
    public ActionResultType castServer(SpellUseContext context)
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
        System.out.println("trying to do particle effects");
        BlockPos waterPos = context.getFacePos();
        Vec3d loc = new Vec3d(waterPos).add(0.5, 1,0.5);
        ParticleEffectPacket pkt1 = new ParticleEffectPacket(5, ParticleTypes.SPLASH, loc, loc, 20, 2, 2);
        PacketHandler.sendToAllTracking(context.getPlayer(), pkt1);
    }
}
