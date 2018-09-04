package pl.shockah.wowdiscordrpc.image;

import javax.annotation.Nonnull;

import javafx.scene.image.Image;
import pl.shockah.wowdiscordrpc.bin.BitBuffer;

public interface BinaryImageReader {
	@Nonnull
	BitBuffer read(@Nonnull Image image);
}