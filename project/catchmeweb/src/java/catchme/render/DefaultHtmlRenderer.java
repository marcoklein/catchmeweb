package catchme.render;

import catchme.HtmlBuilder;
import game.Entity;
import game.World;

/**
 *
 * @author Marco Klein
 */
public class DefaultHtmlRenderer extends HtmlRenderer {
    
    

    @Override
    protected void renderHtml(World world, HtmlBuilder html) {
        html.add("<table>");
        
        // get world size
        
        // add table row
        for (Entity entity : world.getEntities()) {
            entity.getControl(Entity);
        }
        
        
        
        html.add("</table>");
    }
    
}
