package catchme.control;

import game.Control;
import game.Entity;
import game.World;

/**
 *
 * @author Marco Klein
 */
public class FieldControl implements Control {
    
    private int x;
    private int y;
    /**
     * html code to describe the view
     */
    private String view;

    @Override
    public void initialize(Entity entity, World world) {
    }

    @Override
    public void cleanup(Entity entity, World world) {
    }

    @Override
    public void simulate(Entity entity, World world) {
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public String getView() {
        return view;
    }

    public void setView(String view) {
        this.view = view;
    }
    
}
