package pl.shockah.wowdiscordrpc.comm;

import javax.annotation.Nonnull;

import pl.shockah.wowdiscordrpc.bin.BitBuffer;
import pl.shockah.wowdiscordrpc.comm.activity.ActivitySerializer;

public class MessageSerializer {
	public static final int version = 1;

	@Nonnull
	private final CharacterSerializer characterSerializer = new CharacterSerializer();

	@Nonnull
	private final ActivitySerializer activitySerializer = new ActivitySerializer();

	@Nonnull
	public Message deserialize(@Nonnull BitBuffer bits) {
		int readVersion = bits.readUInt(10);
		if (readVersion > version)
			throw new IllegalArgumentException("Unknown version number.");

		Message message = new Message();

		if (bits.read())
			message.character = characterSerializer.deserialize(bits, readVersion);
		if (bits.read())
			message.activity = activitySerializer.deserialize(bits, readVersion);

		return message;
	}
}
