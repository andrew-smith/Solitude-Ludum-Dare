/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package xandrew.game.levels;

import javax.media.opengl.GL;
import xandrew.game.GameLevel;
import xandrew.game.light.ExitPortal;
import xandrew.game.light.LightBeam;

/**
 *
 * @author Andrew
 */
public class Level_2 extends GameLevel
{
    
    public Level_2()
    {
        super(2);
    }

    @Override
    public void init(GL gl)
    {
        super.init(gl);

        setExit(destination);

        //set player starting position
        moveToPostion(75, 50);

        //adds all the light beams to the collision
        //addLightBeam(sourceLight);
        for(LightBeam lb :lightBeams)
        {
            addLightBeam(lb);
        }

        destination.init(gl);

        //first light testing
        interact();
    }


    /** What we are trying to light up */
    private ExitPortal destination = new ExitPortal(400, 320);

    /** These need to be in the order of what is lit */
    private LightBeam[] lightBeams = {
        new LightBeam(200, 350, true),
        new LightBeam(333, 460),
        new LightBeam(540, 420),
        //new LightBeam(750, 350),
    };

}
