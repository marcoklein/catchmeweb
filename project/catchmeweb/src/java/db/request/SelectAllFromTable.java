package db.request;

import db.DatabaseHelper;
import db.DatabaseRequest;
import java.sql.SQLException;

/**
 *
 * @author Marco Klein
 */
public class SelectAllFromTable extends DatabaseRequest {
    
    private String tableName;

    public SelectAllFromTable(String tableName) {
        this.tableName = tableName;
    }
    
    
    @Override
    public Object applyRequest(DatabaseHelper database) throws SQLException {
        return database.selectAllFromTable(tableName);
    }
    
    
}
