package pl.shockah.wowdiscordrpc.comm;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import javafx.scene.image.Image;
import pl.shockah.wowdiscordrpc.BitBuffer;

public abstract class ImageCodeParser {
	@Nullable
	public abstract Image findCodeRectangle(@Nonnull Image fullScreenImage);

	@Nonnull
	public abstract BitBuffer parseBinaryImageCode(@Nonnull Image codeImage);
}