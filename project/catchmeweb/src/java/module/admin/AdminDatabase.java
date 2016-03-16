package module.admin;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import db.DatabaseServlet;
import db.request.GetAllTableNames;
import db.request.SelectAllFromTable;
import main.RequestHandler;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Let the admin see all database entries and execute queries.
 *
 * @author Marco Klein
 */
@WebServlet("/admin/database")
public class AdminDatabase extends AdminModule {

    @Override
    public void initModule() {
    }

    @Override
    public void setupRequestHandlers(HashMap<String, RequestHandler> requestHandlers) {
        requestHandlers.put("table-content", new TableContentRequest());
    }

    @Override
    public void viewRequest(JsonObject config, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // set available tables
        StringBuilder tables = new StringBuilder();
        for (String name : (Iterable<String>) DatabaseServlet.makeDatabaseRequest(new GetAllTableNames(), request, response)) {
            tables.append("<option>");
            tables.append(name);
            tables.append("</option>");
        }
        request.setAttribute("tables", tables.toString());


        RequestDispatcher view = request.getRequestDispatcher("/jsp/module/admin/database.jsp");
        view.forward(request, response);
    }

    class TableContentRequest implements RequestHandler {

        @Override
        public void handleRequest(JsonObject data, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            ResultSet result = null;
            try {
                String tableName = data.get("tableName").getAsString();
                
                result = DatabaseServlet.makeDatabaseRequest(new SelectAllFromTable(tableName), request, response);
                ResultSetMetaData metaData = result.getMetaData();

                int colCount = metaData.getColumnCount();
                // parse result

                // add result to head
                JsonArray head = new JsonArray();
                for (int i = 1; i <= colCount; i++) {
                    head.add(metaData.getColumnName(i));
                }

                // add result to body
                JsonArray body = new JsonArray();
                while (result.next()) {
                    JsonArray row = new JsonArray();
                    for (int i = 1; i <= colCount; i++) {
                        Object el = result.getObject(i);
                        if (el == null) {
                            row.add("NULL");
                        } else {
                            row.add(el.toString());
                        }
                    }
                    body.add(row);
                }

                result.close();



                JsonObject responseJson = new JsonObject();
                responseJson.add("head", head);
                responseJson.add("body", body);

                response.getWriter().printf(gson.toJson(responseJson));




            } catch (SQLException ex) {
                Logger.getLogger(AdminDatabase.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                if (result != null) {
                    try {
                        result.close();
                    } catch (SQLException ex) {
                        Logger.getLogger(AdminDatabase.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }


        }

    }

}
