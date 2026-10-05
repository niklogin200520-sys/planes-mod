package com.example.planes.entity;

public enum PlaneType {
	BIPLANE("Биплан", 1.0f),
	FIGHTER_WW1("Истребитель WWI", 1.3f),
	FIGHTER_WW2("Истребитель WWII", 1.6f),
	JET_FIGHTER("Реактивный истребитель", 2.0f),
	AIRLINER("Современный самолёт", 1.8f);

	public final String displayName;
	public final float speedMultiplier;

	PlaneType(String displayName, float speedMultiplier) {
		this.displayName = displayName;
		this.speedMultiplier = speedMultiplier;
	}
}
