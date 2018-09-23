package pl.shockah.wowdiscordrpc.comm.activity.raid;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Nonnull;

import pl.shockah.wowdiscordrpc.bin.BitBuffer;
import pl.shockah.wowdiscordrpc.comm.CharacterRole;

public class RaidActivitySerializer {
	@Nonnull
	public RaidActivity deserialize(@Nonnull BitBuffer bits, int readVersion) {
		RaidActivity activity = new RaidActivity();

		if (bits.read())
			activity.difficulty = RaidDifficulty.values()[bits.readUInt(2)];
		if (bits.read())
			activity.composition = readComposition(bits);
		if (bits.read())
			activity.encounter = readEncounter(bits);

		return activity;
	}

	@Nonnull
	private RaidComposition readComposition(@Nonnull BitBuffer bits) {
		Map<CharacterRole, Integer> counts = new HashMap<>();
		for (CharacterRole role : CharacterRole.values()) {
			counts.put(role, bits.readUInt(6));
		}
		return new RaidComposition(counts);
	}

	@Nonnull
	private RaidEncounter readEncounter(@Nonnull BitBuffer bits) {
		String name = null;
		Float bossHealth = null;

		if (bits.read())
			name = bits.readString(7);
		if (bits.read())
			bossHealth = bits.readUInt(10) * 0.1f;

		return new RaidEncounter(name, bossHealth);
	}
}