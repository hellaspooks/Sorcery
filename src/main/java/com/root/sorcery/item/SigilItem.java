package com.root.sorcery.item;

import com.root.sorcery.Constants;
import com.root.sorcery.spell.Spell;
import com.root.sorcery.spell.SpellUseContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class SigilItem extends ItemMod
{
    private ResourceLocation spellToTest = new ResourceLocation(Constants.MODID, "testspell");

    public SigilItem()
    {
        super();
    }

    // Below this are testing methods for spell casting, still very very rough.

    // When item is used on an entity
    @Override
    public boolean itemInteractionForEntity(ItemStack stack, PlayerEntity playerIn, LivingEntity target, Hand hand) {

        World worldIn = playerIn.getEntityWorld();
        SpellUseContext spellContext = new SpellUseContext(worldIn, null, playerIn.getPosition(), playerIn, hand, stack);

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

        SpellUseContext spellContext = new SpellUseContext(worldIn, null, playerIn.getPosition(), playerIn, handIn, itemStack);

        ActionResultType actionResultType = castSpell(spellContext);

        return new ActionResult<>(actionResultType, itemStack);
    }

    // Actually casting a spell
    public ActionResultType castSpell(SpellUseContext context)
    {
        ActionResultType actionResultType = GameRegistry.findRegistry(Spell.class).getValue(spellToTest).cast(context);
        return actionResultType;

    }
}
