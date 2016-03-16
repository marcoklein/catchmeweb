package game;

import java.io.Serializable;

/**
 * Used to define logic of an Entity.
 *
 * @author Marco Klein
 */
public interface Control extends Serializable {
    
    /**
     * Called after Control has been added to given Entity or if Entity is added
     * to World.
     * 
     * @param entity 
     * @param world 
     */
    public void initialize(Entity entity, World world);
    
    /**
     * Called after Control has been added to given Entity or if Entity is removed
     * from World.
     * 
     * @param entity 
     * @param world 
     */
    public void cleanup(Entity entity, World world);
    
    /**
     * Called to simulate the control.
     * 
     * @param entity
     * @param world 
     */
    public void simulate(Entity entity, World world);
    
}
