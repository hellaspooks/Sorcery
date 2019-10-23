package com.root.sorcery.entity.renderer;

import com.root.sorcery.Constants;
import com.root.sorcery.entity.ToadEntity;
import com.root.sorcery.entity.model.ToadModel;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nullable;

public class ToadRenderer extends MobRenderer<ToadEntity, ToadModel>
{
    private static final ResourceLocation TEXTURE_GREEN = new ResourceLocation(Constants.MODID, "textures/entity/toad_green.png");
    private static final ResourceLocation TEXTURE_VOLCANIC = new ResourceLocation(Constants.MODID, "textures/entity/toad_volcanic.png");
    private static final ResourceLocation TEXTURE_ENDER = new ResourceLocation(Constants.MODID, "textures/entity/toad_ender.png");

    public ToadRenderer(EntityRendererManager manager)
    {
        super(manager, new ToadModel(), 0.5f);
    }

    @Nullable
    @Override
    protected ResourceLocation getEntityTexture(ToadEntity entity)
    {
        switch (entity.getToadTypeInt())
        {
            case 1:
                return TEXTURE_VOLCANIC;
            case 2:
                return TEXTURE_ENDER;
            default:
                return TEXTURE_GREEN;
        }
    }
}
