package module.admin;

import com.google.gson.JsonObject;
import main.RequestHandler;
import java.io.IOException;
import java.util.HashMap;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import module.Module;

/**
 * Clears login attributes of session to log Admin out.
 *
 * @author Marco Klein
 */
@WebServlet("/admin/logout")
public class AdminLogout extends Module {
    private static final Logger LOG = Logger.getLogger(AdminLogout.class.getName());

    @Override
    public void initModule() {
    }

    @Override
    public void setupRequestHandlers(HashMap<String, RequestHandler> requestHandlers) {
    }

    @Override
    public void viewRequest(JsonObject config, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // clear session attributes which define a valid admin login
        request.getSession().removeAttribute("validAdminLogin");
        LOG.info("Admin logged out.");

        RequestDispatcher view = request.getRequestDispatcher("/jsp/module/admin/logout.jsp");
        view.forward(request, response);
    }

}
