
package xandrew.alone;

import com.sun.opengl.util.GLUT;
import java.util.Random;
import javax.media.opengl.GL;
import scene.Node;

/**
 *
 * @author Andrew
 */
public class MonsterHandler extends Node
{

    
    public MonsterHandler()
    {
        super("Monster Handler");
    }






    @Override
    public void init(GL gl)
    {
        for(int i=0; i<100; i++)
        {
            addChild(new Monster());
        }

        super.init(gl);
    }





    @Override
    public void update()
    {
        super.update();
    }




    /** GLut used to draw cubes */
    private GLUT glut = new GLUT();

    /** RAndom gen to use */
    private final Random rand = new Random();


    private class Monster extends Node
    {
        public Monster()
        {
            super("Monster");
            this.setTranslation(rand.nextFloat() * 200 - 100, 0, rand.nextFloat() * 200 - 100);
        }


        @Override
        public void update()
        {
            setTranslation(getTranslation()[0],(float) ((float) Math.PI/2 + Math.sin(yVal += 0.02)), getTranslation()[2]);
            setRotation(rVal += 0.4, 0.5f, 1.0f, 0.2f);

            super.update();
        }


        private float yVal = (float) (rand.nextFloat() * Math.PI);
        private float rVal = rand.nextInt(360);

        @Override
        public void draw(GL gl)
        {
            preDraw(gl);
            glut.glutSolidCube(0.5f);
            super.draw(gl);

            postDraw(gl);
        }
    }
}
