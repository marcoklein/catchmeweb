package game;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Used to hold world states to keep track of game changes.
 *
 * @author Marco Klein
 */
public class History implements Serializable {

    private ArrayList<World> worldStates = new ArrayList<>();
    private int simulations;

    public History() {
    }

    /**
     * Adds given world state to the underlying world state list.
     *
     * @param worldState
     */
    public void pushWorldState(World worldState) {
        World copy = null;
        try {
            copy = copy(worldState);
        } catch (IOException ex) {
            Logger.getLogger(History.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(History.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (copy == null) {
            worldStates.add(worldState);
            simulations++;
        }
    }

    public World getWorldState(int simulationNumber) {
        return worldStates.get(simulationNumber);
    }

    public ArrayList<World> getWorldStates() {
        return worldStates;
    }

    public int getSimulations() {
        return simulations;
    }

    /**
     * Copy an serializable object deeply.
     *
     * @param obj Object to copy.
     * @return Copied object.
     *
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public static <T extends Serializable> T copy(final T obj) throws IOException, ClassNotFoundException {
        ObjectOutputStream out = null;
        ObjectInputStream in = null;
        Object copy = null;

        try {
            // write the object
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            out = new ObjectOutputStream(baos);
            out.writeObject(obj);
            out.flush();

            // read in the copy
            byte data[] = baos.toByteArray();
            ByteArrayInputStream bais = new ByteArrayInputStream(data);
            in = new ObjectInputStream(bais);
            copy = in.readObject();
        } finally {
            out.close();
            in.close();
        }

        return (T) copy;
    }

}
