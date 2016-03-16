package module.admin;

import com.google.gson.JsonObject;
import db.DatabaseServlet;
import db.request.GetSessionSize;
import main.RequestHandler;
import java.io.IOException;
import java.util.HashMap;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Main page of the Admin Space.
 * From here the Admin can navigate through the Admin Space.
 *
 * @author Marco Klein
 */
@WebServlet("/admin/main")
public class AdminMain extends AdminModule {

    @Override
    public void initModule() {
    }

    @Override
    public void setupRequestHandlers(HashMap<String, RequestHandler> requestHandlers) {
    }

    @Override
    public void viewRequest(JsonObject config, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("sessionSize", (int) DatabaseServlet.makeDatabaseRequest(new GetSessionSize(), request, response));
        
        
        RequestDispatcher view = request.getRequestDispatcher("/jsp/module/admin/main.jsp");
        view.forward(request, response);
    }
    
}
