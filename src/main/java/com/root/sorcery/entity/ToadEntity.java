package com.root.sorcery.entity;

import net.minecraft.entity.AgeableEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.controller.JumpController;
import net.minecraft.entity.ai.controller.MovementController;
import net.minecraft.entity.ai.goal.LookAtGoal;
import net.minecraft.entity.ai.goal.WaterAvoidingRandomWalkingGoal;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.pathfinding.Path;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;

public class ToadEntity extends TameableEntity
{
    protected static final DataParameter<Integer> TOAD_TYPE = EntityDataManager.createKey(ToadEntity.class, DataSerializers.VARINT);
    private int jumpTicks;
    private int jumpDuration;
    private boolean wasOnGround;
    private int currentMoveTypeDuration;

    protected ToadEntity(EntityType<? extends TameableEntity> type, World worldIn)
    {
        super(type, worldIn);
        this.jumpController = new ToadEntity.JumpHelperController(this);
        this.moveController = new ToadEntity.MoveHelperController(this);
        this.setMovementSpeed(1.0D);
    }

    protected void registerAttributes()
    {
        super.registerAttributes();
        this.getAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(10.0D);
        this.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.3D);
    }

    protected void registerGoals()
    {
        this.goalSelector.addGoal(1, new ToadEntity.PanicGoal(this, 2.2D));
        this.goalSelector.addGoal(4, new ToadEntity.AvoidEntityGoal<>(this, PlayerEntity.class, 8.0F, 2.2D, 2.2D));
        this.goalSelector.addGoal(6, new WaterAvoidingRandomWalkingGoal(this, 0.5D));
        this.goalSelector.addGoal(11, new LookAtGoal(this, PlayerEntity.class, 10.0F));
    }

    public void updateAITasks()
    {
        if (this.currentMoveTypeDuration > 0)
        {
            --this.currentMoveTypeDuration;
        }

        if (this.onGround)
        {
            if (!this.wasOnGround)
            {
                this.setJumping(false);
                this.checkLandingDelay();
            }

            if (this.currentMoveTypeDuration == 0)
            {
                LivingEntity livingentity = this.getAttackTarget();
                if (livingentity != null && this.getDistanceSq(livingentity) < 16.0D)
                {
                    this.calculateRotationYaw(livingentity.posX, livingentity.posZ);
                    this.moveController.setMoveTo(livingentity.posX, livingentity.posY, livingentity.posZ, this.moveController.getSpeed());
                    this.startJumping();
                    this.wasOnGround = true;
                }
            }

            ToadEntity.JumpHelperController toadentity$jumphelpercontroller = (ToadEntity.JumpHelperController)this.jumpController;
            if (!toadentity$jumphelpercontroller.getIsJumping())
            {
                if (this.moveController.isUpdating() && this.currentMoveTypeDuration == 0)
                {
                    Path path = this.navigator.getPath();
                    Vec3d vec3d = new Vec3d(this.moveController.getX(), this.moveController.getY(), this.moveController.getZ());
                    if (path != null && path.getCurrentPathIndex() < path.getCurrentPathLength())
                    {
                        vec3d = path.getPosition(this);
                    }

                    this.calculateRotationYaw(vec3d.x, vec3d.z);
                    this.startJumping();
                }
            } else if (!toadentity$jumphelpercontroller.canJump())
            {
                this.enableJumpControl();
            }
        }

        this.wasOnGround = this.onGround;
    }

    private void calculateRotationYaw(double x, double z)
    {
        this.rotationYaw = (float)(MathHelper.atan2(z - this.posZ, x - this.posX) * (double)(180F / (float)Math.PI)) - 90.0F;
    }

    private void enableJumpControl()
    {
        ((ToadEntity.JumpHelperController)this.jumpController).setCanJump(true);
    }

    private void disableJumpControl()
    {
        ((ToadEntity.JumpHelperController)this.jumpController).setCanJump(false);
    }

    private void updateMoveTypeDuration()
    {
        if (this.moveController.getSpeed() < 2.2D)
        {
            this.currentMoveTypeDuration = 10;
        } else {
            this.currentMoveTypeDuration = 1;
        }

    }

    private void checkLandingDelay()
    {
        this.updateMoveTypeDuration();
        this.disableJumpControl();
    }

    /**
     * Called frequently so the entity can update its state every tick as required. For example, zombies and skeletons
     * use this to react to sunlight and start to burn.
     */
    public void livingTick()
    {
        super.livingTick();
        if (this.jumpTicks != this.jumpDuration)
        {
            ++this.jumpTicks;
        } else if (this.jumpDuration != 0)
        {
            this.jumpTicks = 0;
            this.jumpDuration = 0;
            this.setJumping(false);
        }

    }

    @Nullable
    @Override
    public AgeableEntity createChild(AgeableEntity ageable)
    {
        return null;
    }

    protected void registerData()
    {
        super.registerData();
        this.dataManager.register(TOAD_TYPE, 0);
    }

    public void writeAdditional(CompoundNBT compound)
    {
        super.writeAdditional(compound);
        compound.putInt("toadType", this.getToadTypeInt());
    }

    public void readAdditional(CompoundNBT compound)
    {
        super.readAdditional(compound);
        this.setToadTypeInt(compound.getInt("toadType"));
    }

    public int getToadTypeInt()
    {
        return this.dataManager.get(TOAD_TYPE);
    }

    public void setToadTypeInt(int toadType)
    {
        this.dataManager.set(TOAD_TYPE, toadType);
    }

    protected float getJumpUpwardsMotion()
    {
        if (!this.collidedHorizontally && (!this.moveController.isUpdating() || !(this.moveController.getY() > this.posY + 0.5D)))
        {
            Path path = this.navigator.getPath();
            if (path != null && path.getCurrentPathIndex() < path.getCurrentPathLength())
            {
                Vec3d vec3d = path.getPosition(this);
                if (vec3d.y > this.posY + 0.5D)
                {
                    return 0.5F;
                }
            }

            return this.moveController.getSpeed() <= 0.6D ? 0.2F : 0.3F;
        } else {
            return 0.5F;
        }
    }

    /**
     * Causes this entity to do an upwards motion (jumping).
     */
    protected void jump()
    {
        super.jump();
        double d0 = this.moveController.getSpeed();
        if (d0 > 0.0D)
        {
            double d1 = func_213296_b(this.getMotion());
            if (d1 < 0.01D)
            {
                this.moveRelative(0.1F, new Vec3d(0.0D, 0.0D, 1.0D));
            }
        }

        if (!this.world.isRemote)
        {
            this.world.setEntityState(this, (byte)1);
        }

    }

    @OnlyIn(Dist.CLIENT)
    public void handleStatusUpdate(byte id)
    {
        if (id == 1)
        {
            this.createRunningParticles();
            this.jumpDuration = 10;
            this.jumpTicks = 0;
        } else {
            super.handleStatusUpdate(id);
        }

    }

    @OnlyIn(Dist.CLIENT)
    public float getJumpCompletion(float p_175521_1_)
    {
        return this.jumpDuration == 0 ? 0.0F : ((float)this.jumpTicks + p_175521_1_) / (float)this.jumpDuration;
    }

    public void setMovementSpeed(double newSpeed)
    {
        this.getNavigator().setSpeed(newSpeed);
        this.moveController.setMoveTo(this.moveController.getX(), this.moveController.getY(), this.moveController.getZ(), newSpeed);
    }

    public void setJumping(boolean jumping)
    {
        super.setJumping(jumping);
        if (jumping)
        {
            this.playSound(this.getJumpSound(), this.getSoundVolume(), ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.0F) * 0.8F);
        }

    }

    protected SoundEvent getJumpSound()
    {
        return SoundEvents.ENTITY_RABBIT_JUMP;
    }

    public void startJumping()
    {
        this.setJumping(true);
        this.jumpDuration = 10;
        this.jumpTicks = 0;
    }

    public class JumpHelperController extends JumpController
    {
        private final ToadEntity toad;
        private boolean canJump;

        public JumpHelperController(ToadEntity toad)
        {
            super(toad);
            this.toad = toad;
        }

        public boolean getIsJumping()
        {
            return this.isJumping;
        }

        public boolean canJump()
        {
            return this.canJump;
        }

        public void setCanJump(boolean canJumpIn)
        {
            this.canJump = canJumpIn;
        }

        /**
         * Called to actually make the entity jump if isJumping is true.
         */
        public void tick()
        {
            if (this.isJumping)
            {
                this.toad.startJumping();
                this.isJumping = false;
            }

        }
    }

    static class MoveHelperController extends MovementController
    {
        private final ToadEntity toad;
        private double nextJumpSpeed;

        public MoveHelperController(ToadEntity toad)
        {
            super(toad);
            this.toad = toad;
        }

        public void tick()
        {
            if (this.toad.onGround && !this.toad.isJumping && !((ToadEntity.JumpHelperController)this.toad.jumpController).getIsJumping())
            {
                this.toad.setMovementSpeed(0.0D);
            } else if (this.isUpdating())
            {
                this.toad.setMovementSpeed(this.nextJumpSpeed);
            }

            super.tick();
        }

        /**
         * Sets the speed and location to move to
         */
        public void setMoveTo(double x, double y, double z, double speedIn)
        {
            if (this.toad.isInWater())
            {
                speedIn = 1.5D;
            }

            super.setMoveTo(x, y, z, speedIn);
            if (speedIn > 0.0D)
            {
                this.nextJumpSpeed = speedIn;
            }

        }
    }

    static class AvoidEntityGoal<T extends LivingEntity> extends net.minecraft.entity.ai.goal.AvoidEntityGoal<T>
    {
        private final ToadEntity toad;

        public AvoidEntityGoal(ToadEntity toad, Class<T> p_i46403_2_, float p_i46403_3_, double p_i46403_4_, double p_i46403_6_)
        {
            super(toad, p_i46403_2_, p_i46403_3_, p_i46403_4_, p_i46403_6_);
            this.toad = toad;
        }
        /**
         * Returns whether the EntityAIBase should begin execution.
         */
        public boolean shouldExecute()
        {
            return super.shouldExecute();
        }
    }

    static class PanicGoal extends net.minecraft.entity.ai.goal.PanicGoal
    {
        private final ToadEntity toad;

        public PanicGoal(ToadEntity toad, double speedIn)
        {
            super(toad, speedIn);
            this.toad = toad;
        }

        /**
         * Keep ticking a continuous task that has already been started
         */
        public void tick()
        {
            super.tick();
            this.toad.setMovementSpeed(this.speed);
        }
    }

}
