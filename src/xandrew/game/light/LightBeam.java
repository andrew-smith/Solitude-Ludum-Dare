

package xandrew.game.light;

import javax.media.opengl.GL;
import scene.GLRenderable;
import scene.shapes.GLSquare;

/**
 * Defines a light beam
 * @author Andrew
 */
public class LightBeam implements GLRenderable
{

    /** Point of origin */
    public float xPos, yPos;

    public float destX, destY;

    public float rotation;

    public boolean isPowered;

    /**
     * Creates a light beam from a central point
     * @param x
     * @param y
     */
    public LightBeam(float x, float y)
    {
        this.xPos = x;
        this.yPos = y;

        destX = x;
        destY = y;

        rotation = (float) (Math.random() * 360);

        isPowered = false;
    }

    /** The square to draw at the source */
    private GLSquare square = new GLSquare();

    public void init(GL gl) {
    }

    public void update() {
    }


    public float getScale()
    {
        return 8.0f;
    }

    public void draw(GL gl)
    {


        gl.glPushMatrix();
            gl.glTranslatef(xPos, yPos, 0.0f);
            if(isPowered)
                square.setColour(new float[] {1.0f, 1.0f, 0.0f});
            else
                square.setColour(new float[] {0.0f, 0.0f, 0.0f});
            gl.glScaled(getScale(), getScale(), 1.0); //8x8 square
            square.draw(gl);
        gl.glPopMatrix();

        if(isPowered)
        {
            gl.glLineWidth(7);
            gl.glBegin(GL.GL_LINES);
                gl.glColor3f(0.0f, 0.0f, 1.0f);
                gl.glVertex2f(xPos, yPos);
                gl.glVertex2f(destX,destY);
            gl.glEnd();
        }
    }
}
