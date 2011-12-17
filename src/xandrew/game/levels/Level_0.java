

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
        moveToPostion(75, 75);

        //adds all the light beams to the collision
        //addLightBeam(sourceLight);
        for(LightBeam lb :lightBeams)
        {
            addLightBeam(lb);
        }

        //first light testing
        interact();
    }

    
    
    //list of all the positions of the mirrors
    private LightBeam sourceLight = new LightBeam(50, 50);

    /** What we are trying to light up */
    private float[] destination = {750f, 550f};

    /** These need to be in the order of what is lit */
    private LightBeam[] lightBeams = {
        sourceLight,
        new LightBeam(50, 550),
        new LightBeam(340, 540),
    };



    @Override
    public void interact()
    {
        checkLightBeams();

        float playerX = getCurrentPlayerX();
        float playerY = getCurrentPlayerY();

        //check if the player is by any light sources
        for (LightBeam lb : lightBeams)
        {
            //and ensure it is on
           if(lb.isPowered)
           {
               if(playerX > lb.xPos - (lb.getScale() * 2) && playerX <lb.xPos + (lb.getScale() * 2))
               {
                   if(playerY > lb.yPos - (lb.getScale() * 2) && playerY < lb.yPos + (lb.getScale() * 2))
                   {
                       lb.rotation++;
                   }
               }
           }
        }

        checkLightBeams();
    }



    private void checkLightBeams()
    {
        //power off all the lightbeams to begin with
        for (LightBeam lb : lightBeams) {
            lb.isPowered = false;
        }

        //source light is always powered
        sourceLight.isPowered = true;

        //so always project it.
        projectLight(sourceLight);
        //sourceLight.rotation ++;
        //find out if any of the other light beams are powered
        for(LightBeam lSource :lightBeams)
        {
            for(LightBeam lDest : lightBeams)
            {
                final float x = lSource.destX;
                final float y = lSource.destY;
                if(lSource != lDest && lSource.isPowered)
                {
                    float destScale = lDest.getScale();
                    //get if source is projecting at dest light
                    if(x > lDest.xPos - destScale && x < lDest.xPos + destScale)
                    {
                        if(y > lDest.yPos - destScale && y < lDest.yPos + destScale)
                        {
                            lDest.isPowered = true;
                            projectLight(lDest);
                        }
                    }
                }
            }
        }
    }


    @Override
    public void drawLightBeams(GL gl) 
    {
        sourceLight.draw(gl);

        for(LightBeam lb :lightBeams)
            lb.draw(gl);
    }
}
