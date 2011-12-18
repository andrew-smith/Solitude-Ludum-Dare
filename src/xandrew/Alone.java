package xandrew;

import com.sun.opengl.util.Animator;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLCanvas;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.glu.GLU;
import javax.swing.JFrame;
import scene.GLRenderable;
import scene.Scene;
import xandrew.game.CameraController;
import xandrew.game.Game;
import xandrew.game.Menu;



/**
 * Alone.java <BR>
 * author: Andrew && Brian Paul (converted to Java by Ron Cemer and Sven Goethel) <P>
 *
 * This version is equal to Brian Paul's version 1.2 1999/10/21
 */
public class Alone implements GLEventListener {


    //TODO put this in
    private static Game game;

    public static int GAME_LEVEL = 0;

    public static void main(String[] args) {
        JFrame frame = new JFrame("Solitude - Ludum Dare");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        GLCanvas canvas = new GLCanvas();

        canvas.addGLEventListener(new Alone());
        canvas.addKeyListener(CameraController.DEFAULT_CONTROLLER);
        frame.add(canvas);
        //Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setSize(640, 480);
        frame.setExtendedState(Frame.MAXIMIZED_BOTH);
        final Animator animator = new Animator(canvas);
        frame.addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent e) {
                // Run this on another thread than the AWT event queue to
                // make sure the call to Animator.stop() completes before
                // exiting
                new Thread(new Runnable() {

                    public void run() {
                        animator.stop();
                        //System.exit(0);
                    }
                }).start();
            }
        });
        // Center frame
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        animator.start();
    }



    public void init(GLAutoDrawable drawable) {
        // Use debug pipeline
        // drawable.setGL(new DebugGL(drawable.getGL()));

        GL gl = drawable.getGL();
        //System.err.println("INIT GL IS: " + gl.getClass().getName());

        // Enable VSync
        gl.setSwapInterval(1);

        // Setup the drawing area and shading mode
        gl.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        gl.glShadeModel(GL.GL_SMOOTH); // try setting this to GL_FLAT and see what happens.

        //
        //gl.glEnable(GL.GL_LIGHTING);

        gl.glEnable(GL.GL_DEPTH_TEST);
        gl.glEnable (GL.GL_BLEND);
        gl.glBlendFunc (GL.GL_SRC_ALPHA, GL.GL_ONE_MINUS_SRC_ALPHA);


        game = new Game();
        game.setLevel(GAME_LEVEL);
        game.init(gl);
    }

    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
        GL gl = drawable.getGL();
        GLU glu = new GLU();

        if (height <= 0) { // avoid a divide by zero error!
        
            height = 1;
        }
        final float h = (float) width / (float) height;
        gl.glViewport(0, 0, width, height);
        gl.glMatrixMode(GL.GL_PROJECTION);
        gl.glLoadIdentity();
        glu.gluPerspective(100.0f, h, 0.5, 110.0);
        gl.glMatrixMode(GL.GL_MODELVIEW);
        gl.glLoadIdentity();



    }

    public void display(GLAutoDrawable drawable) {
        GL gl = drawable.getGL();

        // Clear the drawing area
        gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);
        // Reset the current matrix to the "identity"
        gl.glLoadIdentity();


        game.update();
        game.draw(gl);

        // Flush all drawing operations to the graphics card
        gl.glFlush();
    }

    public void displayChanged(GLAutoDrawable drawable, boolean modeChanged, boolean deviceChanged) {
    }
}

