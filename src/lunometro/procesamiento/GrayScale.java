package procesamiento;

import java.awt.Color;
import java.awt.image.WritableRaster;

import javax.media.jai.PlanarImage;
import javax.media.jai.TiledImage;

/**
 * Comando que realiza la operación convertir a escala de grises una imagen
 *
 */
public class GrayScale extends AbstractImageCommand {

	public GrayScale(PlanarImage image) {
		super(image);
	}

	protected int colorRGBtoSRGB(Color colorValueRGB) {
		int colorSRGB;
		colorSRGB = (colorValueRGB.getRed() << 16)
				| (colorValueRGB.getGreen() << 8) | colorValueRGB.getBlue();
		return colorSRGB;
	}

	private int calculateAverage(Color color) {
		int averageColor;
		averageColor = (int) ((color.getRed() + color.getGreen() + color
				.getBlue()) / 3);
		return averageColor;
	}

	public PlanarImage execute() {
		int averageColor;
		Color auxColor;
		if (getImage() != null) {
			TiledImage tiledImage = ImageUtil.createTiledImage(getImage(),
					ImageUtil.tileWidth, ImageUtil.tileHeight);
			int tWidth = ImageUtil.tileWidth;
			int tHeight = ImageUtil.tileHeight;

			int maxX = 0;
			int maxY = 0;

			// We must process all tiles.
			for (int th = tiledImage.getMinTileY(); th <= tiledImage
					.getMaxTileY(); th++)
				for (int tw = tiledImage.getMinTileX(); tw <= tiledImage
						.getMaxTileX(); tw++) {
					// Get a raster for that tile.
					WritableRaster wr = tiledImage.getWritableTile(tw, th);

					for (int w = 0; w < tWidth; w++)
						for (int h = 0; h < tHeight; h++) {
							int x = tw * tWidth + w;
							int y = th * tHeight + h;
							if (x <= this.getImage().getMaxX()
									&& y <= this.getImage().getMaxY()) {
								maxX = Math.max(maxX, x);
								maxY = Math.max(maxY, y);

								try {
									int[] pixel = null;

									pixel = wr.getPixel(x, y, pixel);
									int r = pixel[0];
									int g = pixel[0];
									int b = pixel[0];

									if (pixel.length == 3) {
										g = pixel[1];
										b = pixel[2];
									}

									auxColor = new Color(r, g, b);
									averageColor = this
											.calculateAverage(auxColor);
									int colorSRGB = colorRGBtoSRGB(new Color(
											averageColor, averageColor,
											averageColor, auxColor.getAlpha()));
									int grayScaleColor[] = { colorSRGB,
											colorSRGB, colorSRGB };
									wr.setPixel(x, y, grayScaleColor);

								} catch (Exception e) {
									System.out.println("x: " + x + ", y: " + y);
									e.printStackTrace();
									return null;
								}
							}
						}
					tiledImage.releaseWritableTile(tw, th);
				}
			return tiledImage;
		}
		return null;
	}

	public String getCommandName() {
		return "Gray Scale";
	}

	public void postExecute() {

	}

}
