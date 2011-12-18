

package xandrew.game;

import java.awt.event.KeyEvent;
import javax.media.opengl.GL;
import scene.GLRenderable;
import xandrew.game.levels.Level_0;
import xandrew.game.levels.Level_1;
import xandrew.game.levels.Level_2;

/**
 * Main menu display
 * @author Andrew
 */

public class Menu implements GLRenderable
{



    private Game game;




    public void missionSelect(int index)
    {
        GameLevel level;
        switch(index)
        {
            case 1: level = new Level_1(); break;
            case 2: level = new Level_2();  break;
            default: level = new Level_0();  break;

        }

        game = new Game();
        game.setLevel(level);
        
    }


    private Game menuGame;

    public void init(GL gl) 
    {
        menuGame = new Game();
        menuGame.setLevel(new GameLevel(10101) {});
        menuGame.init(gl);
    }




    private int menuIndex = 0;
    

    public void menuDown()
    {
        //move menu down
    }


    public void menuUp()
    {
        //move menu up
    }


    private long nextKeyRead = 0;

    public void update() 
    {
        if(game != null)
        {

        }
        else
        {
            menuGame.update();
        }
    }

    public void draw(GL gl) 
    {
        if(game != null && !game.isCompleted())
        {
            game.draw(gl);
        }
        else
        {
            menuGame.draw(gl);
        }

    }

}
