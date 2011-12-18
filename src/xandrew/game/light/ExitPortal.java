/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package xandrew.game.light;

import javax.media.opengl.GL;
import scene.GLRenderable;
import scene.shapes.GLSquare;

/**
 *
 * @author Andrew
 */
public class ExitPortal implements GLRenderable
{

    public final float xPos, yPos;

    public final float scale;

    public boolean isActive;

    public ExitPortal(float x, float y)
    {
        this.xPos = x;
        this.yPos = y;

        scale = 20;

        isActive = false;
    }

    GLSquare square, squareActive;

    public void init(GL gl)
    {
        square = new GLSquare();
        square.setFileName("images/portal_dead.png");
        square.init(gl);

        squareActive = new GLSquare();
        squareActive.setFileName("images/portal_active.png");
        squareActive.init(gl);
    }

    private float rotation = 0;
    public void update()
    {
    }

    public void draw(GL gl)
    {
        gl.glPushMatrix();
            gl.glTranslatef(xPos, yPos, 0.0f);
            gl.glScalef(scale, scale, 0f);
            if(isActive)
            {
                gl.glRotated(rotation+=2.5f, 0.0, 0.0, 1.0);
                squareActive.draw(gl);
            }
            else square.draw(gl);
        gl.glPopMatrix();
    }


}
