package com.root.sorcery.entity.model;

import com.root.sorcery.entity.ToadEntity;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.entity.model.RendererModel;
import net.minecraft.util.math.MathHelper;

public class ToadEntityModel extends EntityModel<ToadEntity>
{
    public RendererModel Body;
    public RendererModel Tongue;
    public RendererModel RightLeg;
    public RendererModel LeftLeg;
    public RendererModel RightArm;
    public RendererModel LeftArm;
    public RendererModel RightEye;
    public RendererModel LeftEye;
    public RendererModel RightFoot;
    public RendererModel LeftFoot;
    private float jumpRotation;


    public ToadEntityModel()
    {
        this.textureWidth = 32;
        this.textureHeight = 32;
        this.Body = new RendererModel(this, 1, 1);
        this.Body.setRotationPoint(0.0F, 20.1F, 0.0F);
        this.Body.addBox(-3.0F, -4.0F, -4.0F, 6, 6, 6, 0.0F);
        this.RightLeg = new RendererModel(this, 20, 20);
        this.RightLeg.setRotationPoint(-2.5F, 1.0F, 1.5F);
        this.RightLeg.addBox(-1.5F, -1.0F, -2.0F, 2, 3, 4, 0.0F);
        this.LeftEye = new RendererModel(this, 0, 13);
        this.LeftEye.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.LeftEye.addBox(2.5F, -3.0F, -3.0F, 1, 3, 3, 0.0F);
        this.LeftArm = new RendererModel(this, 15, 20);
        this.LeftArm.setRotationPoint(2.5F, 1.5F, -3.5F);
        this.LeftArm.addBox(0.0F, 0.0F, -1.0F, 1, 2, 2, 0.0F);
        this.LeftLeg = new RendererModel(this, 20, 20);
        this.LeftLeg.setRotationPoint(2.0F, 1.0F, 1.5F);
        this.LeftLeg.addBox(0.0F, -1.0F, -2.0F, 2, 3, 4, 0.0F);
        this.LeftFoot = new RendererModel(this, 22, 27);
        this.LeftFoot.setRotationPoint(0.0F, 2.0F, 0.0F);
        this.LeftFoot.addBox(0.5F, 0.0F, -3.0F, 1, 1, 4, 0.0F);
        this.Tongue = new RendererModel(this, 0, 0);
        this.Tongue.setRotationPoint(0.0F, 0.0F, -4.0F);
        this.Tongue.addBox(-1.0F, 0.0F, 0.0F, 2, 1, 0, 0.0F);
        this.setRotateAngle(Tongue, -0.3490658503988659F, 0.0F, 0.0F);
        this.RightArm = new RendererModel(this, 15, 20);
        this.RightArm.setRotationPoint(-2.5F, 1.5F, -3.5F);
        this.RightArm.addBox(-1.0F, 0.0F, -1.0F, 1, 2, 2, 0.0F);
        this.RightFoot = new RendererModel(this, 22, 27);
        this.RightFoot.setRotationPoint(0.0F, 2.0F, 0.0F);
        this.RightFoot.addBox(-1.0F, 0.0F, -3.0F, 1, 1, 4, 0.0F);
        this.RightEye = new RendererModel(this, 0, 13);
        this.RightEye.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.RightEye.addBox(-3.5F, -3.0F, -3.0F, 1, 3, 3, 0.0F);
        this.Body.addChild(this.RightLeg);
        this.Body.addChild(this.LeftEye);
        this.Body.addChild(this.LeftArm);
        this.Body.addChild(this.LeftLeg);
        this.LeftLeg.addChild(this.LeftFoot);
        this.Body.addChild(this.Tongue);
        this.Body.addChild(this.RightArm);
        this.RightLeg.addChild(this.RightFoot);
        this.Body.addChild(this.RightEye);

    }

    @Override
    public void render(ToadEntity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale)
    {
        setRotationAngles(entityIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
        this.Body.render(scale);
    }

    @Override
    public void setRotationAngles(ToadEntity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor)
    {
        float f = ageInTicks - (float)entityIn.ticksExisted;
        this.jumpRotation = MathHelper.sin(entityIn.getJumpCompletion(f) * (float)Math.PI);
        this.LeftLeg.rotateAngleX = (this.jumpRotation * 50.0F - 21.0F) * ((float)Math.PI / 180F);
        this.RightLeg.rotateAngleX = (this.jumpRotation * 50.0F - 21.0F) * ((float)Math.PI / 180F);
        this.LeftArm.rotateAngleX = (this.jumpRotation * -40.0F - 11.0F) * ((float)Math.PI / 180F);
        this.RightArm.rotateAngleX = (this.jumpRotation * -40.0F - 11.0F) * ((float)Math.PI / 180F);

    }

    public void setRotateAngle(RendererModel modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }

    public void setLivingAnimations(ToadEntity entityIn, float limbSwing, float limbSwingAmount, float partialTick) {
        super.setLivingAnimations(entityIn, limbSwing, limbSwingAmount, partialTick);
        this.jumpRotation = MathHelper.sin(entityIn.getJumpCompletion(partialTick) * (float)Math.PI);
    }
}
