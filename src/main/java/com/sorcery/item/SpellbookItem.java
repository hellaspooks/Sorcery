package com.sorcery.item;

import com.sorcery.inventory.SpellbookContainer;
import com.sorcery.inventory.SpellbookInventory;
import com.sorcery.spell.Spell;
import com.sorcery.utils.Utils;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.inventory.container.SimpleNamedContainerProvider;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

public class SpellbookItem extends Item
{
    public SpellbookItem(Properties properties)
    {
        super(properties);
    }

    public Spell getActiveSpell(ItemStack item)
    {
        return Utils.getSpellFromProvider(item);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, PlayerEntity player, Hand hand)
    {
        ItemStack stack = player.getHeldItem(hand);
        if (world.isRemote)
        {
            return new ActionResult<>(ActionResultType.SUCCESS, stack);
        } else {
            INamedContainerProvider container =  new SimpleNamedContainerProvider((id, playerInventory, openPlayer) -> {
                return new SpellbookContainer(id, playerInventory, new SpellbookInventory(stack, 9));
            }, stack.getDisplayName());
            player.openContainer(container);

        }
        return new ActionResult<>(ActionResultType.SUCCESS, stack);

    }

    @Override
    public CompoundNBT getShareTag(ItemStack stack) {
        if (!stack.hasTag()) {
            return null;
        }
        final CompoundNBT compound = stack.getTag().copy();
        compound.remove("Items");
        if (compound.isEmpty()) {
            return null;
        }
        return compound;
    }



}
