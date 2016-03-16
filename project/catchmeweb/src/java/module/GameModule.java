package module;

import catchme.CatchMeGame;
import catchme.GameManager;
import com.google.gson.JsonObject;
import java.io.IOException;
import java.util.HashMap;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import main.RequestHandler;

/**
 * User can submit movements for the next round.
 * Can handle different view requests (provide config for view).
 * 
 *
 * @author Marco Klein
 */
@WebServlet("/game")
public class GameModule extends Module {
    
    private CatchMeGame game;

    @Override
    protected void setupRequestHandlers(HashMap<String, RequestHandler> requestHandlers) {
        requestHandlers.put("submit-movement", new SubmitMovementRequest());
    }
    
    @Override
    protected void initModule() {
        // init game
        game = GameManager.getInstance().getGame();
    }

    @Override
    protected void viewRequest(JsonObject config, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (config == null) {
            request.getRequestDispatcher("/jsp/game/main.jsp").forward(request, response);
        } else {
            throw new IllegalStateException("Game handles no configs.");
        }
    }

    /**
     * User submits his Movement -> tell game.
     * Data:
     * {
     *      
     */
    private class SubmitMovementRequest implements RequestHandler {

        @Override
        public void handleRequest(JsonObject data, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            
        }

    }

    
}
