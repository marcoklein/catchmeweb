package main;

import com.google.gson.JsonObject;
import java.io.IOException;
import java.util.HashMap;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import module.JsonHelper;

/**
 * Looks up idea tours and sends tour data.
 *
 * @author Marco Klein
 */
public class IdeaTourManager extends JsonServlet {

    public IdeaTourManager() {
    }

    @Override
    protected void setupRequestHandlers(HashMap<String, RequestHandler> requestHandlers) {
        requestHandlers.put("random", new RandomTourHandler());
    }
    
    public class RandomTourHandler implements RequestHandler {

        @Override
        public void handleRequest(JsonObject data, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            JsonHelper json = new JsonHelper(data);
            
            // test requirements
            if (json.get("home", true)) {
                // only get tours with "/homeintro" as starting module
            }
        }
        
    }
}
