

package xandrew.alone;

import java.nio.ByteBuffer;
import javax.media.opengl.GL;
import scene.GLRenderable;
import scene.Node;
import scene.RenderableNode;
import scene.Scene;
import scene.shapes.GLSquare;

/**
 *
 * @author Andrew
 */
public class Game implements GLRenderable
{

    /** Complete scene */
    private Scene scene;
    /** Root node from scene */
    private final Node rootNode;

    /** Camera node */
    private final Node camera;



    /** List of remote players node */
    private Node remotePlayerNode;


    /**
     * Creates a new game 
     */
    public Game()
    {
        scene = new Scene();
        rootNode = scene.getRootNode();
        camera = new Node("Camera");
        camera.setTranslation(0.0f, -1.0f, -6.0f);
    }




    public void init(GL gl)
    {
        //init fog
        gl.glEnable(GL.GL_FOG);
        gl.glFogi(GL.GL_FOG_MODE, GL.GL_EXP2);
        gl.glFogfv(GL.GL_FOG_COLOR, COLOUR_BLACK, 0);
        gl.glFogf(GL.GL_FOG_DENSITY, 0.1f);
        gl.glHint(GL.GL_FOG_HINT, GL.GL_NICEST);

        //create floor
        GLSquare squ = new GLSquare();
        squ.setFileName("images/floor.png");
        RenderableNode floor = new RenderableNode("Flor", squ);
        floor.setRotation(90, 1.0f, 0.0f, 0.0f);
        floor.setScale(100f); //a pretty large floor
        rootNode.addChild(floor);

        //create node for remote playes
        remotePlayerNode = new Node("Remote Player Node");
        rootNode.addChild(remotePlayerNode);

        scene.init(gl);
    }

    public void update()
    {
        scene.update();
    }

    
    private final float[] COLOUR_BLACK = new float[]{0.0f, 0.0f, 0.0f, 1.0f};


    public void draw(GL gl)
    {

        

        gl.glTranslatef(-camera.getNodeGlobalX(), -camera.getNodeGlobalY(), -camera.getNodeGlobalZ());
        gl.glMultMatrixf(camera.getNodeGlobalTransform(), 0);
        gl.glTranslatef(camera.getNodeGlobalX(), camera.getNodeGlobalY(), camera.getNodeGlobalZ());

        scene.draw(gl);
    }



    /**
     * Inner class for handling server connection
     */
    private class GameServerListener implements networking.client.ServerListener
    {

        public void recieveMessage(ByteBuffer message)
        {
        }

    }
}
