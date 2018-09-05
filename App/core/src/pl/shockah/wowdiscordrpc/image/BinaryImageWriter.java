package pl.shockah.wowdiscordrpc.image;

import javax.annotation.Nonnull;

import javafx.scene.image.Image;
import pl.shockah.wowdiscordrpc.bin.BitBuffer;

public interface BinaryImageWriter {
	@Nonnull
	Image write(@Nonnull BitBuffer buffer);
}