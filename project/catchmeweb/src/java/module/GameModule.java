package module;

import catchme.CatchMeGame;
import catchme.GameManager;
import catchme.control.EntityControl;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import game.Entity;
import java.io.IOException;
import java.util.HashMap;
import java.util.logging.Logger;
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
    private static final Logger LOG = Logger.getLogger(GameModule.class.getName());
    
    private CatchMeGame game;

    @Override
    protected void setupRequestHandlers(HashMap<String, RequestHandler> requestHandlers) {
        requestHandlers.put("login", new LoginRequest());
        requestHandlers.put("world-update", new WorldUpdateRequest());
        requestHandlers.put("submit-movement", new SubmitMovementRequest());
    }
    
    @Override
    protected void initModule() {
        // init game
        game = GameManager.getInstance().openGame("TEST123");
    }

    @Override
    protected void viewRequest(JsonObject config, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (config == null) {
            request.getRequestDispatcher("/jsp/game/main.jsp").forward(request, response);
            return;
        } else {
            LOG.warning("Client connected specifing unnecessary configs for CatchMe game.");
        }
    }

    private class LoginRequest implements RequestHandler {

        @Override
        public void handleRequest(JsonObject data, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            JsonHelper json = new JsonHelper(data);
            // extract data
            String gameId = json.get("gameId", "[unknown]");
            String playerName = json.get("name", "[unknown]");
            boolean visitor = json.get("visitor", false);
            
            // lookup game with given id
            CatchMeGame game = GameManager.getInstance().findGame(gameId);
            
            // get player entity
            Entity player = game.getPlayer(playerName);
            if (player == null) {
                player = game.addPlayer(playerName);
            }
            
            // respond with world update
            JsonObject respData = new JsonObject();
            
            // send reset world to init world
            JsonObject resetWorld = new JsonObject();
            resetWorld.addProperty("width", 10);
            resetWorld.addProperty("height", 10);
            respData.add("resetWorld", resetWorld);
            
            // send all active entities
            JsonArray addEntities = new JsonArray();
            for (Entity entity : game.getWorld().getEntities()) {
                EntityControl control = entity.getControl(EntityControl.class);
                if (control == null) {
                    JsonObject entityProps = new JsonObject();
                    entityProps.addProperty("x", control.getX());
                    entityProps.addProperty("y", control.getY());
                    entityProps.addProperty("view", control.getView());
                }
            }
            respData.add("addEntities", addEntities);
            
            
            
            
            response.getWriter().print(gson.toJson(respData));
        }
    }
    private class WorldUpdateRequest implements RequestHandler {

        @Override
        public void handleRequest(JsonObject data, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            
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
