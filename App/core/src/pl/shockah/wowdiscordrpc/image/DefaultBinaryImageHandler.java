package pl.shockah.wowdiscordrpc.image;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Nonnull;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import lombok.EqualsAndHashCode;
import pl.shockah.unicorn.collection.BooleanArray2D;
import pl.shockah.unicorn.collection.MutableBooleanArray2D;
import pl.shockah.wowdiscordrpc.bin.BitBuffer;

public class DefaultBinaryImageHandler implements BinaryImageHandler {
	private static final int cornerSize = 7;

	@Nonnull
	private static final int[][] cornerXYs = new int[][] {
			new int[] { 0, 0 },
			new int[] { 1, 0 },
			new int[] { 0, 1 }
	};

	@Nonnull
	private static final BooleanArray2D corner = new MutableBooleanArray2D(cornerSize, cornerSize);

	public final int minRed;

	public final int minGreen;

	public final int minBlue;

	public final int redBits;

	public final int greenBits;

	public final int blueBits;

	public final int maxRed;

	public final int maxGreen;

	public final int maxBlue;

	public final int minArgb;

	public final int maxArgb;

	static {
		// generating a corner symbol which looks like the QR code one, but with added additional corner bits

		MutableBooleanArray2D corner = (MutableBooleanArray2D)DefaultBinaryImageHandler.corner;
		for (int i = 0; i < cornerSize; i++) {
			corner.set(i, 0, true);
			corner.set(i, cornerSize - 1, true);
		}
		for (int i = 1; i < cornerSize - 1; i++) {
			corner.set(0, i, true);
			corner.set(cornerSize - 1, i, true);
		}
		for (int y = 0; y < cornerSize - 4; y++) {
			for (int x = 0; x < cornerSize - 4; x++) {
				corner.set(x + 2, y + 2, true);
			}
		}
		corner.set(1, 1, true);
		corner.set(1, cornerSize - 2, true);
		corner.set(cornerSize - 2, 1, true);
		corner.set(cornerSize - 2, cornerSize - 2, true);
	}

	public DefaultBinaryImageHandler() {
		this(0, 0, 0, 8, 8, 8);
	}

	public DefaultBinaryImageHandler(int minRed, int minGreen, int minBlue, int redBits, int greenBits, int blueBits) {
		if (redBits > 8 || greenBits > 8 || blueBits > 8)
			throw new IllegalStateException("Can only handle up to 8 bits per color component.");

		this.minRed = minRed;
		this.minGreen = minGreen;
		this.minBlue = minBlue;
		this.redBits = redBits;
		this.greenBits = greenBits;
		this.blueBits = blueBits;
		maxRed = minRed + (1 << redBits) - 1;
		maxGreen = minGreen + (1 << greenBits) - 1;
		maxBlue = minBlue + (1 << blueBits) - 1;
		minArgb = getArgb(minRed, minGreen, minBlue);

		int maxArgbR = minRed;
		int maxArgbG = minGreen;
		int maxArgbB = minBlue;

		if (redBits > 0)
			maxArgbR += (1 << redBits) - 1;
		if (greenBits > 0)
			maxArgbG += (1 << greenBits) - 1;
		if (blueBits > 0)
			maxArgbB += (1 << blueBits) - 1;

		maxArgb = getArgb(maxArgbR, maxArgbG, maxArgbB);
	}

	@Nonnull
	@Override
	public Image extract(@Nonnull Image fullImage) {
		int imageWidth = (int)fullImage.getWidth();
		int imageHeight = (int)fullImage.getHeight();

		if (imageWidth < cornerSize * 2 || imageHeight < cornerSize * 2)
			throw new IllegalArgumentException("Image too small to fit binary data.");

		List<Point> corners = new ArrayList<>();
		MutableBooleanArray2D cornerCache = new MutableBooleanArray2D(imageWidth, imageHeight);
		PixelReader pixelReader = fullImage.getPixelReader();

		for (int y = 0; y < imageHeight - cornerSize + 1; y++) {
			for (int x = 0; x < imageWidth - cornerSize + 1; x++) {
				if (cornerCache.get(x, y))
					continue;

				if (isCorner(pixelReader, x, y)) {
					corners.add(new Point(x, y));
					if (corners.size() >= 3) {
						for (Point topLeftCorner : corners) {
							Map<Point, Integer> topRightCorners = new HashMap<>();
							Map<Point, Integer> bottomLeftCorners = new HashMap<>();

							for (Point potentialCorner : corners) {
								if (potentialCorner.y == topLeftCorner.y && potentialCorner.x > topLeftCorner.x)
									topRightCorners.put(potentialCorner, potentialCorner.x - topLeftCorner.x);
								else if (potentialCorner.x == topLeftCorner.x && potentialCorner.y > topLeftCorner.y)
									bottomLeftCorners.put(potentialCorner, potentialCorner.y - topLeftCorner.y);
							}

							if (topRightCorners.isEmpty() || bottomLeftCorners.isEmpty())
								continue;

							for (Point topRightCorner : topRightCorners.keySet()) {
								int topRightCornerDistance = topRightCorners.get(topRightCorner);
								for (Point bottomLeftCorner : bottomLeftCorners.keySet()) {
									int bottomLeftCornerDistance = bottomLeftCorners.get(bottomLeftCorner);
									if (topRightCornerDistance == bottomLeftCornerDistance)
										return new WritableImage(pixelReader, topLeftCorner.x, topLeftCorner.y, topRightCornerDistance + cornerSize, bottomLeftCornerDistance + cornerSize);
								}
							}
						}
					}

					for (int yy = 0; yy < cornerSize; yy++) {
						for (int xx = 0; xx < cornerSize; xx++) {
							cornerCache.set(x + xx, y + yy, true);
						}
					}
				}
			}
		}

		throw new IllegalArgumentException("No binary data stored in the image.");
	}

