

package xandrew.alone;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.nio.ByteBuffer;
import java.util.Arrays;
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
    /** Camera key listener */
    private final CameraController cameraController;



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

        cameraController = CameraController.DEFAULT_CONTROLLER;
    }


    /**
     * Gets the keylistener to listen to keyboard events
     * @return
     */
    public KeyListener getKeyListener()
    {
        return cameraController;
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


    private final float CAM_MOVEMENT = 0.1f;
    private final float CAM_ROTATION = 1.0f;


    public void update()
    {

        float[] trans = camera.getTranslation();
        float cam_x = trans[0];
        float cam_y = trans[1];
        float cam_z = trans[2];

        float rotation = camera.getRotation()[0];

        if(cameraController.poll(KeyEvent.VK_W))
        {
            cam_z += CAM_MOVEMENT;
        }
        if(cameraController.poll(KeyEvent.VK_S))
        {
            cam_z -= CAM_MOVEMENT;
        }
        if(cameraController.poll(KeyEvent.VK_A))
        {
            cam_x += CAM_MOVEMENT;
        }
        if(cameraController.poll(KeyEvent.VK_D))
        {
            cam_x -= CAM_MOVEMENT;
        }
        if(cameraController.poll(KeyEvent.VK_LEFT))
        {
            rotation -= CAM_ROTATION;
        }
        if(cameraController.poll(KeyEvent.VK_RIGHT))
        {
            rotation += CAM_ROTATION;
        }

        camera.setTranslation(cam_x, cam_y, cam_z);
        camera.setRotation(rotation, 0.0f, 1.30f, 0.0f);

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
