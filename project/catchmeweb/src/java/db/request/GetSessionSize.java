package db.request;

import db.DatabaseHelper;
import db.DatabaseRequest;
import db.DatabaseSchema;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Number of sessions.
 *
 * @author Marco Klein
 */
public class GetSessionSize extends DatabaseRequest {

    @Override
    protected Object applyRequest(DatabaseHelper database) throws SQLException {
        ResultSet rs = database.getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY).executeQuery("SELECT * FROM " + DatabaseSchema.Session.TABLE_NAME);
        rs.last();
        int size = rs.getRow();
        rs.close();
        return size;
    }
    
}
