

package xandrew.game;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Arrays;

/**
 * Basic camera poller class
 * @author Andrew
 */
public class CameraController implements KeyListener
{

    
    public static final CameraController DEFAULT_CONTROLLER = new CameraController();


    /** Keyboard array */
    private boolean[] keys = new boolean[256];


    private CameraController()
    {
        //populate all keys as being inactive
        Arrays.fill(keys, false);
    }

    public boolean poll(int keycode)
    {
        return keys[keycode];
    }

    public void keyTyped(KeyEvent e)
    {
        //nothing
    }

    public void keyPressed(KeyEvent e)
    {
        keys[e.getKeyCode()] = true;
    }

    public void keyReleased(KeyEvent e)
    {
        keys[e.getKeyCode()] = false;
    }

}