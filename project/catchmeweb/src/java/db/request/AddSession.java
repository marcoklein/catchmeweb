package db.request;

import db.DatabaseHelper;
import db.DatabaseRequest;
import db.DatabaseSchema;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Random;

/**
 * Adds a new session and returns the session id.
 *
 * @author Marco Klein
 */
public class AddSession extends DatabaseRequest {
    
    private String country;

    public AddSession(String country) {
        this.country = country;
    }

    @Override
    protected Object applyRequest(DatabaseHelper database) throws SQLException {
        Connection connection = database.getConnection();
        int sessionId = new Random().nextInt(Integer.MAX_VALUE);
        PreparedStatement stmt = connection.prepareStatement("INSERT INTO " + DatabaseSchema.Session.TABLE_NAME + " "
                + "("
                + DatabaseSchema.Session.COL_COUNTRY + ""
                + ") VALUES (?)", Statement.RETURN_GENERATED_KEYS);
        
        // set properties
        stmt.setString(1, country);
        
        // execute statement
        stmt.execute();
        
        // get generated session id
//        ResultSet rs = stmt.getGeneratedKeys();
//        int sessionId = rs.getInt(1);
//        rs.close();

        // clean up
        stmt.close();
        return sessionId;
    }
    
}
