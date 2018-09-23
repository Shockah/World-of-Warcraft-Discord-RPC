package pl.shockah.wowdiscordrpc.comm;

import javax.annotation.Nullable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Character {
	@Nullable
	public String name;

	@Nullable
	public String realm;

	@Nullable
	public CharacterFaction faction;

	@Nullable
	public CharacterSpecialization specialization;

	@Nullable
	public Integer level;
}