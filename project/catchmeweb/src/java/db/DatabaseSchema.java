package db;

/**
 *
 * @author Marco Klein
 */
public final class DatabaseSchema {
    
    
    /* Log tables */
    
    public static class Log {
        public static final String TABLE_NAME = "Log";
        public static final String COL_ID = "logId";
        public static final String COL_SESSION_ID = "sessionId";
        public static final String COL_LOGGER_NAME = "loggerName";
        public static final String COL_MESSAGE = "message";
        public static final String COL_TIMESTAMP = "timestamp";
        public static final String COL_LEVEL = "level";
    }
    
    public static class Session {
        public static final String TABLE_NAME = "Session";
        public static final String COL_ID = "sessionId";
        public static final String COL_COUNTRY = "country";
    }
    
}
