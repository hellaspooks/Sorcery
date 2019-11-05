package com.root.sorcery.client.renderer;

import com.root.sorcery.tileentity.PodiumTile;
import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockRendererDispatcher;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.client.model.ModelDataManager;
import net.minecraftforge.client.model.animation.TileEntityRendererFast;
import net.minecraftforge.client.model.data.IModelData;

import java.util.Random;

public class PodiumRenderer extends TileEntityRendererFast
{

    protected static BlockRendererDispatcher blockRenderer;
    protected static ItemRenderer itemRenderer;

    @Override
    public void renderTileEntityFast(TileEntity te, double x, double y, double z, float partialTicks, int destroyStage, BufferBuilder buffer)
    {
        if (!(te instanceof PodiumTile))
        {
            return;
        }

        if(blockRenderer == null) blockRenderer = Minecraft.getInstance().getBlockRendererDispatcher();
        if(itemRenderer == null) itemRenderer = Minecraft.getInstance().getItemRenderer();

        BlockPos pos = te.getPos();
        net.minecraft.world.IEnviromentBlockReader world = MinecraftForgeClient.getRegionRenderCache(te.getWorld(), pos);
        BlockState state = world.getBlockState(pos);
        ItemStack storedItem = ((PodiumTile) te).getItemHandler().getStackInSlot(0);

        IBakedModel model = itemRenderer.getModelWithOverrides(storedItem);

        IModelData data = model.getModelData(world, pos, state, ModelDataManager.getModelData(te.getWorld(), pos));

        blockRenderer.getBlockModelRenderer().renderModel(world, model, state, pos.add(0.5, 1, 0.5), buffer, false, new Random(), 42, data);

    }
}
