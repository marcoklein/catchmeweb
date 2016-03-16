package db;

import java.sql.SQLException;

/**
 * This is the command of the command pattern which is used to manage database.
 * 
 * Defines the logic of a SQL query.
 *
 * @author Marco Klein
 */
public abstract class DatabaseRequest {
    protected abstract Object applyRequest(DatabaseHelper database) throws SQLException;
}
