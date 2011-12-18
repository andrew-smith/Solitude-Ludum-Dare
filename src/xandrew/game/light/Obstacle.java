

package xandrew.game.light;

import javax.media.opengl.GL;
import scene.GLRenderable;

/**
 * Defines an obstacle in the game
 * @author Andrew
 */
public abstract class Obstacle implements GLRenderable
{


    /** True if obstacle is active */
    private boolean active;


    public Obstacle()
    {
        this.active = true;
    }



    public boolean isActive()
    {
        return this.isActive();
    }


    public void setActive(boolean value)
    {
        this.active = value;
    }

    /**
     * Checks if this obstacle contains the specific point
     * @param x
     * @param y
     * @return
     */
    public abstract boolean contains(float x, float y);

    /**
     * True - this obstacle affects the laser 
     * @return
     */
    public boolean affectsLaser()
    {
        return true;
    }

}
