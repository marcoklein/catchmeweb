package db.request;

import db.DatabaseHelper;
import db.DatabaseRequest;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *
 * @author Marco Klein
 */
public class DropTable extends DatabaseRequest {
    
    private String tableName;

    public DropTable(String tableName) {
        this.tableName = tableName;
    }

    @Override
    protected Object applyRequest(DatabaseHelper database) throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("DROP TABLE " + tableName);
        stmt.execute();
        return true;
    }
    
}
