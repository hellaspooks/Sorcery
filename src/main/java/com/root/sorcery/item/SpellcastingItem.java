package com.root.sorcery.item;

import com.root.sorcery.spell.CastType;
import com.root.sorcery.spell.ModSpell;
import com.root.sorcery.spell.Spell;
import com.root.sorcery.spell.SpellUseContext;
import com.root.sorcery.tileentity.ArcanaStorageTile;
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

import static com.root.sorcery.utils.Utils.getSpellFromProvider;

public class SpellcastingItem extends Item
{
    public SpellcastingItem(Item.Properties properties)
    {
        super(properties);
    }

    @Override
    public int getUseDuration(ItemStack stack)
    {
        int castDuration = getSpellFromProvider(stack).getCastDuration();
        return castDuration;
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
        // if targeting arcana source, instead draw in arcana
        if (context.getWorld().getTileEntity(context.getPos()) instanceof ArcanaStorageTile)
        {
            return castSpellOverride(spellContext, ModSpell.ARCANA_DRAIN_SPELL);
        }
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
        Spell spellToCast = getSpellFromProvider(context.getItem());
        CastType castType = spellToCast.getCastType();

        // If duration or channeled spell, set active hand and pass
        if ( castType == CastType.DURATION || castType == CastType.CHANNELED)
        {
            context.getPlayer().setActiveHand(context.getHand());
            return ActionResultType.SUCCESS;
        }
        // Otherwise, cast the spell
        return spellToCast.castFinal(context);
    }

    public ActionResultType castSpellOverride(SpellUseContext context, Spell spell)
    {
        CastType castType = spell.getCastType();

        // If duration or channeled spell, set active hand and pass
        if ( castType == CastType.DURATION || castType == CastType.CHANNELED)
        {
            context.getPlayer().setActiveHand(context.getHand());
            return ActionResultType.SUCCESS;
        }
        // Otherwise, cast the spell
        return spell.castFinal(context);
    }


    // Duration spells finish here
    @Override
    public ItemStack onItemUseFinish(ItemStack stack, World worldIn, LivingEntity entityLiving)
    {
        SpellUseContext spellContext = new SpellUseContext(worldIn, entityLiving, entityLiving.getActiveHand());
        Spell spellToCast = getSpellFromProvider(stack);
        spellToCast.castFinal(spellContext);
        return stack;
    }

    // Needed to make channeled spells work
    @Override
    public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged)
    {
        return false;
    }

    @Override
    public boolean canContinueUsing(ItemStack oldStack, ItemStack newStack)
    {
        return true;
    }

    // NBT handlers
    @Override
    public CompoundNBT getShareTag(ItemStack stack)
    {
        CompoundNBT baseTag = stack.getOrCreateTag();
        CompoundNBT arcanaTag = Utils.getArcanaCap(stack).serializeNBT();
        CompoundNBT spellTag = Utils.getSpellCap(stack).serializeNBT();
        baseTag.put("arcanaCap", arcanaTag);
        baseTag.put("spellCap", spellTag);
        return baseTag;
    }

    @Override
    public void readShareTag(ItemStack stack, @Nullable CompoundNBT nbt)
    {
        Utils.getArcanaCap(stack).deserializeNBT(nbt.getCompound("arcanaCap"));
        Utils.getSpellCap(stack).deserializeNBT(nbt.getCompound("spellCap"));
        stack.setTag(nbt);
    }
}
