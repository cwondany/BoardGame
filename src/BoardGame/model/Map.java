package BoardGame.model;

import BoardGame.RenderHandler;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Map class reads the file map.txt which helds the whole 2D-tile layout.
 */
public class Map {

    private Tiles tileSet;
    private int fileTileID = -1;
    private ArrayList<MappedTile> mappedTiles = new ArrayList<MappedTile>();

    public Map(File mapFile, Tiles tileSet) {
        this.tileSet = tileSet;

        Scanner scanner;
        try {
            scanner = new Scanner(mapFile);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (!line.startsWith("//")) {
                    if (line.contains(":")) {
                        String[] splitString = line.split(":");
                        if (splitString[0].equalsIgnoreCase("Fill")) {
                            fileTileID = Integer.parseInt(splitString[1]);
                            continue;
                        }
                    }
                    String[] splitString = line.split(",");
                    if (splitString.length >= 3) {
                        MappedTile mappedTile = new MappedTile(Integer.parseInt(splitString[0]),
                                Integer.parseInt(splitString[1]),
                                Integer.parseInt(splitString[2]));
                        mappedTiles.add(mappedTile);
                    }
                }
            }
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }
    }

    public void render(RenderHandler renderer, int xZoom, int yZoom) {
        int tileWidth = 16 * xZoom;
        int tileHeight = 16 * yZoom;

        if (fileTileID >= 0) { // fileTileID exists
            Rectangle camera = renderer.getCamera();

            for (int y = camera.y-tileHeight-(camera.y%tileHeight); y < camera.y + camera.h; y += tileHeight) {
                for (int x = camera.x-tileWidth-(camera.x%tileWidth); x < camera.x + camera.w; x += tileWidth) {
                    tileSet.renderTile(fileTileID, renderer, x, y, xZoom, yZoom);
                }
            }

        }
        for (int tileIndex = 0; tileIndex < mappedTiles.size(); tileIndex++) {
            MappedTile mappedTile = mappedTiles.get(tileIndex);
            tileSet.renderTile(mappedTile.id, renderer, mappedTile.x * tileWidth,
                    mappedTile.y * tileHeight, xZoom, yZoom);
        }
    }

    //Tile ID in the tileSet and the position of the tile in the map
    class MappedTile {

        public int id, x, y;

        public MappedTile(int id, int x, int y) {
            this.id = id;
            this.x = x;
            this.y = y;
        }
    }

}
