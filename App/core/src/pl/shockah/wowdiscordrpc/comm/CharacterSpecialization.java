package pl.shockah.wowdiscordrpc.comm;

import javax.annotation.Nonnull;

import lombok.Getter;

import static pl.shockah.wowdiscordrpc.comm.CharacterClass.*;
import static pl.shockah.wowdiscordrpc.comm.CharacterRole.*;

public enum CharacterSpecialization {
	Arms("Arms", Warrior, DPS),
	Fury("Fury", Warrior, DPS),
	ProtectionWarrior("Protection", Warrior, Tank),

	Blood("Blood", DeathKnight, Tank),
	FrostDeathKnight("Frost", DeathKnight, DPS),
	Unholy("Unholy", DeathKnight, DPS),

	HolyPaladin("Holy", Paladin, Healer),
	ProtectionPaladin("Protection", Paladin, Tank),
	Retribution("Retribution", Paladin, DPS),

	Brewmaster("Brewmaster", Monk, Tank),
	Mistweaver("Mistweaver", Monk, Healer),
	Windwalker("Windwalker", Monk, DPS),

	Discipline("Discipline", Priest, Healer),
	HolyPriest("Holy", Priest, Healer),
	Shadow("Shadow", Priest, DPS),

	Elemental("Elemental", Shaman, DPS),
	Enhancement("Enhancement", Shaman, DPS),
	RestorationShaman("Restoration", Shaman, Healer),

	Balance("Balance", Druid, DPS),
	Feral("Feral", Druid, DPS),
	Guardian("Guardian", Druid, Tank),
	RestorationDruid("Restoration", Druid, Healer),

	Assassination("Assassination", Rogue, DPS),
	Outlaw("Outlaw", Rogue, DPS),
	Subtlety("Subtlety", Rogue, DPS),

	Arcane("Arcane", Mage, DPS),
	Fire("Fire", Mage, DPS),
	FrostMage("Frost", Mage, DPS),

	Affliction("Affliction", Warlock, DPS),
	Demonology("Demonology", Warlock, DPS),
	Destruction("Destruction", Warlock, DPS),

	BeastMastery("Beast Mastery", Hunter, DPS),
	Marksmanship("Marksmanship", Hunter, DPS),
	Survival("Survival", Hunter, DPS),

	Havoc("Havoc", DemonHunter, DPS),
	Vengeance("Vengeance", DemonHunter, Tank);

	@Nonnull
	@Getter
	public final String name;

	@Nonnull
	@Getter
	public final CharacterClass clazz;

	@Nonnull
	@Getter
	public final CharacterRole role;

	CharacterSpecialization(@Nonnull String name, @Nonnull CharacterClass clazz, @Nonnull CharacterRole role) {
		this.name = name;
		this.clazz = clazz;
		this.role = role;
	}
}