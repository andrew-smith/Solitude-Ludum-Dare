/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package xandrew.game.levels;

import javax.media.opengl.GL;
import scene.shapes.GLSquare;
import xandrew.game.GameLevel;
import xandrew.game.light.DelayedLightBeam;
import xandrew.game.light.ExitPortal;
import xandrew.game.light.LightBeam;
import xandrew.game.light.Obstacle;

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

        
        Obstacle popup = new PopupBlock();
        popup.init(gl);
        addObstacle(popup);

        destination.init(gl);

        //first light testing
        interact();
    }


    /** What we are trying to light up */
    private ExitPortal destination = new ExitPortal(400, 320);

    /** When this light is active - the popup box appears */
    LightBeam popupLight = new LightBeam(540, 420);

    /** These need to be in the order of what is lit */
    private LightBeam[] lightBeams = {
        new LightBeam(200, 350, true),
        new LightBeam(333, 460),
        popupLight,
        new DelayedLightBeam(405, 570),
    };






    private class PopupBlock extends Obstacle
    {

        private float xPos = 455,
                      yPos = 380;

        private float scale = 30;

        @Override
        public boolean contains(float x, float y)
        {
            if(x > xPos - scale && x < xPos + scale)
            {
                if(y > yPos - scale && y < yPos + scale)
                {
                    return true;
                }
            }
            return false;
        }

        GLSquare square;

        public void init(GL gl)
        {
            square = new GLSquare();
            square.init(gl);


            square.setColour(new float[]{1.0f, 1.0f, 0.0f, 1.0f});
        }

        public void update()
        {
            //if the light is emitting then this block is in place
            this.setActive(popupLight.isEmitting());
        }

        public void draw(GL gl)
        {

            if(isActive())
            {
                gl.glPushMatrix();
                    gl.glTranslatef(xPos, yPos, 0.0f);
                    gl.glScalef(scale, scale, 0);
                    square.draw(gl);
                gl.glPopMatrix();
            }
        }

        @Override
        public boolean affectsLaser()
        {
            return false;
        }
    }
}
