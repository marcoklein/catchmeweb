package catchme.render;

import catchme.HtmlBuilder;
import game.Renderer;
import game.World;

/**
 *
 * @author Marco Klein
 */
public abstract class HtmlRenderer implements Renderer {
    
    private HtmlBuilder html;

    @Override
    public void render(World world) {
        if (html == null) {
            throw new IllegalStateException("Renderer called without HtmlView set.");
        }
        renderHtml(world, html);
    }
    
    /**
     * Renders html by calling methods of the given html view.
     * 
     * @param view 
     */
    protected abstract void renderHtml(World world, HtmlBuilder html);

    public HtmlBuilder getHtml() {
        return html;
    }
    
}
