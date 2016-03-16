package db.request;

import db.DatabaseHelper;
import db.DatabaseRequest;
import db.DatabaseSchema.Log;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Marco Klein
 */
public class GetLogSize extends DatabaseRequest {

    @Override
    protected Object applyRequest(DatabaseHelper database) throws SQLException {
        ResultSet rs = database.getConnection().createStatement().executeQuery("SELECT * FROM " + Log.TABLE_NAME);
        rs.last();
        int size = rs.getRow();
        rs.close();
        return size;
    }
    
}
