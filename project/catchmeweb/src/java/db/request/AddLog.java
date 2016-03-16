package db.request;

import db.DatabaseHelper;
import db.DatabaseProperties;
import db.DatabaseRequest;
import db.DatabaseSchema;
import db.DatabaseSchema.Log;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.logging.Logger;

/**
 * Adds a new Log to the database. Returns the session id of the added log.
 *
 * @author Marco Klein
 */
public class AddLog extends DatabaseRequest {
    private static final Logger LOG = Logger.getLogger(AddLog.class.getName());
    
    private int sessionId;
    private String loggerName;
    private String message;
    private Timestamp time;
    private String level;

    public AddLog(int sessionId, String loggerName, String message, Timestamp time, String level) {
        this.sessionId = sessionId;
        this.loggerName = loggerName;
        this.message = message;
        this.time = time;
        this.level = level;
    }
    
    public AddLog(int sessionId, String loggerName, String message, long time, String level) {
        this.sessionId = sessionId;
        this.loggerName = loggerName;
        this.message = message;
        this.time = new Timestamp(time);
        this.level = level;
    }

    @Override
    protected Object applyRequest(DatabaseHelper database) throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("INSERT INTO " + DatabaseSchema.Log.TABLE_NAME
                + "("
                + Log.COL_SESSION_ID + ","
                + Log.COL_LOGGER_NAME + ","
                + Log.COL_MESSAGE + ","
                + Log.COL_TIMESTAMP + ","
                + Log.COL_LEVEL
                + ") VALUES (?, ?, ?, ?, ?)");
        
        // set properties
        stmt.setInt(1, sessionId);
        stmt.setString(2, loggerName);
        stmt.setString(3, message);
        stmt.setTimestamp(4, time);
        stmt.setString(5, level);

        stmt.execute();

        // clean up
        stmt.close();
        return null;
    }
    
    
}
