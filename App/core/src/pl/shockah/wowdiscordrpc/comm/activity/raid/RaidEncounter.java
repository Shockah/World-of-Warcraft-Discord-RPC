package pl.shockah.wowdiscordrpc.comm.activity.raid;

import javax.annotation.Nullable;

public class RaidEncounter {
	@Nullable
	public final String encounterName;

	@Nullable
	public final Float bossHealth;

	public RaidEncounter(@Nullable String encounterName, @Nullable Float bossHealth) {
		this.encounterName = encounterName;
		this.bossHealth = bossHealth;
	}
}