package game;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Holds all data of the game.
 *
 * @author Marco Klein
 */
public class World implements Serializable {
    
    private ArrayList<Entity> entities = new ArrayList<Entity>();
    
    /**
     * Adds given entity to world.
     * 
     * @param entity 
     */
    public void addEntity(Entity entity) {
        entities.add(entity);
        entity.setWorld(this);
    }
    
    /**
     * Removes given entity from world.
     * 
     * @param entity 
     */
    public void removeEntity(Entity entity) {
        entity.setWorld(null);
        entities.remove(entity);
    }
    
    public ArrayList<Entity> getEntities() {
        return entities;
    }
    
}
