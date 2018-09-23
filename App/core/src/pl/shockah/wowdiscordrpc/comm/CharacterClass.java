package pl.shockah.wowdiscordrpc.comm;

import javax.annotation.Nonnull;

import lombok.Getter;

public enum CharacterClass {
	Warrior("Warrior"),
	DeathKnight("Death Knight"),
	Paladin("Paladin"),
	Monk("Monk"),
	Priest("Priest"),
	Shaman("Shaman"),
	Druid("Druid"),
	Rogue("Rogue"),
	Mage("Mage"),
	Warlock("Warlock"),
	Hunter("Hunter"),
	DemonHunter("Demon Hunter");

	@Nonnull
	@Getter
	public final String name;

	CharacterClass(@Nonnull String name) {
		this.name = name;
	}
}