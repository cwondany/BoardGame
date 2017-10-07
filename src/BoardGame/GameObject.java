package BoardGame;

/**
 * Interface for game objects: render, update method
 */
public interface GameObject {
 
    //call every time it is possible
    public void render(RenderHandler renderer, int xZoom, int yZoom);
    // call at 60fps rate
    public void update(Game game);
}
