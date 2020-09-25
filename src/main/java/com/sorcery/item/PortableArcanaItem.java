package com.sorcery.item;

import com.sorcery.arcana.ArcanaCapability;
import com.sorcery.arcana.IArcanaStorage;
import com.sorcery.network.PacketHandler;
import com.sorcery.network.packets.ArcanaCapSyncPacket;
import com.sorcery.utils.Utils;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class PortableArcanaItem extends Item
{
    private int arcanaRestoreAmount = 10000;

    public PortableArcanaItem(Properties properties)
    {
        super(properties);
    }


    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn)
    {
        ItemStack itemStack = playerIn.getHeldItem(handIn);

        if (!worldIn.isRemote()) {
            IArcanaStorage itemCap = Utils.getArcanaCap(itemStack);
            IArcanaStorage playerCap = Utils.getArcanaCap(playerIn);
            int arcanaExtracted = itemCap.extractArcana(arcanaRestoreAmount, false);
            int remainder = playerCap.receiveArcana(arcanaExtracted, false);
            itemCap.receiveArcana(remainder, false);

            // Sync player arcana with client
            ServerPlayerEntity serverPlayer = worldIn.getServer().getPlayerList().getPlayerByUUID(playerIn.getUniqueID());
            PacketHandler.sendToPlayer(serverPlayer, new ArcanaCapSyncPacket(ArcanaCapability.ARCANA.writeNBT(playerCap, null)));

            return new ActionResult<>(ActionResultType.SUCCESS, itemStack);
        }

        return new ActionResult<>(ActionResultType.SUCCESS, itemStack);
    }


    @Override
    public CompoundNBT getShareTag(ItemStack stack)
    {
        CompoundNBT baseTag = stack.getTag();
        CompoundNBT arcanaTag = Utils.getArcanaCap(stack).serializeNBT();
        baseTag.put("arcanaCap", arcanaTag);
        return baseTag;
    }

    @Override
    public void readShareTag(ItemStack stack, @Nullable CompoundNBT nbt)
    {
        Utils.getArcanaCap(stack).deserializeNBT(nbt.getCompound("arcanaCap"));
        stack.setTag(nbt);
    }

}
