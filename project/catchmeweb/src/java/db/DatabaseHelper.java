package db;

import db.request.DropTable;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Facade to make database requests easier.
 *
 * @author Marco Klein
 */
public class DatabaseHelper {
    private static final Logger LOG = Logger.getLogger(DatabaseHelper.class.getName());
    
    private Connection connection;
    private DatabaseProperties properties;

    public DatabaseHelper() {
        this.properties = new DatabaseProperties(this);
    }

    public DatabaseHelper(Connection connection) {
        this.connection = connection;
        this.properties = new DatabaseProperties(this);
    }
    
    public ArrayList<String> getAllTableNames() {
        ArrayList<String> list = new ArrayList<>();
        try {
            String[] types = {"TABLE"};
            DatabaseMetaData md = connection.getMetaData();
            ResultSet rs = md.getTables(null, null, "%", types);
            while (rs.next()) {
                list.add(rs.getString(3));
            }
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseHelper.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }
    
    public ResultSet selectAllFromTable(String tableName) {
        try {
            Statement stmt = connection.createStatement();
            ResultSet result = stmt.executeQuery("SELECT * FROM " + tableName);
            return result;
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseHelper.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public boolean createTableIfNotExists(String tableName, String tableValues) {
        Statement stat = null;
        try {
            DatabaseMetaData md = connection.getMetaData();
            ResultSet rs = md.getTables(null, null, tableName.toUpperCase(), null);
            if (rs.next()) {
                return false;
            }
            stat = connection.createStatement();
            LOG.log(Level.INFO, "Trying to execute {0}", ("CREATE TABLE " + tableName + " " + tableValues));
            stat.execute("CREATE TABLE " + tableName + " " + tableValues);
            LOG.log(Level.INFO, "Created table {0}.", tableName);
        } catch (Exception ex) {
            Logger.getLogger(DatabaseHelper.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        } finally {
            try {
                if (stat != null) {
                    stat.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(DatabaseHelper.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return true;
    }
    
    public boolean dropTable(String tableName) {
        LOG.log(Level.INFO, "Dropping table {0}", tableName);
        Boolean result = executeSqlRequest(new DropTable(tableName));
        if (result == null) {
            return false;
        }
        return result;
    }
    
    public <T> T executeSqlRequest(DatabaseRequest request) {
        try {
            return (T) request.applyRequest(this);
        } catch (Exception ex) {
            Logger.getLogger(DatabaseHelper.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public DatabaseProperties getProperties() {
        return properties;
    }

}
