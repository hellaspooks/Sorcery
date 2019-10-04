package com.root.sorcery.item;

import com.root.sorcery.Constants;
import com.root.sorcery.spell.Spell;
import com.root.sorcery.spell.SpellUseContext;
import com.root.sorcery.spellcasting.ISpellcasting;
import com.root.sorcery.spellcasting.SpellcastingDefault;
import com.root.sorcery.spellcasting.SpellcastingProvider;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class SigilItem extends ItemMod
{

    public SigilItem()
    {
        super();
    }

    // Below this are testing methods for spell casting, still very very rough.

    // When item is used on an entity
    @Override
    public boolean itemInteractionForEntity(ItemStack stack, PlayerEntity playerIn, LivingEntity target, Hand hand) {

        World worldIn = playerIn.getEntityWorld();
        SpellUseContext spellContext = new SpellUseContext(worldIn, playerIn.getPosition(), null, playerIn, hand, stack, target);

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

        ItemStack itemStack = playerIn.getHeldItem(handIn);

        SpellUseContext spellContext = new SpellUseContext(worldIn, playerIn.getPosition(), null, playerIn, handIn, itemStack, null);

        ActionResultType actionResultType = castSpell(spellContext);

        return new ActionResult<>(actionResultType, itemStack);
    }

    // Actually casting a spell
    public ActionResultType castSpell(SpellUseContext context)
    {
        if (!context.getWorld().isRemote) {
            ISpellcasting playerCap = context.getPlayer().getCapability(SpellcastingProvider.SPELLCASTING).orElseThrow(NullPointerException::new);

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
