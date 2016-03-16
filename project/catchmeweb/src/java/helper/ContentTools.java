package helper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Marco Klein
 */
public class ContentTools {

    private ContentTools() {
    }

    /**
     * Loads given file and returns the content as a string. Useful if you want
     * to load html, css or javascript files.
     *
     * @param path
     * @return
     */
    public static String loadFileAsString(String path) {
        return readFile(path, StandardCharsets.UTF_8);
    }

    /**
     * Creates the sent html by appending the css and javascript strings.
     *
     * @param css
     * @param html
     * @param javascript
     * @return
     */
    public static String createHtmlFromStrings(String css, String html, String javascript) {
        StringBuilder finalHtml = new StringBuilder();

        // css
        finalHtml.append("<style>");
        finalHtml.append(css);
        finalHtml.append("</style>");

        // html
        finalHtml.append(html);

        // javascript
        finalHtml.append("<script>");
        finalHtml.append(javascript);
        finalHtml.append("</script>");

        return finalHtml.toString();
    }
    
    public static ArrayList<String> readLines(InputStream input) {
        ArrayList<String> lines = new ArrayList<String>();
        BufferedReader reader = new BufferedReader(new InputStreamReader(input));
        try {
            String line = null;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
        } catch (IOException ex) {
            Logger.getLogger(ContentTools.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                input.close();
            } catch (IOException ex) {
                Logger.getLogger(ContentTools.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return lines;
    }

    public static String readFile(InputStream input) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(input));
        StringBuilder stringBuilder = new StringBuilder();
        try {
            String line = null;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line);
            }
        } catch (IOException ex) {
            Logger.getLogger(ContentTools.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                input.close();
            } catch (IOException ex) {
                Logger.getLogger(ContentTools.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return stringBuilder.toString();
    }

    static String readFile(String path, Charset encoding) {
        byte[] encoded;
        try {
            encoded = Files.readAllBytes(Paths.get(path));
            return new String(encoded, encoding);
        } catch (IOException ex) {
            Logger.getLogger(ContentTools.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "";
    }

}
