package pl.shockah.wowdiscordrpc.image;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.shape.Rectangle;
import pl.shockah.wowdiscordrpc.bin.BitBuffer;

public class DefaultCachingBinaryImageHandler implements BinaryImageHandler {
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

	public DefaultCachingBinaryImageHandler(int minRed, int minGreen, int minBlue, int redBits, int greenBits, int blueBits) {
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

	private int getRed(int argb) {
		return 0xFF & (argb >> 16);
	}

	private int getGreen(int argb) {
		return 0xFF & (argb >> 8);
	}

	private int getBlue(int argb) {
		return 0xFF & argb;
	}

	private int getArgb(int red, int green, int blue) {
		return ((red & 0x0ff) << 16) | ((green & 0x0ff) << 8) | (blue & 0x0ff);
	}

	@Nonnull
	@Override
	public Image extract(@Nonnull Image fullImage) {
		// TODO: extract a code image out of a full screen image
		return null;
	}

	@Nonnull
	@Override
	public BitBuffer read(@Nonnull Image image) {
		BitBuffer bits = new BitBuffer();
		PixelReader pixelReader = image.getPixelReader();
		int imageWidth = (int)image.getWidth();
		int imageHeight = (int)image.getHeight();

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
			}
			
			if (maxBitLength != null) {
				if (bits.getSize() >= maxBitLength) {
					if (bits.getSize() > maxBitLength) {
						BitBuffer truncated = new BitBuffer(maxBitLength);
						bits.seekTo(0);
						for (int j = 0; j < maxBitLength; j++) {
							truncated.write(bits.read());
						}
						bits = truncated;
					}
					break;
				}
			}
		}

		bits.seekTo(0);
		return bits;
	}

	@Nonnull
	@Override
	public Image write(@Nonnull BitBuffer buffer) {
		int bitsPerPixel = redBits + greenBits + blueBits;
		int totalBits = buffer.getSize() + 24;
		int totalPixels = (int)Math.ceil(1.0 * totalBits / bitsPerPixel);

		int height = (int)Math.sqrt(totalPixels);
		int width = (int)Math.ceil(1.0 * totalPixels / height);
		int totalWidth = width + 2;
		int totalHeight = height + 2;

		WritableImage output = new WritableImage(totalWidth, totalHeight);
		PixelWriter pixelWriter = output.getPixelWriter();

		for (int x = 0; x < totalWidth; x++) {
			drawBorderPixel(pixelWriter, x, 0);
			drawBorderPixel(pixelWriter, x, totalHeight - 1);
		}
		for (int y = 1; y < totalHeight - 1; y++) {
			drawBorderPixel(pixelWriter, 0, y);
			drawBorderPixel(pixelWriter, totalWidth - 1, y);
		}

		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				int r = minRed + readOrPad(buffer, redBits);
				int g = minGreen + readOrPad(buffer, greenBits);
				int b = minBlue + readOrPad(buffer, blueBits);
				pixelWriter.setArgb(x + 1, y + 1, getArgb(r, g, b));
			}
		}

		return output;
	}

	private int readOrPad(@Nonnull BitBuffer buffer, int bits) {
		return buffer.readUInt(Math.min(bits, buffer.getAvailable()));
	}

	private void drawBorderPixel(@Nonnull PixelWriter pixelWriter, int x, int y) {
		int argb = (x + y) % 2 == 0
				? getArgb(minRed, minGreen, minBlue)
				: getArgb(minRed + (1 << (redBits - 1)), minGreen + (1 << (greenBits - 1)), minBlue + (1 << (blueBits - 1)));
		pixelWriter.setArgb(x, y, argb);
	}
}