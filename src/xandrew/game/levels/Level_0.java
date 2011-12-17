

package xandrew.game.levels;

import javax.media.opengl.GL;
import xandrew.game.GameLevel;
import xandrew.game.light.LightBeam;

/**
 * The first level of the 
 * @author Andrew
 */
public class Level_0 extends GameLevel
{

    
    
    public Level_0()
    {
        super(0);
    }



    @Override
    public void init(GL gl)
    {
        super.init(gl);


        //set player starting position
        moveToPostion(50, 50);
    }

    
    
    //list of all the positions of the mirrors
    private LightBeam sourceLight = new LightBeam(50, 50);


    @Override
    public void interact()
    {
        projectLight(sourceLight);
        sourceLight.rotation ++;
    }

    @Override
    public void drawLightBeams(GL gl) 
    {
        sourceLight.draw(gl);
    }
}
