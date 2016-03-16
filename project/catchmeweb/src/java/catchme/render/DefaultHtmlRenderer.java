package catchme.render;

import catchme.CatchMeGame;
import catchme.HtmlBuilder;
import catchme.control.FieldControl;
import game.Entity;
import game.World;

/**
 *
 * @author Marco Klein
 */
public class DefaultHtmlRenderer extends HtmlRenderer {
    
    private CatchMeGame game;

    public DefaultHtmlRenderer(CatchMeGame game) {
        this.game = game;
    }
    
    @Override
    protected void renderHtml(World world, HtmlBuilder html) {
        html.add("<table>");
        
        // get world size
        int width = game.getWidth();
        int height = game.getHeight();
        
        // add table row
        for (Entity entity : world.getEntities()) {
            FieldControl control = entity.getControl(FieldControl.class);
            control.getX();
        }
        
        
        
        html.add("</table>");
    }
    
}
