/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package xandrew.game.light;

/**
 *
 * @author Andrew
 */
public class DelayedLightBeam extends LightBeam
{

    private boolean emitting;

    public DelayedLightBeam(float x, float y)
    {
        super(x, y);

        emitting = false;
    }


    /** How long to sleep */
    private static final long TIME_TO_SLEEP = 1000;


    private long nextUpdate = -1;

    @Override
    public void update()
    {
        if(System.currentTimeMillis() > nextUpdate)
        {
            emitting = ! emitting;
            nextUpdate = System.currentTimeMillis() + TIME_TO_SLEEP;
        }
    }



    @Override
    public boolean isEmitting()
    {
        return isPowered && emitting;
    }
}
