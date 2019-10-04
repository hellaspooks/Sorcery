package com.root.sorcery.item;

import com.root.sorcery.spell.Spell;
import com.root.sorcery.spell.SpellUseContext;
import com.root.sorcery.spellcasting.ISpellcasting;
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
        return 72000;
    }

    @Override
    public UseAction getUseAction(ItemStack stack)
    {
        return UseAction.BOW;
    }

    @Override
    public void onPlayerStoppedUsing(ItemStack stack, World worldIn, LivingEntity entityLiving, int timeLeft)
    {
    }

    // When item is used on an entity
    @Override
    public boolean itemInteractionForEntity(ItemStack stack, PlayerEntity playerIn, LivingEntity target, Hand hand) {

        World worldIn = playerIn.getEntityWorld();
        SpellUseContext spellContext = new SpellUseContext(worldIn, playerIn.getPosition(), playerIn, hand, stack, target);

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
        System.out.println("In on item right click");
        SpellUseContext spellContext = new SpellUseContext(worldIn, playerIn, handIn);

        ItemStack itemStack = playerIn.getHeldItem(handIn);
        ActionResultType actionResultType = castSpell(spellContext);

        return new ActionResult<>(actionResultType, itemStack);
    }


    // Actually casting a spell
    public ActionResultType castSpell(SpellUseContext context)
    {
        if (!context.getWorld().isRemote) {
            ISpellcasting playerCap = context.getPlayer().getCapability(SpellcastingCapability.SPELLCASTING).orElseThrow(NullPointerException::new);

            ResourceLocation spellToTest = playerCap.getActiveSpell();

            ActionResultType actionResultType = GameRegistry.findRegistry(Spell.class).getValue(spellToTest).cast(context);
            return actionResultType;
        }
        else
        {
            return ActionResultType.SUCCESS;
        }

    }
}
