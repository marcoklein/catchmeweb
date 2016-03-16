package game;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Entities are part of a world and are defined by Controls.
 * 
 * 
 * @see Control
 * @author Marco Klein
 */
public class Entity implements Serializable {
    
    /**
     * World Entity is part of.
     */
    private World world;
    private boolean remove;
    
    private ArrayList<Control> controls = new ArrayList<>();
    private HashMap<Class<? extends Control>, Control> controlMap = new HashMap<>();

    /**
     * Adds given control.
     * 
     * @param control 
     */
    public void addControl(Control control) {
        if (controlMap.get(control) != null) {
            throw new IllegalStateException("Can't add Control twice.");
        }
        controls.add(control);
        controlMap.put(control.getClass(), control);
        if (world != null) {
            control.initialize(this, world);
        }
    }
    
    /**
     * Removes given control.
     * 
     * @param control 
     */
    public void removeControl(Control control) {
        controls.remove(control);
        controlMap.remove(control.getClass());
        if (world != null) {
            control.cleanup(this, world);
        }
    }
    
    /**
     * Gets control with given class and returns null if it does not exist.
     * 
     * @param <T>
     * @param controlClass
     * @return 
     */
    public <T extends Control> T getControl(Class<T> controlClass) {
        return (T) controlMap.get(controlClass);
    }
    
    
    
    public ArrayList<Control> getControls() {
        return controls;
    }

    void setWorld(World world) {
        if (this.world != null) {
            
            // cleanup
            for (Control control : controls) {
                control.cleanup(this, world);
            }
        }
        this.world = world;
        if (world != null) {
            
            // initialize
            for (Control control : controls) {
                control.initialize(this, world);
            }
        }
    }
    
    /**
     * World Entity is part of.
     * @return 
     */
    public World getWorld() {
        return world;
    }

    public boolean isMarkedAsRemoved() {
        return remove;
    }

    /**
     * Sets remove to true and Entity will get removed from game.
     */
    public void remove() {
        this.remove = true;
    }
}
