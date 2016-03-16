package game;

import java.io.Serializable;

/**
 *
 * @author Marco Klein
 */
public abstract class Game implements Serializable {
    
    protected World world;
    protected Simulator simulator;
    private boolean running;
    
    public void start() {
        running = true;
        world = new World();
        initWorld(world);
        
        simulator = new Simulator(world);
        initSimulator(simulator);
        
        
    }
    
    public void stop() {
        running = false;
    }
    
    public void simulate() {
        if (running) {
            simulator.simulate();
        }
    }
    
    protected abstract void initWorld(World world);
    
    protected abstract void initSimulator(Simulator simulator);

    public World getWorld() {
        return world;
    }

    public Simulator getSimulator() {
        return simulator;
    }

    public boolean isRunning() {
        return running;
    }

}
