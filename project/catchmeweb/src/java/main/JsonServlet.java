package main;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import db.DatabaseServlet;
import db.request.AddLog;
import db.request.AddSession;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Marco Klein
 */
public abstract class JsonServlet extends HttpServlet {

    private static final Logger LOG = Logger.getLogger(JsonServlet.class.getName());

    /**
     * For parsing into json.
     */
    protected Gson gson = new Gson();

    private HashMap<String, RequestHandler> requestHandlers;
    /**
     * Request Filters are called before the actual request is forwarded to a
     * Request Handler. If only one Filter returns false the message will not
     * proceed to the Request Handler.
     */
    private ArrayList<RequestFilter> requestFilters;

    @Override
    public void init() throws ServletException {
        super.init();
        requestHandlers = new HashMap<>();
        requestFilters = new ArrayList<>();
        setupRequestHandlers(requestHandlers);

        // add request filter
        requestFilters.add(new LogRequestFilter());
    }

    protected abstract void setupRequestHandlers(HashMap<String, RequestHandler> requestHandlers);

    /**
     * Calls all request filters and checks whether the given request should be
     * filtered out or forwarded to a Request Handler.
     *
     * @param data
     * @param request
     * @param response
     * @return
     */
    private boolean testRequestFilters(String action, JsonObject data, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        boolean filterRequest = true;
        for (RequestFilter filter : requestFilters) {
            if (!filter.filterRequest(action, data, request, response)) {
                filterRequest = false;
            }
        }
        return filterRequest;
    }

    /**
     * Handles post requests and filters out the json data which is sent in the
     * data parameter.
     *
     * @param data
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    private void handleRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        String action = request.getParameter("action");
        // extract json data
        String json = request.getParameter("data");
        LOG.log(Level.INFO, "Recieved action: {0}, json: {1}", new String[]{action, json});
        JsonObject data = gson.fromJson(json, JsonObject.class);;

//        if (action != null) {
        RequestHandler handler = requestHandlers.get(action);
        if (handler != null) {
            if (testRequestFilters(action, data, request, response)) {
                handler.handleRequest(data, request, response);
            }
        } else {
            LOG.log(Level.WARNING, "No handler defined for action {0}", action);
        }
//        } else {
//            LOG.info("Message has no action.");
//            if ()
//        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        handleRequest(req, resp);
    }

    @Override
    protected final void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        handleRequest(req, resp);
    }

    public void addRequestHandler(String action, RequestHandler handler) {
        requestHandlers.put(action, handler);
    }

    public void addRequestFilter(RequestFilter filter) {
        requestFilters.add(filter);
    }

    /**
     * Logs all requests to an associated session.
     */
    private class LogRequestFilter implements RequestFilter {

        @Override
        public boolean filterRequest(String action, JsonObject data, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            // check if a session id already exists
            Integer sessionId = null;
            try {
                sessionId = (Integer) request.getSession().getAttribute("sessionId");
            } catch (IllegalStateException e) {
                LOG.info("Illegal session state - can't log message.");
            }
            if (sessionId == null) {
                LOG.info("Creating new log session...");
                // create new session
                sessionId = DatabaseServlet.makeDatabaseRequest(new AddSession(
                        request.getLocale().getCountry()
                ), request, response);
                try {
                    request.getSession().setAttribute("sessionId", sessionId);
                } catch (IllegalStateException e) {
                    LOG.info("Illegal session state - can't set session ID.");
                }
            }
            
            if (sessionId != null) {

                try {
                    // store a log holding information about the request
                    DatabaseServlet.makeDatabaseRequest(new AddLog(
                            sessionId,
                            JsonServlet.class.getName(),
                            "Recieved action " + action + " for " + request.getServletPath() + " with data " + gson.toJson(data).toString(),
                            new Timestamp(System.currentTimeMillis()),
                            Level.INFO.getName()
                    ), request, response);
                    LOG.info("Stored log in Database.");

                } catch (Exception e) {
                    // logging may go wrong
                }
            } else {
                LOG.info("Failed to create new sessionId.");
            }

            return true;
        }
    }
}
