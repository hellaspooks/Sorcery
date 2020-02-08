package com.root.sorcery.item;

import com.root.sorcery.tileentity.ArcanaStorageTile;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;


public class LinkingItem extends Item
{

    public LinkingItem(Properties properties)
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
            System.out.println("full nbt");
            System.out.println(nbt);

            BlockPos pos = new BlockPos(context.getPos());

            System.out.println("Hit pos:");
            System.out.println(pos);


            TileEntity tile =  world.getTileEntity(pos);
            System.out.println("hit tile");
            System.out.println(tile);

            if (tile instanceof ArcanaStorageTile)
            {
                if (context.getPlayer().isSneaking())
                {
                    System.out.println("setting link pos");
                    setLinkPos(context.getItem(), pos);
                } else {

                    if (nbt.contains("linkPos"))
                    {
                        CompoundNBT posTag = (CompoundNBT) nbt.get("linkPos");
                        BlockPos linkPos = new BlockPos(posTag.getInt("x"), posTag.getInt("y"), posTag.getInt("z"));


                        System.out.println("link pos");
                        System.out.println(linkPos);

                        TileEntity linkTile = world.getTileEntity(linkPos);

                        System.out.println("link tile");
                        System.out.println(linkTile);

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
