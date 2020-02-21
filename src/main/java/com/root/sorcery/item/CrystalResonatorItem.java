package com.root.sorcery.item;

import com.root.sorcery.network.PacketHandler;
import com.root.sorcery.network.packets.ParticleEffectPacket;
import com.root.sorcery.tileentity.AbstractMonolithTile;
import com.root.sorcery.tileentity.ArcanaStorageTile;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;

import javax.annotation.Nullable;


public class CrystalResonatorItem extends Item
{

    public CrystalResonatorItem(Properties properties)
    {
        super(properties);
    }
    @Nullable
    public BlockPos getLinkPos(ItemStack stack)
    {
        CompoundNBT nbt = stack.getTag();
        if ( nbt.contains("linkPos"))
        {
            CompoundNBT posTag = (CompoundNBT) nbt.get("linkPos");
            return new BlockPos(posTag.getInt("x"), posTag.getInt("y"), posTag.getInt("z"));
        } else {
            return null;
        }
    }

    @Override
    public ActionResultType onItemUse(ItemUseContext context)
    {
        if (!context.getWorld().isRemote())
        {
            World world = context.getWorld();
            CompoundNBT nbt = context.getItem().getOrCreateTag();
            BlockPos pos = new BlockPos(context.getPos());
            TileEntity tile =  world.getTileEntity(pos);

            if (tile instanceof ArcanaStorageTile)
            {
                if (context.getPlayer().isSneaking())
                {
                    context.getPlayer().sendStatusMessage(new StringTextComponent("Link position set!"), true);
                    setLinkPos(context.getItem(), pos);
                } else {

                    // do interference particle effect for monoliths
                    if (tile instanceof AbstractMonolithTile)
                    {
                        int range = ((AbstractMonolithTile) tile).getInterferenceRange();
                        Vec3d tilePos = new Vec3d(pos);

                        ParticleEffectPacket pkt = new ParticleEffectPacket(8, ParticleTypes.ENCHANT, tilePos.add(0.5, 0.5, 0.5), tilePos, 10, 0, range, 20);
                        PacketHandler.sendToAllTrackingPlayer(context.getPlayer(), pkt);
                    }

                    if (nbt.contains("linkPos"))
                    {
                        CompoundNBT posTag = (CompoundNBT) nbt.get("linkPos");
                        BlockPos linkPos = new BlockPos(posTag.getInt("x"), posTag.getInt("y"), posTag.getInt("z"));

                        TileEntity linkTile = world.getTileEntity(linkPos);

                        context.getPlayer().sendStatusMessage(new StringTextComponent("Arcana target linked!"), true);

                        if (linkTile != null && linkTile instanceof ArcanaStorageTile)
                        {
                            ((ArcanaStorageTile) linkTile).setArcanaTransferTarget((ArcanaStorageTile) tile);
                        }
                    }
                }

            }
        }
        return ActionResultType.SUCCESS;
    }


    public void setLinkPos(ItemStack stack, BlockPos pos)
    {
        CompoundNBT nbt = stack.getTag();
        CompoundNBT posTag = new CompoundNBT();
        posTag.putInt("x", pos.getX());
        posTag.putInt("y", pos.getY());
        posTag.putInt("z", pos.getZ());
        nbt.put("linkPos", posTag);
        stack.setTag(nbt);
    }

    public void nullLinkPos(ItemStack stack)
    {
        CompoundNBT nbt = stack.getTag();
        if (nbt.contains("linkPos"))
        {
            nbt.remove("linkPos");
        }
    }

}
