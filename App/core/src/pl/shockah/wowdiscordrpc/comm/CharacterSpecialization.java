package pl.shockah.wowdiscordrpc.comm;

import javax.annotation.Nonnull;

import lombok.Getter;

import static pl.shockah.wowdiscordrpc.comm.CharacterClass.*;
import static pl.shockah.wowdiscordrpc.comm.CharacterRole.*;

public enum CharacterSpecialization {
	Arms("Arms", Warrior, MeleeDPS),
	Fury("Fury", Warrior, MeleeDPS),
	ProtectionWarrior("Protection", Warrior, Tank),

	Blood("Blood", DeathKnight, Tank),
	FrostDeathKnight("Frost", DeathKnight, MeleeDPS),
	Unholy("Unholy", DeathKnight, RangedDPS),

	HolyPaladin("Holy", Paladin, Healer),
	ProtectionPaladin("Protection", Paladin, Tank),
	Retribution("Retribution", Paladin, MeleeDPS),

	Brewmaster("Brewmaster", Monk, Tank),
	Mistweaver("Mistweaver", Monk, Healer),
	Windwalker("Windwalker", Monk, MeleeDPS),

	Discipline("Discipline", Priest, Healer),
	HolyPriest("Holy", Priest, Healer),
	Shadow("Shadow", Priest, RangedDPS),

	Elemental("Elemental", Shaman, RangedDPS),
	Enhancement("Enhancement", Shaman, MeleeDPS),
	RestorationShaman("Restoration", Shaman, Healer),

	Balance("Balance", Druid, RangedDPS),
	Feral("Feral", Druid, MeleeDPS),
	Guardian("Guardian", Druid, Tank),
	RestorationDruid("Restoration", Druid, Healer),

	Assassination("Assassination", Rogue, MeleeDPS),
	Outlaw("Outlaw", Rogue, MeleeDPS),
	Subtlety("Subtlety", Rogue, MeleeDPS),

	Arcane("Arcane", Mage, RangedDPS),
	Fire("Fire", Mage, RangedDPS),
	FrostMage("Frost", Mage, RangedDPS),

	Affliction("Affliction", Warlock, RangedDPS),
	Demonology("Demonology", Warlock, RangedDPS),
	Destruction("Destruction", Warlock, RangedDPS),

	BeastMastery("Beast Mastery", Hunter, RangedDPS),
	Marksmanship("Marksmanship", Hunter, RangedDPS),
	Survival("Survival", Hunter, MeleeDPS),

	Havoc("Havoc", DemonHunter, MeleeDPS),
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