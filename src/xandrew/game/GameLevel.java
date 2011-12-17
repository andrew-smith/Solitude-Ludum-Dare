

package xandrew.game;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.media.opengl.GL;
import scene.GLRenderable;
import scene.Node;
import scene.RenderableNode;
import scene.shapes.GLSquare;

/**
 *
 * @author Andrew
 */
public class GameLevel extends Node
{

    /**
     * levels/levelX_data.png
     *      This is the data file - defines the bounds
     * levels/levelX_bg.png
     *      This is the display what is shown on the screen
     *
     */
     private BufferedImage levelData;//, levelDisplay;

     /** The level id */
     private int levelID;


    /**
     * Creates a new level with an id
     * @param levelID
     */
    public GameLevel(int levelID)
    {
        super("Level" + levelID);

        this.levelID = levelID;
    }


    @Override
    public void init(GL gl)
    {
        //load images
        try
        {
            //load image
            levelData = ImageIO.read(new File("images/level" + levelID + "_data.png"));
            //levelDisplay = ImageIO.read(new File("images/level" + levelID + "_bg.png"));
        }
        catch (IOException ex)
        {
            Logger.getLogger(GameLevel.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        //find where to set the player
        for(int h=0; h<levelData.getHeight(); h++)
        {
            for (int w = 0; w < levelData.getWidth(); w++)
            {
                int rgb = levelData.getRGB(w, h);

            }
        }


        //create a visual display
        GLSquare square = new GLSquare();
        square.setFileName("images/level" + levelID + "_bg.png");
        RenderableNode rn = new RenderableNode("LevelDisplay", square);
        rn.setScale(levelData.getWidth()/50, levelData.getHeight()/50, 1);


        addChild(rn);

        super.init(gl);

    }


    @Override
    public void update()
    {
        super.update();
    }


    @Override
    public void draw(GL gl)
    {
        super.draw(gl);
    }
}
