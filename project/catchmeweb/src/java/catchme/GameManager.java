package catchme;

/**
 *
 * @author Marco Klein
 */
public class GameManager {
    
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
    
    
    public CatchMeGame getGame() {
        return game;
    }
}
