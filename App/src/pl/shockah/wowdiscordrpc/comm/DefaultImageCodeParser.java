package pl.shockah.wowdiscordrpc.comm;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import pl.shockah.unicorn.collection.MutableArray2D;
import pl.shockah.wowdiscordrpc.BitBuffer;

public class DefaultImageCodeParser extends ImageCodeParser {
	public final int minRed;

	public final int minGreen;

	public final int minBlue;

	public final int redBits;

	public final int greenBits;

	public final int blueBits;

	public final int maxRed;

	public final int maxGreen;

	public final int maxBlue;

	@Nullable
	private Rectangle cachedRectangle;

	public DefaultImageCodeParser(int minRed, int minGreen, int minBlue, int redBits, int greenBits, int blueBits) {
		this.minRed = minRed;
		this.minGreen = minGreen;
		this.minBlue = minBlue;
		this.redBits = redBits;
		this.greenBits = greenBits;
		this.blueBits = blueBits;
		maxRed = minRed + (1 << redBits) - 1;
		maxGreen = minGreen + (1 << greenBits) - 1;
		maxBlue = minBlue + (1 << blueBits) - 1;
	}

	@Nullable
	@Override
	public Image findCodeRectangle(@Nonnull Image fullScreenImage) {
		return null;
	}

	private boolean isPixelValid(@Nonnull PixelReader pixelReader, @Nonnull MutableArray2D<Boolean> validCache, int x, int y) {
		return validCache.computeIfAbsent(x, y, (nx, ny) -> {
			int argb = pixelReader.getArgb(nx, ny);
			int r = 0xFF & (argb >> 16);
			int g = 0xFF & (argb >> 8);
			int b = 0xFF & argb;

			if (r < minRed || r > maxRed)
				return false;
			if (g < minGreen || g > maxGreen)
				return false;
			if (b < minBlue || b > maxBlue)
				return false;
			return true;
		});
	}

	private int getRed(int argb) {
		return 0xFF & (argb >> 16);
	}

	private int getGreen(int argb) {
		return 0xFF & (argb >> 8);
	}

	private int getBlue(int argb) {
		return 0xFF & argb;
	}

	private int getArgbAndMarkValidity(@Nonnull PixelReader pixelReader, @Nonnull MutableArray2D<Boolean> validCache, int x, int y) {
		int argb = pixelReader.getArgb(x, y);
		validCache.computeIfAbsent(x, y, (nx, ny) -> {
			int r = getRed(argb);
			int g = getGreen(argb);
			int b = getBlue(argb);

			if (r < minRed || r > maxRed)
				return false;
			if (g < minGreen || g > maxGreen)
				return false;
			if (b < minBlue || b > maxBlue)
				return false;
			return true;
		});
		return argb;
	}

	@Nonnull
	private Color getColorAndMarkValidity(@Nonnull PixelReader pixelReader, @Nonnull MutableArray2D<Boolean> validCache, int x, int y) {
		Color color = pixelReader.getColor(x, y);
		isPixelValid(pixelReader, validCache, x, y);
		return color;
	}

	@Nullable
	private Rectangle getValidRectangle(@Nonnull Image image, @Nonnull PixelReader pixelReader, @Nonnull MutableArray2D<Boolean> validCache, int x0, int y0) {
		if (!isPixelValid(pixelReader, validCache, x0, y0))
			return null;

		int imageWidth = (int)image.getWidth();
		int imageHeight = (int)image.getHeight();

		int x1 = x0;
		int y1 = y0;
		int x2 = x0;
		int y2 = y0;

		for (int x = x1; x < imageWidth; x++) {
			if (!isPixelValid(pixelReader, validCache, x, y0))
				break;

			int argb = getArgbAndMarkValidity(pixelReader, validCache, x, y0);
			int r = getRed(argb);
			int g = getGreen(argb);
			int b = getBlue(argb);

			int mod = (x - x1) % 2;
			if (r != (mod == 0 ? maxRed : minRed))
				break;
			if (g != (mod == 0 ? maxGreen : minGreen))
				break;
			if (b != (mod == 0 ? maxBlue : minBlue))
				break;

			x2 = x;
		}

		if (x2 - x1 - 1 < 1)
			return null;

		for (int y = y1 + 1; y < imageHeight; y++) {
			if (!isPixelValid(pixelReader, validCache, x1, y))
				break;

			int argb = getArgbAndMarkValidity(pixelReader, validCache, x1, y);
			int r = getRed(argb);
			int g = getGreen(argb);
			int b = getBlue(argb);

			int mod = (y - y1) % 2;
			if (r != (mod == 0 ? maxRed : minRed))
				break;
			if (g != (mod == 0 ? maxGreen : minGreen))
				break;
			if (b != (mod == 0 ? maxBlue : minBlue))
				break;

			y2 = y;
		}

		if (y2 - y1 - 1 < 1)
			return null;

		// TODO: confirm right and bottom side are same lengths or even there

		return new Rectangle(x1, y1, x2 - x1 + 1, y2 - y1 + 1);
	}

	@Nonnull
	@Override
	public BitBuffer parseBinaryImageCode(@Nonnull Image codeImage) {
		BitBuffer bits = new BitBuffer();
		PixelReader pixelReader = codeImage.getPixelReader();
		int imageWidth = (int)codeImage.getWidth();
		int imageHeight = (int)codeImage.getHeight();

		Integer maxBitLength = null;

		int length = imageWidth * imageHeight;
		for (int i = 0; i < length; i++) {
			int x = i % imageWidth;
			int y = i / imageWidth;
			int argb = pixelReader.getArgb(x, y);

			if (redBits > 0) {
				int r = getRed(argb);
				bits.writeUInt(redBits, r - minRed);
			}
			if (greenBits > 0) {
				int g = getGreen(argb);
				bits.writeUInt(greenBits, g - minGreen);
			}
			if (blueBits > 0) {
				int b = getBlue(argb);
				bits.writeUInt(blueBits, b - minBlue);
			}

			if (maxBitLength == null) {
				if (bits.getSize() >= 24) { // allowing for 64 KB of data (8 bits in a byte, times 16)
					bits.seekTo(0);
					maxBitLength = bits.readUInt(24);
					bits.seekTo(bits.getSize());
				}
			} else {
				if (bits.getSize() >= maxBitLength)
					break;
			}
		}

		bits.seekTo(0);
		return bits;
	}
}
