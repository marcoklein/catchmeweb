package catchme.control;

import game.Control;
import game.Entity;
import game.World;

/**
 *
 * @author Marco Klein
 */
public class PlayerControl implements Control {
    
    private String name;

    public PlayerControl() {
    }

    public PlayerControl(String name) {
        this.name = name;
    }

    @Override
    public void initialize(Entity entity, World world) {
    }

    @Override
    public void cleanup(Entity entity, World world) {
    }

    @Override
    public void simulate(Entity entity, World world) {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
}
