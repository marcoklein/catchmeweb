package catchme;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Marco Klein
 */
public class GameManager {
    private static final Logger LOG = Logger.getLogger(GameManager.class.getName());
    
    private static GameManager instance;
    
    private CatchMeGame game = new CatchMeGame();
    
    private GameManager() {
    }
    
    public static GameManager getInstance() {
        if (instance == null) {
            instance = new GameManager();
        }
        return instance;
    }
    
    /**
     * Opens a new game.
     * 
     * @param key
     * @return 
     */
    public CatchMeGame openGame(String key) {
        LOG.log(Level.INFO, "Opening game with key {0}", key);
        game = new CatchMeGame();
        return game;
    }
    
    public CatchMeGame closeGame(String key) {
        return null;
    }
    
    public CatchMeGame findGame(String key) {
        return game;
    }
}
