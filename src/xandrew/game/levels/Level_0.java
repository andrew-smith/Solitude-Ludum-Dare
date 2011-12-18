

package xandrew.game.levels;

import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.Map;
import javax.media.opengl.GL;
import xandrew.game.CameraController;
import xandrew.game.GameLevel;
import xandrew.game.light.ExitPortal;
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

        setExit(destination);

        //set player starting position
        moveToPostion(75, 75);

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

    
    
    //list of all the positions of the mirrors
    private LightBeam sourceLight = new LightBeam(50, 50);

    /** What we are trying to light up */
    private ExitPortal destination = new ExitPortal(750f, 550f);

    /** These need to be in the order of what is lit */
    private LightBeam[] lightBeams = {
        sourceLight,
        new LightBeam(50, 550),
        new LightBeam(340, 540),
        new LightBeam(400, 350),
        new LightBeam(750, 350),
    };



    @Override
    public void interact()
    {


        CameraController key = CameraController.DEFAULT_CONTROLLER;



        checkLightBeams();

        float playerX = getCurrentPlayerX();
        float playerY = getCurrentPlayerY();

        //check if the player is by any light sources
        for (LightBeam lb : lightBeams)
        {
            //and ensure it is on
           if(lb.isPowered)
           {
               //standardize rotation
               while(lb.rotation > 360) lb.rotation -= 360;
               while(lb.rotation < 0) lb.rotation += 360;

               if(playerX > lb.xPos - (lb.getScale() * 2) && playerX <lb.xPos + (lb.getScale() * 2))
               {
                   if(playerY > lb.yPos - (lb.getScale() * 2) && playerY < lb.yPos + (lb.getScale() * 2))
                   {
                       //90 degrees is UP
                       if(key.poll(KeyEvent.VK_UP))
                       {
                           if(!(lb.rotation > 89 && lb.rotation < 91))
                           {
                               if(lb.rotation > 90 && lb.rotation < 270)
                               {
                                   lb.rotation--;
                               }
                               else
                               {
                                   lb.rotation++;
                               }
                           }
                           else lb.rotation = 90;
                       }
                       if(key.poll(KeyEvent.VK_DOWN))
                       {
                           if(!(lb.rotation > 269 && lb.rotation < 271))
                           {
                               if(lb.rotation >= 90 && lb.rotation <= 270)
                               {
                                   lb.rotation++;
                               }
                               else
                               {
                                   lb.rotation--;
                               }
                           }
                           else lb.rotation = 270;
                       }
                       if(key.poll(KeyEvent.VK_LEFT))
                       {
                           if(!(lb.rotation > 179 && lb.rotation < 181))
                           {
                               if(lb.rotation <= 180 && lb.rotation >= 0)
                               {
                                   lb.rotation++;
                               }
                               else
                               {
                                   lb.rotation--;
                               }
                           }
                           else lb.rotation = 180;
                       }
                       if(key.poll(KeyEvent.VK_RIGHT))
                       {
                           if((lb.rotation > 1))
                           {
                               if(lb.rotation <= 180 && lb.rotation >= 0)
                               {
                                   lb.rotation--;
                               }
                               else
                               {
                                   lb.rotation++;
                               }
                           }
                           else lb.rotation = 0;
                       }
                   }
               }
           }
        }

        checkLightBeams();
    }



    private void checkLightBeams()
    {
        //source light is always powered
        sourceLight.isPowered = true;

        //save the states of all the lights (if one changes to on then we will play a sound */
        Map<LightBeam, Boolean> lightStates = new HashMap<LightBeam, Boolean>();

        for (LightBeam lb : lightBeams) {
            lightStates.put(lb, lb.isPowered);
        }

        //power off all the lightbeams to begin with
        for (LightBeam lb : lightBeams) {
            if(lb != sourceLight)
                lb.isPowered = false;
        }


        

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

        //check exit portal
        boolean originalPortalState = destination.isActive;
        destination.isActive = false;
        for(LightBeam lb :lightBeams)
        {
            if(checkExitPortal(lb))
                destination.isActive = true;
        }

        if(!originalPortalState && destination.isActive)
        {
            playSound(Sound.PortalActivated);
        }


        //check if the state of any changed
        for (LightBeam lb : lightBeams) {
            if(lightStates.get(lb) == false && lb.isPowered) //then it has changed
            {
                playSound(Sound.LightOn);
            }
        }
    }



    @Override
    public void drawLightBeams(GL gl) 
    {
        //sourceLight.draw(gl);

        gl.glPushMatrix();
        gl.glTranslatef(0.0f, 0.0f, 6f);
        for(LightBeam lb :lightBeams)
            lb.draw(gl);
        gl.glPopMatrix();

        destination.draw(gl);
    }
}
