package com.example.planes.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.listener.ServerPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.EntitySpawnS2CPacket;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class PlaneEntity extends Entity {
	private static final float DRAG = 0.99f;
	private static final float GRAVITY = 0.04f;
	private static final float ACCELERATION = 0.15f;
	private static final float MAX_SPEED = 1.5f;
	private static final float TURNING_SPEED = 3f;
	private static final float PITCH_SPEED = 2f;
	
	private PlaneType planeType;
	private float enginePower = 0f;
	private boolean ascending = false;
	private int damage = 0;
	private static final int MAX_DAMAGE = 100;

	public PlaneEntity(EntityType<?> type, World world) {
		super(type, world);
		this.planeType = PlaneType.BIPLANE;
	}

	public PlaneEntity(World world, double x, double y, double z, PlaneType type) {
		this((EntityType<PlaneEntity>) PlaneEntity.getEntityType(type), world);
		this.planeType = type;
		this.setPosition(x, y, z);
	}

	@Override
	public void tick() {
		super.tick();

		if (this.getWorld().isClient) {
			return;
		}

		// Получить седока (пилота)
		Entity pilot = this.getFirstPassenger();
		
		if (pilot instanceof PlayerEntity player) {
			// Управление движением
			Vec3d motionInput = Vec3d.ZERO;
			float pitch = 0;
			float yaw = 0;

			// Получить направление игрока
			double moveX = 0;
			double moveZ = 0;

			if (player.input.playerySneaking) {
				// Спуск
				this.velocity = this.velocity.multiply(0.98, 0.98, 0.98);
			} else if (player.input.jump) {
				// Подъём
				this.velocity = this.velocity.add(0, ACCELERATION * this.planeType.speedMultiplier, 0);
			}

			// Движение вперёд/назад и влево/вправо
			float forward = 0;
			float strafe = 0;

			if (player.input.pressingForward) forward += 1;
			if (player.input.pressingBack) forward -= 1;
			if (player.input.pressingLeft) strafe += 1;
			if (player.input.pressingRight) strafe -= 1;

			// Применить ускорение в направлении взгляда
			float yawRad = (float) Math.toRadians(this.getYaw());
			double accelX = (forward * Math.cos(yawRad) - strafe * Math.sin(yawRad)) * ACCELERATION * this.planeType.speedMultiplier;
			double accelZ = (forward * Math.sin(yawRad) + strafe * Math.cos(yawRad)) * ACCELERATION * this.planeType.speedMultiplier;

			this.velocity = this.velocity.add(accelX, 0, accelZ);

			// Ограничить скорость
			double horizontalSpeed = Math.sqrt(this.velocity.x * this.velocity.x + this.velocity.z * this.velocity.z);
			if (horizontalSpeed > MAX_SPEED * this.planeType.speedMultiplier) {
				double scale = (MAX_SPEED * this.planeType.speedMultiplier) / horizontalSpeed;
				this.velocity = new Vec3d(this.velocity.x * scale, this.velocity.y, this.velocity.z * scale);
			}

			// Применить сопротивление воздуха
			this.velocity = this.velocity.multiply(DRAG, 0.98, DRAG);

			// Гравитация
			this.velocity = this.velocity.subtract(0, GRAVITY, 0);

			// Разломать самолёт при падении
			if (this.getY() < -30) {
				this.damage = MAX_DAMAGE;
			}

			// Удалить самолёт если он сломан
			if (this.damage >= MAX_DAMAGE) {
				if (pilot instanceof PlayerEntity) {
					pilot.startRiding(null);
				}
				this.discard();
				return;
			}

			// Телепортировать игрока обратно в самолёт если он выходит
			if (!pilot.isRiding()) {
				this.discard();
				return;
			}
		} else {
			// Без пилота применить гравитацию и сопротивление
			this.velocity = this.velocity.multiply(0.99, 0.99, 0.99);
			this.velocity = this.velocity.subtract(0, 0.08, 0);
		}

		// Обновить позицию
		this.setPosition(this.getPos().add(this.velocity));

		// Вращение для визуализации направления движения
		if (Math.sqrt(this.velocity.x * this.velocity.x + this.velocity.z * this.velocity.z) > 0.1) {
			float targetYaw = (float) Math.toDegrees(Math.atan2(this.velocity.z, this.velocity.x)) - 90;
			this.setYaw(targetYaw);
		}

		// Проверка коллизий с земёй
		if (this.isOnGround()) {
			this.velocity = Vec3d.ZERO;
			this.damage = MAX_DAMAGE;
		}
	}

	@Override
	protected void readCustomDataFromNbt(NbtCompound nbt) {
		if (nbt.contains("PlaneType")) {
			this.planeType = PlaneType.valueOf(nbt.getString("PlaneType"));
		}
		this.damage = nbt.getInt("Damage");
	}

	@Override
	protected void writeCustomDataToNbt(NbtCompound nbt) {
		nbt.putString("PlaneType", this.planeType.name());
		nbt.putInt("Damage", this.damage);
	}

	@Override
	public boolean canAddPassenger(Entity passenger) {
		return this.getPassengerList().isEmpty();
	}

	@Override
	public Packet<ServerPlayPacketListener> createSpawnPacket() {
		return new EntitySpawnS2CPacket(this);
	}

	@Override
	public double getMountedHeightOffset() {
		return 0.3;
	}

	@Override
	public boolean collidesWith(Entity other) {
		return false;
	}

	@Override
	public boolean isCollidable() {
		return false;
	}

	public PlaneType getPlaneType() {
		return this.planeType;
	}

	public int getDamage() {
		return this.damage;
	}

	public void setPlaneType(PlaneType type) {
		this.planeType = type;
	}

	public static EntityType<PlaneEntity> getEntityType(PlaneType type) {
		return switch (type) {
			case BIPLANE -> PlaneEntity.BIPLANE_TYPE;
			case FIGHTER_WW1 -> PlaneEntity.FIGHTER_WW1_TYPE;
			case FIGHTER_WW2 -> PlaneEntity.FIGHTER_WW2_TYPE;
			case JET_FIGHTER -> PlaneEntity.JET_FIGHTER_TYPE;
			case AIRLINER -> PlaneEntity.AIRLINER_TYPE;
		};
	}

	// Static references to entity types (will be set in ModEntities)
	public static EntityType<PlaneEntity> BIPLANE_TYPE;
	public static EntityType<PlaneEntity> FIGHTER_WW1_TYPE;
	public static EntityType<PlaneEntity> FIGHTER_WW2_TYPE;
	public static EntityType<PlaneEntity> JET_FIGHTER_TYPE;
	public static EntityType<PlaneEntity> AIRLINER_TYPE;
}
