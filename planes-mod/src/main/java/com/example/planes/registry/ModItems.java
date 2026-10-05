package com.example.planes.registry;

import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import com.example.planes.PlanesMod;
import com.example.planes.entity.PlaneType;
import com.example.planes.item.PlaneSpawnerItem;

public class ModItems {
	public static final Item BIPLANE = new PlaneSpawnerItem(PlaneType.BIPLANE, new Item.Settings());
	public static final Item FIGHTER_WW1 = new PlaneSpawnerItem(PlaneType.FIGHTER_WW1, new Item.Settings());
	public static final Item FIGHTER_WW2 = new PlaneSpawnerItem(PlaneType.FIGHTER_WW2, new Item.Settings());
	public static final Item JET_FIGHTER = new PlaneSpawnerItem(PlaneType.JET_FIGHTER, new Item.Settings());
	public static final Item AIRLINER = new PlaneSpawnerItem(PlaneType.AIRLINER, new Item.Settings());

	public static void register() {
		Registry.register(Registries.ITEM, PlanesMod.id("biplane"), BIPLANE);
		Registry.register(Registries.ITEM, PlanesMod.id("fighter_ww1"), FIGHTER_WW1);
		Registry.register(Registries.ITEM, PlanesMod.id("fighter_ww2"), FIGHTER_WW2);
		Registry.register(Registries.ITEM, PlanesMod.id("jet_fighter"), JET_FIGHTER);
		Registry.register(Registries.ITEM, PlanesMod.id("airliner"), AIRLINER);
	}
}
