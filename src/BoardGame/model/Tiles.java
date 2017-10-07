package BoardGame.model;


import BoardGame.RenderHandler;
import BoardGame.model.SpriteSheet;
import BoardGame.model.Sprite;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * The Tiles class deals with the platcement of the image.
 *
 */
public class Tiles {

    private SpriteSheet spriteSheet;
    private ArrayList<Tile> tilesList = new ArrayList<Tile>();

    // assuming sprites in spriteSheet have been loaded
    public Tiles(File tilesFile, SpriteSheet spriteSheet) {
        this.spriteSheet = spriteSheet;
        try {
            Scanner scanner = new Scanner(tilesFile);
            //reading tiles and add them to tileList array
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (!line.startsWith("//")) {
                    String[] splitString = line.split("-");
                    String tileName = splitString[0];
                    int spirteX = Integer.parseInt(splitString[1]);
                    int spirteY = Integer.parseInt(splitString[2]);
                    Tile tile = new Tile(tileName, spriteSheet.getSprite(spirteX, spirteY));
                    tilesList.add(tile);
                }
            }
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }
    }

    public void renderTile(int tileID, RenderHandler renderer,
            int xPosition, int yPosition, int xZoom, int yZoom) {
        if (tileID >= 0 && tilesList.size() > tileID) {
            renderer.renderSprite(tilesList.get(tileID).sprite,
                    xPosition, yPosition, xZoom, yZoom);
        }else {
            System.out.println("TileID " + tileID + " is not within range " + tilesList.size() + ".");
        }
    }

    //Tile structure
    class Tile {

        public String tileName;
        public Sprite sprite;

        public Tile(String tileName, Sprite sprite) {
            this.tileName = tileName;
            this.sprite = sprite;
        }

    }
}
