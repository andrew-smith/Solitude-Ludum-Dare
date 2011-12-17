

package xandrew.game;

import javax.media.opengl.GL;
import scene.GLRenderable;
import scene.Node;
import scene.RenderableNode;
import scene.Scene;

/**
 *
 * @author Andrew
 */
public class Game implements GLRenderable
{
    
    /** Min scene to use */
    private final Scene scene;
    /** Root node */
    private final Node rootNode;


    /** Creates a new game */
    public Game()
    {
        this.scene = new Scene();
        this.rootNode = scene.getRootNode();
    }


    GameLevel level;

    public void init(GL gl)
    {

        level = new GameLevel(0);
        
        rootNode.addChild(level);

        scene.init(gl);
    }

    public void update()
    {

        scene.update();
    }

    public void draw(GL gl)
    {
        gl.glTranslatef(0.0f, 0.0f, -6.0f);
        scene.draw(gl);
    }

}
