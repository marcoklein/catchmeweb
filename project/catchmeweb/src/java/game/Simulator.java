package game;

import java.util.ArrayList;

/**
 * Simulates game logic.
 *
 * @author Marco Klein
 */
public class Simulator {
    
    private World world;
    private Renderer renderer;
    private History history = new History();
    
    private ArrayList<Entity> helperRemoveList = new ArrayList<>();

    public Simulator(World world) {
        this.world = world;
    }
    
    /**
     * Executes logic by going through all Entities in the world and calling their Controls.
     */
    public void simulate() {
        if (world == null) {
            throw new IllegalStateException("Simulator has no World.");
        }
        // keep current world state
        history.pushWorldState(world);
        helperRemoveList.clear();
        for (Entity entity : world.getEntities()) {
            if (entity.isMarkedAsRemoved()) {
                // should not happen but check to be sure
                // (may happen if world gets feeded with removed entities)
                helperRemoveList.add(entity);
                continue;
            }
            for (Control control : entity.getControls()) {
                // call each control
                control.simulate(entity, world);
            }
            if (entity.isMarkedAsRemoved()) {
                helperRemoveList.add(entity);
            }
        }
        // remove entites
        for (Entity entity : helperRemoveList) {
            world.removeEntity(entity);
        }
        render();
    }
    
    public void render() {
        if (renderer == null) {
            throw new IllegalStateException("render() called but no Renderer set.");
        }
        renderer.render(world);
    }
    
    /**
     * Renders using the given Renderer. This will not set the renderer of the Simulator.
     * 
     * @param renderer 
     */
    public void render(Renderer renderer) {
        renderer.render(world);
    }

    public World getWorld() {
        return world;
    }

    public Renderer getRenderer() {
        return renderer;
    }

    public void setRenderer(Renderer renderer) {
        this.renderer = renderer;
    }

    public History getHistory() {
        return history;
    }
    
}