	private boolean isCorner(@Nonnull PixelReader pixelReader, int x, int y) {
		for (int yy = 0; yy < cornerSize; yy++) {
			for (int xx = 0; xx < cornerSize; xx++) {
				int argb = pixelReader.getArgb(x + xx, y + yy);
				if (argb != (corner.get(xx, yy) ? maxArgb : minArgb))
					return false;
			}
		}
		return true;
	}

	@Nonnull
	@Override
	public BitBuffer read(@Nonnull Image image) {
		int imageWidth = (int)image.getWidth();
		int imageHeight = (int)image.getHeight();
		PixelReader pixelReader = image.getPixelReader();
		BitBuffer bits = new BitBuffer();

		Integer maxBitLength = null;

		Outer:
		for (int y = 0; y < imageHeight; y++) {
			int xMin = 0;
			int xMax = imageWidth;

			if (y < cornerSize || y >= imageHeight - cornerSize)
				xMin += cornerSize;
			if (y < cornerSize)
				xMax -= cornerSize;

			for (int x = xMin; x < xMax; x++) {
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
					if (bits.getSize() >= maxBitLength + 24) {
						BitBuffer truncated = new BitBuffer(maxBitLength);
						bits.seekTo(24);
						for (int j = 0; j < maxBitLength; j++) {
							truncated.write(bits.read());
						}
						bits = truncated;
						break Outer;
					}
				}
			}
		}

		bits.seekTo(0);
		return bits;
	}

	@Nonnull
	@Override
	public Image write(@Nonnull BitBuffer buffer) {
		BitBuffer realBuffer = new BitBuffer(buffer.getSize() + 24);
		realBuffer.writeUInt(24, buffer.getSize());
		while (buffer.getAvailable() != 0)
			realBuffer.write(buffer.read());
		realBuffer.seekTo(0);
		buffer = realBuffer;

		int imageSize = cornerSize * 2;
		while (getBitCapacity(imageSize) < buffer.getSize())
			imageSize++;

		WritableImage output = new WritableImage(imageSize, imageSize);
		PixelWriter pixelWriter = output.getPixelWriter();

		int cornerV = imageSize - cornerSize;
		for (int[] cornerXY : cornerXYs) {
			for (int y = 0; y < cornerSize; y++) {
				for (int x = 0; x < cornerSize; x++) {
					pixelWriter.setArgb(x + cornerXY[0] * cornerV, y + cornerXY[1] * cornerV, corner.get(x, y) ? maxArgb : minArgb);
				}
			}
		}

		for (int y = 0; y < imageSize; y++) {
			int xMin = 0;
			int xMax = imageSize;

			if (y < cornerSize || y >= imageSize - cornerSize)
				xMin += cornerSize;
			if (y < cornerSize)
				xMax -= cornerSize;

			for (int x = xMin; x < xMax; x++) {
				int r = minRed + readOrPad(realBuffer, redBits);
				int g = minGreen + readOrPad(realBuffer, greenBits);
				int b = minBlue + readOrPad(realBuffer, blueBits);
				pixelWriter.setArgb(x, y, getArgb(r, g, b));
			}
		}

		return output;
	}

	public int getBitCapacity(int imageSize) {
		return (cornerSize * (imageSize - cornerSize * 2) + cornerSize * (imageSize - cornerSize) + (imageSize - cornerSize * 2) * imageSize) * (redBits + greenBits + blueBits);
	}

	private int readOrPad(@Nonnull BitBuffer buffer, int bits) {
		return buffer.readUInt(Math.min(bits, buffer.getAvailable()));
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
		int argb = 255;
		argb = (argb << 8) + red;
		argb = (argb << 8) + green;
		argb = (argb << 8) + blue;
		return argb;
	}

	@EqualsAndHashCode
	private static class Point {
		final int x;

		final int y;

		private Point(int x, int y) {
			this.x = x;
			this.y = y;
		}
	}
}