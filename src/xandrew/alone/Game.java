package xandrew.alone;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.nio.ByteBuffer;
import java.util.Arrays;
import javax.media.opengl.GL;
import javax.media.opengl.glu.GLU;
import scene.GLRenderable;
import scene.Node;
import scene.RenderableNode;
import scene.Scene;
import scene.shapes.GLSquare;

/**
 *
 * @author Andrew
 */
public class Game implements GLRenderable {

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
    public Game() {
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
    public KeyListener getKeyListener() {
        return cameraController;
    }

    public void init(GL gl) {
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
    //camera vars from: http://www.swiftless.com/tutorials/opengl/camera2.html
    float xpos = 0, ypos = 1, zpos = 0, xrot = 0, yrot = 0, angle = 0.0f;
    private final float CAM_ROTATION = 1.0f;
    private final float CAM_MOVEMENT = 0.5f;
    private final float PI = 3.141592654f;

    public void update() {


        if (cameraController.poll(KeyEvent.VK_W)) {
            float xrotrad, yrotrad;
            yrotrad = (yrot / 180 * PI);
            xrotrad = (xrot / 180 * PI);
            xpos += (Math.sin(yrotrad)) / PI * CAM_MOVEMENT;
            zpos -= (Math.cos(yrotrad)) / PI * CAM_MOVEMENT;
            ypos -= (Math.sin(xrotrad)) / PI * CAM_MOVEMENT;
        }
        if (cameraController.poll(KeyEvent.VK_S)) {
            float xrotrad, yrotrad;
            yrotrad = (yrot / 180 * PI);
            xrotrad = (xrot / 180 * PI);
            xpos -= (Math.sin(yrotrad)) / PI * CAM_MOVEMENT;
            zpos += (Math.cos(yrotrad) / PI * CAM_MOVEMENT);
            ypos += (Math.sin(xrotrad) / PI * CAM_MOVEMENT);
        }
        if (cameraController.poll(KeyEvent.VK_A)) {
            float yrotrad;
            yrotrad = (yrot / 180 * PI);
            xpos -= (Math.cos(yrotrad)) * 0.2;
            zpos -= (Math.sin(yrotrad)) * 0.2;
        }
        if (cameraController.poll(KeyEvent.VK_D)) {
            float yrotrad;
            yrotrad = (yrot / 180 * PI);
            xpos += (Math.cos(yrotrad)) * 0.2;
            zpos += (Math.sin(yrotrad)) * 0.2;
        }
        if (cameraController.poll(KeyEvent.VK_LEFT)) {
            yrot -= CAM_ROTATION;
        }
        if (cameraController.poll(KeyEvent.VK_RIGHT)) {
            yrot += CAM_ROTATION;
        }
        scene.update();
    }
    private final float[] COLOUR_BLACK = new float[]{0.0f, 0.0f, 0.0f, 1.0f};
    GLU glu = new GLU();

    public void draw(GL gl) {


        gl.glRotatef(xrot, 1.0f, 0.0f, 0.0f);  //rotate our camera on teh x-axis (left and right)
        gl.glRotatef(yrot, 0.0f, 1.0f, 0.0f);  //rotate our camera on the y-axis (up and down)
        gl.glTranslated(-xpos, -ypos, -zpos); //translate the screen to the position of our camera

        scene.draw(gl);
    }

    /**
     * Inner class for handling server connection
     */
    private class GameServerListener implements networking.client.ServerListener {

        public void recieveMessage(ByteBuffer message) {
        }
    }
}
