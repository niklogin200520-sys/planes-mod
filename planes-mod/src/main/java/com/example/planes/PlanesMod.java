package com.example.planes;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import com.example.planes.entity.PlaneEntity;
import com.example.planes.item.PlaneSpawnerItem;
import com.example.planes.registry.ModEntities;
import com.example.planes.registry.ModItems;

public class PlanesMod implements ModInitializer {
	public static final String MOD_ID = "planes";

	@Override
	public void onInitialize() {
		ModEntities.register();
		ModItems.register();
	}

	public static Identifier id(String path) {
		return new Identifier(MOD_ID, path);
	}
}
