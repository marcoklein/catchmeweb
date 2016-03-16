package db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Marco Klein
 */
public class DatabaseProperties {
    private static final Logger LOG = Logger.getLogger(DatabaseProperties.class.getName());
    
    /* This table holds information about the db itself for example version number */
    private static final String TABLE_PROPERTY = "DatabaseProperty";
    private static final String PROPERTY_VALUES = "("
            + "pkey VARCHAR(50),"
            + "value VARCHAR(50),"
            + "PRIMARY KEY(pkey)"
            + ")";
    
    public String KEY_VERSION = "version";
    
    private DatabaseHelper database;
    private boolean initialized = false;
    
    private String version;

    public DatabaseProperties(DatabaseHelper database) {
        this.database = database;
    }
    
    private void initialize() {
        if (!initialized) {
            LOG.info("Initializing Database Properties.");
            initialized = true;
            database.createTableIfNotExists(TABLE_PROPERTY, PROPERTY_VALUES);
        }
    }
    
    public int getDatabaseVersion() {
        if (version == null) {
            // load version
            version = getProperty(KEY_VERSION);
            if (version == null) {
                version = "0";
                setProperty(KEY_VERSION, String.valueOf(DatabaseServlet.DB_VERSION));
            }
        }
        return Integer.parseInt(version);
    }
    
    /**
     * Sets the given property and updates it on the database.
     * 
     * @param key
     * @param property 
     */
    public void setProperty(String key, String property) {
        initialize();
        PreparedStatement stmt = null;
        try {
            // create statement
            Connection connection = database.getConnection();
            stmt = connection.prepareStatement("UPDATE " + TABLE_PROPERTY
                    + " SET VALUE = ? WHERE PKEY = ?");

            // set properties
            stmt.setString(1, property);
            stmt.setString(2, key);
            
            stmt.execute();

            // clean up
            stmt.close();
            LOG.log(Level.INFO, "Updated key {0} to property {1}", new Object[]{key, property});
        } catch (SQLException ex) {
            LOG.info("Can't update property - creating new one (added for the first time).");
//            Logger.getLogger(DatabaseProperties.class.getName()).log(Level.SEVERE, null, ex);
            try {
                stmt.close();
                // create statement
                Connection connection = database.getConnection();
                stmt = connection.prepareStatement("INSERT INTO " + TABLE_PROPERTY
                        + "("
                        + "pkey,"
                        + "value"
                        + ") VALUES (?, ?)");
                
                // set properties
                stmt.setString(1, key);
                stmt.setString(2, property);
                
                stmt.execute();
                
                // clean up
                stmt.close();
            } catch (SQLException e) {
                Logger.getLogger(DatabaseProperties.class.getName()).log(Level.SEVERE, null, e);
            }
        }
    }
    
    /**
     * Gets the property with given key.
     * 
     * @param key
     * @return 
     */
    public String getProperty(String key) {
        initialize();
            Statement stmt = null;
        try {
            Connection connection = database.getConnection();
            stmt = connection.createStatement();
            ResultSet result = stmt.executeQuery("SELECT value FROM " + TABLE_PROPERTY + " WHERE pkey = " + key);
            String value = result.getString("value");
            stmt.close();
            return value;
        } catch (SQLException ex) {
            LOG.log(Level.WARNING, "Could not find property for key {0}", key);
//            Logger.getLogger(DatabaseProperties.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException ex) {
                    Logger.getLogger(DatabaseProperties.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return null;
    }
    
}
