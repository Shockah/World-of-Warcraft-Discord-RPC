package pl.shockah.wowdiscordrpc.comm;

import javax.annotation.Nonnull;

import lombok.Getter;

public enum CharacterRole {
	MeleeDPS("Melee DPS"),
	RangedDPS("Ranged DPS"),
	Healer("Healer"),
	Tank("Tank");

	@Nonnull
	@Getter
	public final String name;

	CharacterRole(@Nonnull String name) {
		this.name = name;
	}

	public boolean isDPS() {
		return this == MeleeDPS || this == RangedDPS;
	}
}