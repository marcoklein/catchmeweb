package db.request;

import db.DatabaseHelper;
import java.sql.SQLException;
import db.DatabaseRequest;

/**
 *
 * @author Marco Klein
 */
public class GetAllTableNames extends DatabaseRequest {

    @Override
    public Object applyRequest(DatabaseHelper database) throws SQLException {
        return database.getAllTableNames();
    }
    
    
    
}
