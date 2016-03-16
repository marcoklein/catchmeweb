
package module;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.util.Map.Entry;

/**
 * Helper to get data out of a JsonObject.
 *
 * @author Marco Klein
 */
public class JsonHelper {
    private JsonObject json;

    public JsonHelper() {
    }

    public JsonHelper(JsonObject json) {
        this.json = json;
    }
    
    public String get(String key, String defaultValue) {
        if (json.has(key)) {
            return json.get(key).getAsString();
        }
        return defaultValue;
    }
    
    public int get(String key, int defaultValue) {
        if (json.has(key)) {
            return json.get(key).getAsInt();
        }
        return defaultValue;
    }
    
    public boolean get(String key, boolean defaultValue) {
        if (json.has(key)) {
            return json.get(key).getAsBoolean();
        }
        return defaultValue;
    }
    
    /**
     * Copies all entries of the given JsonObject into own JsonObject.
     * 
     * @param other 
     */
    public void copyFrom(JsonObject other) {
        for (Entry<String, JsonElement> entry : other.entrySet()) {
            json.add(entry.getKey(), entry.getValue());
        }
    }
    
    

    public JsonObject getJson() {
        return json;
    }

    public void setJson(JsonObject json) {
        this.json = json;
    }
    
}
