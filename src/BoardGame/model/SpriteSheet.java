package BoardGame.model;

import java.awt.image.BufferedImage;

public class SpriteSheet {

    private int[] pixels;
    private BufferedImage image;
    public final int SIZE_X;
    public final int SIZE_Y;
    private int spriteSizeX;
    private Sprite[] loadedSprites = null;
    private boolean spritesLoaded = false;

    public SpriteSheet(BufferedImage sheetImage) {
        this.image = sheetImage;
        this.SIZE_X = sheetImage.getWidth();
        this.SIZE_Y = sheetImage.getHeight();

        pixels = new int[SIZE_X * SIZE_Y];
        pixels = sheetImage.getRGB(0, 0, SIZE_X, SIZE_Y, pixels, 0, SIZE_X);
    }

    public void loadSprites(int spriteSizeX, int spriteSizeY) {
        this.spriteSizeX = spriteSizeX;
        loadedSprites = new Sprite[(SIZE_X / spriteSizeX) * (SIZE_Y / spriteSizeY)];
        int spriteID = 0;
        for (int y = 0; y < SIZE_Y; y += spriteSizeX) {

            for (int x = 0; x < SIZE_X; x += spriteSizeY) {
                loadedSprites[spriteID] = new Sprite(this, x, y, spriteSizeX, spriteSizeY);
                spriteID++;
            }
        }
        spritesLoaded = true;
    }
    /**
     * 
     * @param x-Position in spriteSheet
     * @param y-Position in spriteSheet
     * @return Sprite with position (x,y)
     */
    public Sprite getSprite(int x, int y) {
        if (spritesLoaded) {       //sprite-width
            int spriteID = x + y * (SIZE_X / spriteSizeX);

            if (spriteID < loadedSprites.length) {
                return loadedSprites[spriteID];
            } else {
                System.out.println("SpriteID of " + spriteID + " ist out of range with a maximum of " + loadedSprites.length);
            }
        } else {
            System.out.println("Spritesheet could not get a sprites with no loades sprites");
        }
        return null;
    }

    public int[] getPixel() {
        return pixels;
    }

    public BufferedImage getImage() {
        return image;
    }

}
