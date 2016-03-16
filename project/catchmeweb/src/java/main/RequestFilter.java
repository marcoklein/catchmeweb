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
public interface RequestFilter {
    
    /**
     * Called before any Request Handler is called to filter out unwanted messages
     * or to simply log requests.
     * If false is returned the request will not be forwarded to any Request Handler.
     * 
     * @param action
     * @param data
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException 
     * @return 
     */
    public boolean filterRequest(String action, JsonObject data, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException;
}
