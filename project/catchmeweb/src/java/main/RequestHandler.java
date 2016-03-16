package main;

import com.google.gson.JsonObject;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Marco Klein
 */
public interface RequestHandler {
    
    /**
     * Handles a request. Map the request to the request handlers.
     * 
     * @param data
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException 
     */
    public void handleRequest(JsonObject data, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException;
    
    
}
