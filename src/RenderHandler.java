
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

public class RenderHandler {

    private BufferedImage view;
    private Rectangle camera;
    private int[] pixels;

    public RenderHandler(int width, int height) {
        //Create a BufferedImage that will represent our view.
        view = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        camera = new Rectangle(0, 0, width, height);
        camera.x = 0;
        camera.y = 0;

        //Create an array for pixels
        pixels = ((DataBufferInt) view.getRaster().getDataBuffer()).getData();

    }

    // renders array of pixels to the screen
    public void render(Graphics graphics) {
        graphics.drawImage(view, 0, 0, view.getWidth(), view.getHeight(), null);
    }

    public void renderSprite(Sprite sprite, int xPosition, int yPosition, int xZoom, int yZoom) {
        renderArray(sprite.getPixels(), sprite.getWidth(), sprite.getHeight(), xPosition, yPosition, xZoom, yZoom);
    }
    //render image to our array of pixels: yPosition, xPosition of the imagePosition
    //Parameter: image, x-Posi, y-Posi, Breite, Höhe
    public void renderImage(BufferedImage image, int xPosition, int yPosition, int xZoom, int yZoom) {
        int[] imagePixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();

        renderArray(imagePixels, image.getWidth(), image.getHeight(), xPosition, yPosition, xZoom, yZoom);
    }

    public void renderRectangle(Rectangle rect, int xZoom, int yZoom) {
        int[] rectanglePixels = rect.getPixels();
        if (rectanglePixels != null) {
            renderArray(rectanglePixels, rect.w, rect.h, rect.x, rect.y, xZoom, yZoom);
        }
    }

    public void renderArray(int[] renderPixels, int renderWidth, int renderHeight,
            int xPosition, int yPosition, int xZoom, int yZoom) {
        // x,y are current pixel of the image
        for (int y = 0; y < renderHeight; y++) {
            for (int x = 0; x < renderWidth; x++) {
                for (int yZoomPosition = 0; yZoomPosition < yZoom; yZoomPosition++) {
                    for (int xZoomPosition = 0; xZoomPosition < xZoom; xZoomPosition++) {
                        setPixel((renderPixels[x + y * renderWidth]),
                                (x * xZoom) + xPosition + xZoomPosition - 3, (y * yZoom) + yPosition + yZoomPosition - 3);
                    }
                }
            }
        }
    }

    private void setPixel(int pixel, int x, int y) {
        // if pixel is in screen r,t,l,b
        if (x >= camera.x && y >= camera.y && x <= camera.x + camera.w
                && y <= camera.y + camera.h) {
            int pixelIndex = (x - camera.x) + (y - camera.y) * view.getWidth();
            if (pixels.length > pixelIndex && pixel != Game.alpha) {
                pixels[pixelIndex] = pixel;
            }
        }

    }

}
