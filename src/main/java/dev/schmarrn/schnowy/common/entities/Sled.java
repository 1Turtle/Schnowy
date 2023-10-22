package dev.schmarrn.schnowy.common.entities;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.animal.WaterAnimal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class Sled extends Entity {
	private float deltaRotation;

	private int lerpSteps;

	private double lerpX;
	private double lerpY;
	private double lerpZ;

	private double lerpYRot;
	private double lerpXRot;

	private boolean inputLeft;
	private boolean inputRight;
	private boolean inputUp;
	private boolean inputDown;

	public Sled(EntityType<?> variant, Level world) {
		super(variant, world);
	}

	protected Entity.@NotNull MovementEmission getMovementEmission() {
		return MovementEmission.EVENTS;
	}

	public void tick() {
		super.tick();
		this.tickLerp();
		if (this.isControlledByLocalInstance()) {
			if (this.level().isClientSide) {
				this.controlBoat();
			}
			this.move(MoverType.SELF, this.getDeltaMovement());
		} else {
			this.setDeltaMovement(Vec3.ZERO);
		}

		this.checkInsideBlocks();
		List<Entity> list = this.level().getEntities(this, this.getBoundingBox().inflate(0.20000000298023224, -0.009999999776482582, 0.20000000298023224), EntitySelector.pushableBy(this));
		if (!list.isEmpty()) {
			boolean bl = !this.level().isClientSide && !(this.getControllingPassenger() instanceof Player);

			for(int j = 0; j < list.size(); ++j) {
				Entity entity = (Entity)list.get(j);
				if (!entity.hasPassenger(this)) {
					if (bl && this.getPassengers().size() < this.getMaxPassengers() && !entity.isPassenger() && this.hasEnoughSpaceFor(entity) && entity instanceof LivingEntity && !(entity instanceof WaterAnimal) && !(entity instanceof Player)) {
						entity.startRiding(this);
					} else {
						this.push(entity);
					}
				}
			}
		}
	}

	private void tickLerp() {
		if (this.isControlledByLocalInstance()) {
			this.lerpSteps = 0;
			this.syncPacketPositionCodec(this.getX(), this.getY(), this.getZ());
		}

		if (this.lerpSteps > 0) {
			double d = this.getX() + (this.lerpX - this.getX()) / (double)this.lerpSteps;
			double e = this.getY() + (this.lerpY - this.getY()) / (double)this.lerpSteps;
			double f = this.getZ() + (this.lerpZ - this.getZ()) / (double)this.lerpSteps;
			double g = Mth.wrapDegrees(this.lerpYRot - (double)this.getYRot());
			this.setYRot(this.getYRot() + (float)g / (float)this.lerpSteps);
			this.setXRot(this.getXRot() + (float)(this.lerpXRot - (double)this.getXRot()) / (float)this.lerpSteps);
			--this.lerpSteps;
			this.setPos(d, e, f);
			this.setRot(this.getYRot(), this.getXRot());
		}
	}

	private void controlBoat() {
		if (this.isVehicle()) {
			float f = 0.0F;
			if (this.inputLeft) {
				--this.deltaRotation;
			}

			if (this.inputRight) {
				++this.deltaRotation;
			}

			if (this.inputRight != this.inputLeft && !this.inputUp && !this.inputDown) {
				f += 0.005F;
			}

			this.setYRot(this.getYRot() + this.deltaRotation);
			if (this.inputUp) {
				f += 0.04F;
			}

			if (this.inputDown) {
				f -= 0.005F;
			}

			this.setDeltaMovement(this.getDeltaMovement().add(Mth.sin(-this.getYRot() * 0.017453292F) * f, 0.0, (double)(Mth.cos(this.getYRot() * 0.017453292F) * f)));
		}
	}

	protected float getEyeHeight(Pose pose, EntityDimensions dimensions) {
		return dimensions.height;
	}

	public boolean canCollideWith(Entity other) {
		return canVehicleCollide(this, other);
	}

	public static boolean canVehicleCollide(Entity entity, Entity other) {
		return (other.canBeCollidedWith() || other.isPushable()) && !entity.isPassengerOfSameVehicle(other);
	}

	public boolean canBeCollidedWith() {
		return true;
	}

	public boolean isPushable() {
		return true;
	}

	public boolean isPickable() {
		return !this.isRemoved();
	}

	public void push(Entity entity) {
		if (entity instanceof Sled) {
			if (entity.getBoundingBox().minY < this.getBoundingBox().maxY) {
				super.push(entity);
			}
		} else if (entity.getBoundingBox().minY <= this.getBoundingBox().minY) {
			super.push(entity);
		}
	}

	public boolean hasEnoughSpaceFor(Entity entity) {
		return entity.getBbWidth() < this.getBbWidth()*1.25;
	}

	protected void clampRotation(Entity entity) {
		entity.setYBodyRot(this.getYRot());
		float f = Mth.wrapDegrees(entity.getYRot() - this.getYRot());
		float g = Mth.clamp(f, -105.0F, 105.0F);
		entity.yRotO += g - f;
		entity.setYRot(entity.getYRot() + g - f);
		entity.setYHeadRot(entity.getYRot());
	}

	public void onPassengerTurned(Entity passenger) {
		this.clampRotation(passenger);
	}

	protected int getMaxPassengers() {
		return 2;
	}

	@Override
	protected void defineSynchedData() {

	}

	@Override
	protected void readAdditionalSaveData(CompoundTag nbt) {

	}

	@Override
	protected void addAdditionalSaveData(CompoundTag nbt) {

	}
}
