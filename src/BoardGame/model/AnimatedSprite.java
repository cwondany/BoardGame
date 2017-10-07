package BoardGame.model;

import BoardGame.Game;
import BoardGame.GameObject;
import BoardGame.RenderHandler;
import java.awt.image.BufferedImage;

public class AnimatedSprite extends Sprite implements GameObject {

    private Sprite[] sprites;
    private int currentSprite = 0;
    private int speed;
    private int counter;

    public AnimatedSprite(SpriteSheet sheet, Rectangle[] positions, int speed) {
     sprites = new Sprite [positions.length];
     this.speed = speed;
     
        for (int i = 0; i < positions.length; i++) {
            sprites[i] = new Sprite(sheet, positions[i].x, positions[i].y, positions[i].w, positions[i].h);
        }
    }
    
    /**
     *  
     * @param images
     * @param speed represnts how many frames pass until the sprite changes
     */
    public AnimatedSprite(BufferedImage[] images, int speed) {
        sprites = new Sprite[images.length];
        this.speed = speed;
        for (int i = 0; i < images.length; i++) {
            sprites[i] = new Sprite(images[i]);
        }
    }

    //Reander is dealt with Layer class.
    public void render(RenderHandler renderer, int xZoom, int yZoom) {
    }

    //Call at 60 fps rate.
    public void update(Game game) {
        counter++;
        if (counter >= speed) {
            counter = 0;
            incrementSprite();
        }
    }

    public int getWidth() {
        return sprites[currentSprite].getWidth();
    }

    public int getHeight() {
        return sprites[currentSprite].getHeight();
    }

    public int[] getPixels() {
        return sprites[currentSprite].getPixels();
    }

    public void incrementSprite() {
        currentSprite++;
        if (currentSprite >= sprites.length) {
            currentSprite = 0;
        }
    }

}
