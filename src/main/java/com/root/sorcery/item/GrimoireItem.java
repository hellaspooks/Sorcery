package com.root.sorcery.item;

import com.root.sorcery.client.gui.grimoire.GrimoireGui;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;
import net.voxelindustry.brokkgui.wrapper.impl.BrokkGuiManager;

public class GrimoireItem extends Item
{
    public GrimoireItem(Properties properties)
    {
        super(properties);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, PlayerEntity player, Hand hand)
    {
        ItemStack itemStack = player.getHeldItem(hand);

        if (!world.isRemote())
        {
            BrokkGuiManager.openBrokkGuiScreen(new StringTextComponent("none"), new GrimoireGui());
            return new ActionResult<>(ActionResultType.SUCCESS, itemStack);
        }

        return new ActionResult<>(ActionResultType.SUCCESS, itemStack);
    }
}
