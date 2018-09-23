package pl.shockah.wowdiscordrpc.comm;

import javax.annotation.Nonnull;

import pl.shockah.wowdiscordrpc.bin.BitBuffer;

public class CharacterSerializer {
	@Nonnull
	public Character deserialize(@Nonnull BitBuffer bits, int readVersion) {
		Character character = new Character();

		if (bits.read())
			character.name = bits.readString(6);
		if (bits.read())
			character.realm = bits.readString(6);
		if (bits.read())
			character.faction = CharacterFaction.values()[bits.readUInt(2)];
		if (bits.read())
			character.specialization = CharacterSpecialization.values()[bits.readUInt(6)];
		if (bits.read())
			character.level = bits.readUInt(8);

		return character;
	}
}