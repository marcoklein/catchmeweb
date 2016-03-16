package db;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Superclass for all servlets which process database queries.
 * 
 * To use a Database servlet you have to define an action using the setAttribute
 * method of the request. Use ATR_ACTION as key.
 * 
 * If a sql request returns something you can access it by using ATR_RESPONSE
 * as a key. f.i. getAttribute(ATR_RESPONSE).
 * 
 * Each Database Servlet manages a specific part of the database. Look at the
 * database layout specification for more information.
 *
 * @author Marco Klein
 */
@WebServlet("/database")
public class DatabaseServlet extends HttpServlet {
    private static final Logger LOG = Logger.getLogger(DatabaseServlet.class.getName());
    
    public static final int DB_VERSION = 10;
    
    public static final String ATR_REQUEST = "dbrequest";
    public static final String ATR_RESULT = "dbresponse";
    private static final String JDBC_DRIVER = "org.apache.derby.jdbc.EmbeddedDriver";
    private static final String DB_URL = "jdbc:derby:idea;create=true";
    private static final String DB_USER = "";
    private static final String DB_PASSWORD = "";
    
    
    /* Database Layout using DatabaseSchema to define database commands to create db. */
    
//    private static final String IDEA_TOUR_VALUES = "("
//            + DatabaseSchema.IdeaTour.COL_ID + " INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),"
//            + DatabaseSchema.IdeaTour.COL_CREATED_BY + " INTEGER,"
//            + DatabaseSchema.IdeaTour.COL_DATE_CREATED + " TIMESTAMP,"
//            + DatabaseSchema.IdeaTour.COL_ACTIVE + " BOOLEAN,"
//            + DatabaseSchema.IdeaTour.COL_NAME + " VARCHAR(50),"
//            + DatabaseSchema.IdeaTour.COL_DESCRIPTION + " VARCHAR(500),"
//            + "PRIMARY KEY (" + DatabaseSchema.IdeaTour.COL_ID + ")"
//            + ")";
    
    /* Log tables */
    private static final String LOG_VALUES = "("
            + DatabaseSchema.Log.COL_ID + " INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),"
            + DatabaseSchema.Log.COL_SESSION_ID + " INTEGER,"
            + DatabaseSchema.Log.COL_LOGGER_NAME + " VARCHAR(100),"
            + DatabaseSchema.Log.COL_MESSAGE + " VARCHAR(8000),"
            + DatabaseSchema.Log.COL_TIMESTAMP + " TIMESTAMP,"
            + DatabaseSchema.Log.COL_LEVEL + " VARCHAR(31),"
            + "PRIMARY KEY (" + DatabaseSchema.Log.COL_ID + ")"
            + ")";
    
    private static final String SESSION_VALUES = "("
            + DatabaseSchema.Session.COL_ID + " INTEGER GENERATED ALWAYS AS IDENTITY,"
            + DatabaseSchema.Session.COL_COUNTRY + " VARCHAR(255)"
            //+ "PRIMARY KEY (" + DatabaseSchema.Session.COL_ID + ")"
            + ")";
    
    
    
    
    
    private DatabaseHelper database = new DatabaseHelper();

    public DatabaseServlet() {
    }
    
    
    /**
     * Helper to easily make a database request.
     * 
     * @param <T>
     * @param sqlRequest
     * @param req
     * @param resp
     * @return
     * @throws ServletException
     * @throws IOException 
     */
    public static <T> T makeDatabaseRequest(DatabaseRequest sqlRequest, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute(DatabaseServlet.ATR_REQUEST, sqlRequest);
        RequestDispatcher dispatcher = req.getRequestDispatcher("/database");
        dispatcher.include(req, resp);
        return (T) req.getAttribute(DatabaseServlet.ATR_RESULT);
    }

    @Override
    public void init() throws ServletException {
        super.init();
        try {
            Class.forName(JDBC_DRIVER);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DatabaseServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        // set up database
        Connection connection;
        try {
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            database.setConnection(connection);
            
            // check if db has to upgrade
            DatabaseProperties properties = database.getProperties();
            LOG.log(Level.INFO, "Database version: {0}", properties.getDatabaseVersion());
            if (properties.getDatabaseVersion() != DB_VERSION) {
                LOG.log(Level.INFO, "Upgrading database to version {0}", DB_VERSION);
                upgradeDatabase(database);
            }
            
            LOG.info("Setting up database...");
            setupDatabase(database);
            LOG.info("Database connection established.");
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    protected void upgradeDatabase(DatabaseHelper database) {
        // TODO drop tables
        database.dropTable(DatabaseSchema.Log.TABLE_NAME);
        database.dropTable(DatabaseSchema.Session.TABLE_NAME);
    }
    
    /**
     * Set up table of the database.
     * 
     * @param database 
     */
    protected void setupDatabase(final DatabaseHelper database) {
        // TODO create tables
        database.createTableIfNotExists(DatabaseSchema.Log.TABLE_NAME, LOG_VALUES);
        database.createTableIfNotExists(DatabaseSchema.Session.TABLE_NAME, SESSION_VALUES);
    }
    
    protected void processRequest(DatabaseRequest dbRequest, DatabaseHelper database, HttpServletRequest req, HttpServletResponse resp) {
        Object result = database.executeSqlRequest(dbRequest);
        req.setAttribute(ATR_RESULT, result);
    }
    

    /**
     * Handles post and get requests.
     * 
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException 
     */
    private void handleRequest(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // test if there is a valid action
        DatabaseRequest dbRequest;
        if ((dbRequest = (DatabaseRequest) req.getAttribute(ATR_REQUEST)) == null) {
            LOG.severe("Called Database without specifying a DatabaseRequest.");
            return;
        }
        // create a connection and let child class process the request
        processRequest(dbRequest, database, req, resp);

    }
    
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        handleRequest(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        handleRequest(req, resp);
    }

    @Override
    public void destroy() {
        super.destroy();
        LOG.info("Destroyed Database Servlet.");
//        try {
//            // close connection
//            database.getConnection().close();
//            LOG.info("Database connection closed.");
//        } catch (SQLException ex) {
//            Logger.getLogger(DatabaseServlet.class.getName()).log(Level.SEVERE, null, ex);
//        }
    }
    
    
}
