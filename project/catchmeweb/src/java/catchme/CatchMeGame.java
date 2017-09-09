package catchme;

import catchme.control.PlayerControl;
import catchme.render.HtmlRenderer;
import game.Entity;
import game.Game;
import game.Renderer;
import game.Simulator;
import game.World;
import java.util.HashMap;
import java.util.logging.Logger;

/**
 *
 * @author Marco Klein
 */
public class CatchMeGame extends Game implements Renderer {
    private static final Logger LOG = Logger.getLogger(CatchMeGame.class.getName());
    
    protected int width;
    protected int height;
    
    protected HtmlRenderer renderer;
    protected HtmlBuilder renderedHtml;
    
    protected HashMap<String, Entity> players = new HashMap<>();

    @Override
    protected void initWorld(World world) {
    }

    @Override
    protected void initSimulator(Simulator simulator) {
        simulator.setRenderer(this);
    }

    @Override
    public void render(World world) {
        // renderer renders into HtmlBuilder
        renderer.render(world);
        renderedHtml = renderer.getHtml();
        LOG.info("Game rendererd.");
    }

    public String getRenderedHtml() {
        if (renderedHtml == null) {
            return "";
        }
        return renderedHtml.toString();
    }
    
    public Entity getPlayer(String name) {
        return players.get(name);
    }
    
    public Entity addPlayer(String name) {
        Entity player = new Entity();
        player.addControl(new PlayerControl(name));
        players.put(name, player);
        return player;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public HtmlRenderer getRenderer() {
        return renderer;
    }
    
}
