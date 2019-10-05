package com.root.sorcery.item;

import com.root.sorcery.spell.Spell;
import com.root.sorcery.spell.SpellUseContext;
import com.root.sorcery.spellcasting.SpellcastingCapability;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.item.UseAction;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class SpellcastingItem extends ItemMod
{

    @Override
    public int getUseDuration(ItemStack stack)
    {
        return 100;
    }

    @Override
    public UseAction getUseAction(ItemStack stack){
        return UseAction.BOW;
    }

    // When item is used on an entity
    @Override
    public boolean itemInteractionForEntity(ItemStack stack, PlayerEntity playerIn, LivingEntity target, Hand hand) {

        World worldIn = playerIn.getEntityWorld();
        SpellUseContext spellContext = new SpellUseContext(worldIn, playerIn.getPosition(), playerIn, hand, stack, target);
        Spell spellToCast = getSpellFromEntity(playerIn);

        if (spellToCast.getCastDuration() > 0){
            ActionResult<ItemStack> actionItem =  onItemRightClick(worldIn, playerIn, hand);
            return true;
        }

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
        Spell spellToCast = getSpellFromEntity(context.getPlayer());

        if (spellToCast.getCastDuration() == 0)
        {
            return castSpell(spellContext);
        }
        else
        {
            return onItemRightClick(context.getWorld(), context.getPlayer(), context.getHand()).getType();
        }

    }

    // When item is used not targeting anything
    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn)
    {
        Spell spellToCast = getSpellFromEntity(playerIn);
        ItemStack itemStack = playerIn.getHeldItem(handIn);
        // If instant cast spell
        if (spellToCast.getCastDuration() == 0)
        {
            SpellUseContext spellContext = new SpellUseContext(worldIn, playerIn, handIn);
            ActionResultType actionResultType = castSpell(spellContext);
            return new ActionResult<>(actionResultType, itemStack);
        }
        else
        {
            playerIn.setActiveHand(handIn);
            return new ActionResult<>(ActionResultType.SUCCESS, itemStack);
        }

    }

    @Override
    public ItemStack onItemUseFinish(ItemStack stack, World worldIn, LivingEntity entityLiving)
    {
        SpellUseContext spellContext = new SpellUseContext(worldIn, entityLiving, entityLiving.getActiveHand());
        castSpell(spellContext);
        return stack;
    }



    // Actually casting a spell
    public ActionResultType castSpell(SpellUseContext context)
    {
        if (!context.getWorld().isRemote) {

            Spell spellToCast = getSpellFromEntity(context.getPlayer());


            ActionResultType actionResultType = spellToCast.cast(context);
            return actionResultType;
        }
        else
        {
            return ActionResultType.SUCCESS;
        }

    }

    private static Spell getSpellFromEntity(LivingEntity entity)
    {
        ResourceLocation spellName = entity.getCapability(SpellcastingCapability.SPELLCASTING).orElseThrow(NullPointerException::new).getActiveSpell();

        return GameRegistry.findRegistry(Spell.class).getValue(spellName);
    }

}
