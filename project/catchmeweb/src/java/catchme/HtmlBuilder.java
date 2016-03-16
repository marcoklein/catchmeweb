package catchme;

/**
 *
 * @author Marco Klein
 */
public class HtmlBuilder {
    
    private StringBuilder html = new StringBuilder();;

    public HtmlBuilder() {
    }
    
    /**
     * Adds given html text.
     * 
     * @param html 
     */
    public HtmlBuilder add(String html) {
        this.html.append(html);
        return this;
    }
    
    /**
     * Adds another html builder by calling build() on it.
     * 
     * @param html
     * @return 
     */
    public HtmlBuilder add(HtmlBuilder html) {
        this.html.append(html.build());
        return this;
    }
    
    public HtmlBuilder add(String element, String content) {
        return add(element, content, null, null);
    }
    
    public HtmlBuilder add(String element, String content, String classes) {
        return add(element, content, classes, null);
    }
    
    public HtmlBuilder add(String element, String content, String classes, String id) {
        html.append("<");
        html.append(element);
        if (id != null) {
            // add id
            html.append(" id=\"");
            html.append(id);
            html.append("\"");
        }
        if (classes != null) {
            // add classes
            html.append(" class=\"");
            html.append(classes);
            html.append("\"");
        }
        html.append(">");
        
        // add content
        html.append(content);
        
        // close element
        html.append("</");
        html.append(element);
        html.append(">");
        
        
        
        return this;
    }
    
    
    public HtmlBuilder clear() {
        html = new StringBuilder();
        return this;
    }
    
    private String build() {
        return html.toString();
    }
    
    
}
