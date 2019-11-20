package com.root.sorcery.item;

import com.root.sorcery.spell.CastType;
import com.root.sorcery.spell.Spell;
import com.root.sorcery.spell.SpellUseContext;
import com.root.sorcery.utils.Utils;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.item.UseAction;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

import javax.annotation.Nullable;

import static com.root.sorcery.utils.Utils.getSpellFromEntity;

public class SpellcastingItem extends Item
{
    public SpellcastingItem(Item.Properties properties)
    {
        super(properties);
    }

    @Override
    public int getUseDuration(ItemStack stack)
    {
        return 1;
    }

    @Override
    public UseAction getUseAction(ItemStack stack){
        return UseAction.BOW;
    }

    // When item is used on an entity
    @Override
    public boolean itemInteractionForEntity(ItemStack stack, PlayerEntity playerIn, LivingEntity target, Hand hand) {
        SpellUseContext spellContext = new SpellUseContext(playerIn.getEntityWorld(), playerIn.getPosition(), playerIn, hand, stack, target);
        ActionResultType actionResultType = castSpell(spellContext);
        if (actionResultType == ActionResultType.SUCCESS)
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    // When item is used on a block
    @Override
    public ActionResultType onItemUse(ItemUseContext context)
    {
        SpellUseContext spellContext = new SpellUseContext(context);
        return castSpell(spellContext);
    }

    // When item is used not targeting anything
    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn)
    {
        SpellUseContext spellContext = new SpellUseContext(worldIn, playerIn, handIn);
        ActionResultType actionResultType = castSpell(spellContext);

        ItemStack itemStack = playerIn.getHeldItem(handIn);
        return new ActionResult<>(actionResultType, itemStack);
    }

    // Actually casting a spell
    public ActionResultType castSpell(SpellUseContext context)
    {
        Spell spellToCast = getSpellFromEntity(context.getPlayer());

        // If duration spell, set active hand and pass
        if ( spellToCast.getCastType() == CastType.DURATION || spellToCast.getCastType() == CastType.CHANNELED)
        {
            context.getPlayer().setActiveHand(context.getHand());
            return ActionResultType.PASS;
        }
        // Otherwise, cast the spell
        return spellToCast.cast(context);
    }

    // Duration spells finish here
    @Override
    public ItemStack onItemUseFinish(ItemStack stack, World worldIn, LivingEntity entityLiving)
    {
        SpellUseContext spellContext = new SpellUseContext(worldIn, entityLiving, entityLiving.getActiveHand());
        Spell spellToCast = getSpellFromEntity(entityLiving);
        spellToCast.cast(spellContext);
        return stack;
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
