package com.root.sorcery.entity;

import com.root.sorcery.Constants;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nullable;

public class ToadEntityRenderer extends MobRenderer<ToadEntity, ToadEntityModel>
{
    private static final ResourceLocation TEXTURE = new ResourceLocation(Constants.MODID, "textures/entity/toad_green.png");

    public ToadEntityRenderer(EntityRendererManager manager)
    {
        super(manager, new ToadEntityModel(), 0.5f);
    }

    @Nullable
    @Override
    protected ResourceLocation getEntityTexture(ToadEntity entity)
    {
        return TEXTURE;
    }
}
