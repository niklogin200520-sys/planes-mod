package com.example.planes.registry;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import com.example.planes.PlanesMod;
import com.example.planes.entity.PlaneEntity;
import com.example.planes.entity.PlaneType;

public class ModEntities {
	public static void register() {
		PlaneEntity.BIPLANE_TYPE = Registry.register(
			Registries.ENTITY_TYPE,
			PlanesMod.id("biplane"),
			FabricEntityTypeBuilder.create(SpawnGroup.MISC, PlaneEntity::new)
				.dimensions(EntityDimensions.fixed(1.5f, 1.0f))
				.trackRangeBlocks(256)
				.trackedUpdateRate(3)
				.build()
		);

		PlaneEntity.FIGHTER_WW1_TYPE = Registry.register(
			Registries.ENTITY_TYPE,
			PlanesMod.id("fighter_ww1"),
			FabricEntityTypeBuilder.create(SpawnGroup.MISC, PlaneEntity::new)
				.dimensions(EntityDimensions.fixed(2.0f, 1.2f))
				.trackRangeBlocks(256)
				.trackedUpdateRate(3)
				.build()
		);

		PlaneEntity.FIGHTER_WW2_TYPE = Registry.register(
			Registries.ENTITY_TYPE,
			PlanesMod.id("fighter_ww2"),
			FabricEntityTypeBuilder.create(SpawnGroup.MISC, PlaneEntity::new)
				.dimensions(EntityDimensions.fixed(2.2f, 1.5f))
				.trackRangeBlocks(256)
				.trackedUpdateRate(3)
				.build()
		);

		PlaneEntity.JET_FIGHTER_TYPE = Registry.register(
			Registries.ENTITY_TYPE,
			PlanesMod.id("jet_fighter"),
			FabricEntityTypeBuilder.create(SpawnGroup.MISC, PlaneEntity::new)
				.dimensions(EntityDimensions.fixed(2.5f, 1.8f))
				.trackRangeBlocks(256)
				.trackedUpdateRate(3)
				.build()
		);

		PlaneEntity.AIRLINER_TYPE = Registry.register(
			Registries.ENTITY_TYPE,
			PlanesMod.id("airliner"),
			FabricEntityTypeBuilder.create(SpawnGroup.MISC, PlaneEntity::new)
				.dimensions(EntityDimensions.fixed(3.0f, 2.0f))
				.trackRangeBlocks(256)
				.trackedUpdateRate(3)
				.build()
		);
	}
}
