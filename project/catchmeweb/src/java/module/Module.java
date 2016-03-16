package module;

import main.RequestHandler;
import com.google.gson.JsonObject;
import db.DatabaseServlet;
import main.JsonServlet;
import java.io.IOException;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import db.DatabaseRequest;
import java.util.HashMap;

/**
 * Defines 2 request handler one for a "get-html" and one for a null action (empty message).
 * Both will delegate their calls to the getHtml() method.
 *
 * @author Marco Klein
 */
public abstract class Module extends JsonServlet {
    private static final Logger LOG = Logger.getLogger(Module.class.getName());


    public Module() {
    }

    @Override
    public void init() throws ServletException {
        super.init();
        addRequestHandler("get-view", new RequestHandler() {
            @Override
            public void handleRequest(JsonObject data, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
                viewRequest(data, request, response);
            }

        });
        addRequestHandler(null, new RequestHandler() {
            @Override
            public void handleRequest(JsonObject data, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
                viewRequest(data, request, response);
            }
            
        });
        initModule();
    }

    @Override
    protected abstract void setupRequestHandlers(HashMap<String, RequestHandler> requestHandlers);

    protected abstract void initModule();



    /**
     * Called if the client requests the html of the module.
     * Must answer with content type "text/html".
     *
     * @param config
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected abstract void viewRequest(JsonObject config, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException ;

}
