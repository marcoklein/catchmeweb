package module.admin;

import com.google.gson.JsonObject;
import main.RequestFilter;
import main.RequestHandler;
import java.io.IOException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import module.Module;

/**
 * Super class of all Admin Modules.
 * Checks for credentials as the user requests the page. If no credentials are
 * given the user will be redirected to the Admin Login.
 *
 * @author Marco Klein
 */
public abstract class AdminModule extends Module {
    private static final Logger LOG = Logger.getLogger(AdminModule.class.getName());
    /**
     * Time in ms after an admin will be asked to enter credentials if he was inactive.
     */
    public static final long LOGIN_TIME = 1000 * 60 * 10; // 10 min

    @Override
    public void init() throws ServletException {
        super.init();
        addRequestFilter(new RequestCredentialFilter());
    }
    
    /* Define order of methods */

    @Override
    protected abstract void setupRequestHandlers(HashMap<String, RequestHandler> requestHandlers);

    @Override
    protected abstract void initModule();

    @Override
    protected abstract void viewRequest(JsonObject config, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException;


    /**
     * Checks if request provides valid credentials.
     */
    static class RequestCredentialFilter implements RequestFilter {

        @Override
        public boolean filterRequest(String action, JsonObject data, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            Long validLogin = (Long) request.getSession().getAttribute("validAdminLogin");
            if (validLogin != null) {
                if (System.currentTimeMillis() - validLogin < LOGIN_TIME) {
                    // refresh login time
                    request.getSession().setAttribute("validAdminLogin", System.currentTimeMillis());
                    // valid credentials
                    LOG.info("Valid admin.");
                    return true; // allow access to Admin Module
                }
                LOG.log(Level.INFO, "Admin timed out: {0}", (System.currentTimeMillis() - validLogin));
            }
            LOG.info("No valid login given - redirecting to login page.");
            // inform login module that we want to proceed to this page after login
            request.getSession().setAttribute("adminModule", request.getServletPath());
            // invalid credentials - redirect to admin login
            response.sendRedirect("/admin/login");
            return false; // no processing of this request
        }

    }

}
