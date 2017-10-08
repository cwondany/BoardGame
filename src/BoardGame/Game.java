package BoardGame;

import BoardGame.model.AnimatedSprite;
import BoardGame.model.KeyBoardListener;
import BoardGame.model.Map;
import BoardGame.model.MouseEventListener;
import BoardGame.model.Player;
import BoardGame.model.Tiles;
import BoardGame.model.Rectangle;
import BoardGame.model.SpriteSheet;
import BoardGame.model.Sprite;
import java.awt.Canvas;
import java.awt.image.BufferStrategy;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.swing.JFrame;
import javax.imageio.ImageIO;
import java.io.IOException;

public class Game extends JFrame implements Runnable {

    //colorID
    public static int alpha = 0xFFFF00DC;

    private Canvas canvas = new Canvas();
    private RenderHandler renderer;
    private BufferedImage testImage;

    private Sprite testSprite;
    private SpriteSheet sheet;
    private SpriteSheet playerSheet;
    private Rectangle textRectangle = new Rectangle(30, 30, 100, 100);

    private Tiles tiles;
    private Map map;

    private GameObject[] objects;
    private KeyBoardListener keyListener = new KeyBoardListener(this);
    private MouseEventListener mouseListener = new MouseEventListener(this);
    private Player player;

    private int xZoom = 3;
    private int yZoom = 3;

    public Game() {

        //Make our program shutdown when we exit out.
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Set the position and size of our frame.
        setBounds(0, 0, 1000, 800);

        //Put our frame in the center of the screen.
        setLocationRelativeTo(null);

        //Add our graphics compoent
        add(canvas);

        //Make our frame visible.
        setVisible(true);

        //Create our object for buffer strategy.
        canvas.createBufferStrategy(3);

        renderer = new RenderHandler(getWidth(), getHeight());

        // load Assets
        BufferedImage sheetImage = loadImage("../assets/Tiles1.png");
//        URL url = this.getClass().getResource("../assets/Tiles1.png");
//        BufferedImage sheetImage = ImageIO.read(url);
        sheet = new SpriteSheet(sheetImage);
        sheet.loadSprites(16, 16);

        BufferedImage playerSheetImage = loadImage("../assets/Player.png");
        playerSheet = new SpriteSheet(playerSheetImage);
        playerSheet.loadSprites(20, 26); //Sprite(width, height)

        //Player AnimetesSprite
        AnimatedSprite playerAnimation = new AnimatedSprite(playerSheet, 5);

        //load Objects
        objects = new GameObject[1];
        player = new Player(playerAnimation);
        objects[0] = player;

        //Testing AnimetesSprite
        playerAnimation = new AnimatedSprite(playerSheet, 5);

        //load Tiles
        tiles = new Tiles(new File("src/assets/tiles.txt"), sheet);

        //load Map
        map = new Map(new File("src/assets/Map.txt"), tiles);

        // Add Listeners
        canvas.addKeyListener(keyListener);
        canvas.addFocusListener(keyListener);
        canvas.addMouseListener(mouseListener);
        canvas.addMouseMotionListener(mouseListener);
        //testImage = loadImage("GrassTile.png");
        //testSprite = sheet.getSprite(4, 1);
        //textRectangle.generateGraphics(3, 1234);
    }

    public void update() {
        for (int i = 0; i < objects.length; i++) {
            objects[i].update(this);
        }
    }

    public void render() {
        BufferStrategy bufferStrategy = canvas.getBufferStrategy();
        Graphics graphics = bufferStrategy.getDrawGraphics();
        super.paint(graphics);

        map.render(renderer, 3, 3);
        for (int i = 0; i < objects.length; i++) {
            objects[i].render(renderer, 3, 3);
        }
        renderer.render(graphics);

        //tiles.renderTile(2, renderer, 0, 0, 3, 3);
        //renderer.renderSprite(testSprite, 0, 0, 5, 5);
        //renderer.renderRectangle(textRectangle, 1, 1);
        graphics.dispose();
        bufferStrategy.show();
        renderer.clear();
    }

    public void handleCTRL(boolean[] keys) {
        if (keys[KeyEvent.VK_S]) {
            map.saveMap();
        }
    }

    public void leftClick(int x, int y) {
        x = (int) Math.floor((x + renderer.getCamera().x) / (16.0 * xZoom));
        y = (int) Math.floor((y + renderer.getCamera().y) / (16.0 * yZoom));
        map.setTile(x, y, 2);
    }

    public void rightClick(int x, int y) {
        x = (int) Math.floor((x + renderer.getCamera().x) / (16.0 * xZoom));
        y = (int) Math.floor((y + renderer.getCamera().y) / (16.0 * yZoom));
        map.removeTile(x, y);
    }

    private BufferedImage loadImage(String path) {
        try {
            BufferedImage loadedImage = ImageIO.read(Game.class.getResource(path));
            BufferedImage formattedImage = new BufferedImage(loadedImage.getWidth(), loadedImage.getHeight(), BufferedImage.TYPE_INT_RGB);
            formattedImage.getGraphics().drawImage(loadedImage, 0, 0, null);

            return formattedImage;
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public void run() {
        BufferStrategy bufferStrategy = canvas.getBufferStrategy();
        int i = 0;
        int x = 0;

        long lastTime = System.nanoTime(); //long 2^63
        double nanoSecondConversion = 1000000000.0 / 60; //60 frames per second
        double changeInSeconds = 0;

        while (true) {
            long now = System.nanoTime();

            changeInSeconds += (now - lastTime) / nanoSecondConversion;
            while (changeInSeconds >= 1) {
                update();
                changeInSeconds = 0;
            }
            render();
            lastTime = now;
        }

    }

    public static void main(String[] args) {
        Game game = new Game();
        Thread gameThread = new Thread(game);
        gameThread.start();
    }

    public KeyBoardListener getKeyListener() {
        return keyListener;
    }

    public MouseEventListener getMouseListener() {
        return mouseListener;
    }

    public RenderHandler getRenderer() {
        return renderer;
    }

}
