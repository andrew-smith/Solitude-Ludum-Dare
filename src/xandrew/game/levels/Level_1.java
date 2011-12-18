

package xandrew.game.levels;

import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.Map;
import javax.media.opengl.GL;
import xandrew.game.CameraController;
import xandrew.game.GameLevel;
import xandrew.game.light.DelayedLightBeam;
import xandrew.game.light.ExitPortal;
import xandrew.game.light.LightBeam;

/**
 * The first level of the
 * @author Andrew
 */
public class Level_1 extends GameLevel
{



    public Level_1()
    {
        super(1);
    }



    @Override
    public void init(GL gl)
    {
        //adds all the light beams to the collision
        //addLightBeam(sourceLight);
        for(LightBeam lb :lightBeams)
        {
            addLightBeam(lb);
        }
        
        super.init(gl);

        setExit(destination);

        //set player starting position
        moveToPostion(75, 50);

        

        destination.init(gl);

        //first light testing
        interact();
    }


    /** What we are trying to light up */
    private ExitPortal destination = new ExitPortal(750f, 550f);

    /** These need to be in the order of what is lit */
    private LightBeam[] lightBeams = {
        new LightBeam(80, 300, true),
        new LightBeam(350, 220),
        new LightBeam(500, 350),
        new LightBeam(750, 350),
    };


}
