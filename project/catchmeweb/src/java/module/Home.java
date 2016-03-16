package module;

import main.RequestHandler;
import com.google.gson.JsonObject;
import java.io.IOException;
import java.util.HashMap;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Marco Klein
 */
@WebServlet("/home")
public class Home extends Module {
    private static final Logger LOG = Logger.getLogger(Home.class.getName());

    @Override
    public void initModule() {
    }

    @Override
    public void setupRequestHandlers(HashMap<String, RequestHandler> requestHandlers) {
    }


    @Override
    public void viewRequest(JsonObject data, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher view = request.getRequestDispatcher("/jsp/module/home.jsp");
        view.forward(request, response);
    }




}
