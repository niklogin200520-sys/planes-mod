package com.example.planes.item;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;
import com.example.planes.entity.PlaneEntity;
import com.example.planes.entity.PlaneType;

public class PlaneSpawnerItem extends Item {
	private final PlaneType planeType;

	public PlaneSpawnerItem(PlaneType planeType, Item.Settings settings) {
		super(settings);
		this.planeType = planeType;
	}

	@Override
	public ActionResult use(World world, PlayerEntity player, Hand hand) {
		if (!world.isClient) {
			// Найти точку спавна самолёта
			BlockHitResult hit = raycast(world, player, RaycastContext.FluidHandling.NONE);
			double x = player.getX();
			double y = player.getY() + 5;
			double z = player.getZ();

			if (hit.getType() != net.minecraft.util.hit.HitResult.Type.MISS) {
				BlockPos pos = hit.getBlockPos();
				x = pos.getX() + 0.5;
				y = pos.getY() + 5;
				z = pos.getZ() + 0.5;
			}

			// Создать самолёт
			PlaneEntity plane = new PlaneEntity(world, x, y, z, this.planeType);
			world.spawnEntity(plane);

			// Посадить игрока в самолёт
			plane.addPassenger(player);

			// Удалить предмет если не в творческом режиме
			if (!player.getAbilities().creativeMode) {
				player.getStackInHand(hand).decrement(1);
			}

			return ActionResult.SUCCESS;
		}
		return ActionResult.PASS;
	}

	public PlaneType getPlaneType() {
		return this.planeType;
	}
}
