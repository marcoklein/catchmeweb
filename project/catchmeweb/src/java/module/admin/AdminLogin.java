package module.admin;

import com.google.gson.JsonObject;
import main.RequestHandler;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import module.Module;

/**
 *
 * @author Marco Klein
 */
@WebServlet("/admin/login")
public class AdminLogin extends Module {

    private static final Logger LOG = Logger.getLogger(AdminLogin.class.getName());

    @Override
    public void initModule() {

    }

    @Override
    public void setupRequestHandlers(HashMap<String, RequestHandler> requestHandlers) {

        requestHandlers.put("login", new RequestHandler() {
            @Override
            public void handleRequest(JsonObject data, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
                String adminName = data.get("adminName").getAsString();
                String adminPassword = data.get("adminPassword").getAsString();
                if (adminName != null && adminPassword != null) {
                    // TODO replace admin name and admin password by db call
                    if ("marco".equals(adminName) && "jklo__2344".equals(adminPassword)) {
                        // give user access to admin space
                        request.getSession().setAttribute("validAdminLogin", System.currentTimeMillis());
                        request.getSession().setAttribute("adminName", adminName);
                        request.getSession().setAttribute("adminPassword", adminPassword);
                        LOG.log(Level.INFO, "Admin logged in on {0}", new Date());
                        String redirectModule = (String) request.getSession().getAttribute("adminModule");
                        if (redirectModule == null) {
                            redirectModule = "/admin/main";
                        }
                        JsonObject responseJson = new JsonObject();
                        responseJson.addProperty("status", "success");
                        responseJson.addProperty("adminModule", redirectModule);
                        response.getWriter().write(gson.toJson(responseJson));
                        return;
                    }
                }
                LOG.warning("Access denied, invalid login data.");
                // invalid credentials
                JsonObject responseJson = new JsonObject();
                responseJson.addProperty("status", "error");
                responseJson.addProperty("statusText", "Invalid credentials.");
                response.getWriter().write(gson.toJson(responseJson));
            }
        });

    }

    @Override
    public void viewRequest(JsonObject config, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher view = request.getRequestDispatcher("/jsp/module/admin/login.jsp");
        view.forward(request, response);
    }

}
