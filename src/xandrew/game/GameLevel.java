

package xandrew.game;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.media.opengl.GL;
import javax.swing.JOptionPane;
import scene.GLRenderable;
import scene.Node;
import scene.RenderableNode;
import scene.shapes.GLSquare;
import xandrew.game.light.ExitPortal;
import xandrew.game.light.LightBeam;

/**
 *
 * @author Andrew
 */
public abstract class GameLevel extends Node
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

     /** The player's position */
     private Node player;


    /**
     * Creates a new level with an id
     * @param levelID
     */
    public GameLevel(int levelID)
    {
        super("Level" + levelID);

        this.levelID = levelID;

        player = new Node("Player");
        addChild(player);

        

    }

    /**
     * Interact with the level at the current position
     */
    public abstract void interact();


    private ArrayList<LightBeam> lightBeams = new ArrayList<LightBeam>();


    public void addLightBeam(LightBeam lb)
    {
        this.lightBeams.add(lb);
    }



    private ExitPortal exitPortal;

    public void setExit(ExitPortal ep)
    {
        this.exitPortal = ep;
    }


    /**
     * Projects a lightbeam to the boundary
     * @param light the light to project
     */
    public void projectLight(LightBeam light)
    {
        //reset position
        light.destX = light.xPos;
        light.destY = light.yPos;
        //set light beam source
        int length = 1; //go past the boundaries
        while( checkPosition(light.destX, light.destY, light) && !checkExitPortal(light))
        {
            light.destX = (float) (light.xPos + length * Math.cos(Math.toRadians(light.rotation)));
            light.destY = (float) (light.yPos + length * Math.sin(Math.toRadians(light.rotation)));
            length++;
        }
    }

    /**
     * Checks if a light beam is touching the exit portal
     * @param light
     * @return
     */
    public boolean checkExitPortal(LightBeam light)
    {
        if(light.destX > exitPortal.xPos - exitPortal.scale && light.destX < exitPortal.xPos + exitPortal.scale)
        {
            if(light.destY > exitPortal.yPos - exitPortal.scale && light.destY < exitPortal.yPos + exitPortal.scale)
            {
                //exitPortal.isActive = true;
                return true;
            }
        }

        return false;
    }
    
     


    public Node getPlayer()
    {
        return player;
    }

    //LightBeam lightBeam;
  

    @Override
    public void init(GL gl)
    {

        //load images
        try
        {
            //load image
            BufferedImage tmp = ImageIO.read(new File("images/level" + levelID + "_data.png"));
            //levelDisplay = ImageIO.read(new File("images/level" + levelID + "_bg.png"));

            //have to flip image for data process
            levelData = new BufferedImage(tmp.getWidth(), tmp.getHeight(),tmp.getType());

            for(int h=0; h<tmp.getHeight(); h++)
            {
                for(int w=0; w<tmp.getWidth(); w++)
                {
                    levelData.setRGB(w, tmp.getHeight() - h - 1, tmp.getRGB(w, h));
                }
            }
        }
        catch (IOException ex)
        {
            Logger.getLogger(GameLevel.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        //create a visual display
        GLSquare square = new GLSquare();
        square.setFileName("images/level" + levelID + "_bg.png");
        RenderableNode levelDisplay = new RenderableNode("LevelDisplay", square);
        levelDisplay.setScale(levelData.getWidth(), levelData.getHeight(), 1);


        //create player visual
        RenderableNode playerPic = new RenderableNode("Player Pic");
        GLSquare pp = new GLSquare();
        pp.setFileName("images/player.png");
        playerPic.setRenderTarget(pp);
        playerPic.setScale(5.0f);

        player.addChild(playerPic);

        //lightBeamNode.setTranslation(-getWidth()/2, -getHeight()/2);

        //moveToPostion(50,50);

        addChild(levelDisplay);

        

        super.init(gl);

        //init sound lib
        //init sound lib
        for (Sound s : Sound.values()) {
            soundLib.loadSound("sounds/" + s, s.toString());
        }

    }
    
    /** Audio manager  */
    private AudioManager soundLib = new AudioManager();


    public enum Sound
    {
        LightOn("lightActive.wav");

        private Sound(String f)
        {
            this.file = f;
        }
        public String toString()
        {
            return file;
        }
        private String file;
    }


    public void playSound(Sound s)
    {
        soundLib.playSound(s.toString());
    }


    public int getWidth()
    {
        return levelData.getWidth();
    }


    public int getHeight()
    {
        return levelData.getHeight();
    }


    /**
     *
     * @param pX
     * @param pY
     */
    public void moveToPostion(float pX, float pY)
    {
        if(checkPosition(pX, pY, null) )
        {
            player.setTranslation((-getWidth()/2) + pX, (-getHeight()/2) + pY);
        }
    }


    /**
     * Checks if the position x, y is valid
     * @param x
     * @param y
     * @return true if valid, false if not
     */
    public boolean checkPosition(float x, float y, LightBeam ignoreThisLight)
    {
        boolean moved = false;

        Color c = new Color(levelData.getRGB((int)x, (int)y));
        int red = c.getRed();
        int green = c.getGreen();
        int blue = c.getBlue();

        if( ! (red < 250 && green < 250 && blue < 250) )
        {
            moved = true;
        }

        //need to check that there isn't a light source in the way
        for (LightBeam lb : lightBeams)
        {
            if(lb != ignoreThisLight)
            {
                if( x > lb.xPos - lb.getScale() && x < lb.xPos + lb.getScale() )
                {
                     if( y > lb.yPos - lb.getScale() && y < lb.yPos + lb.getScale() )
                     {
                         moved = false;
                     }
                }
            }
        }

        return moved;
    }


    private static final int P_MOVE = 2;


    public float getCurrentPlayerX()
    {
        return player.getTranslation()[0] + getWidth()/2;
    }

    public float getCurrentPlayerY()
    {
        return player.getTranslation()[1] + getHeight()/2;
    }

    @Override
    public void update()
    {
        CameraController key = CameraController.DEFAULT_CONTROLLER;


        if(key.poll(KeyEvent.VK_W) || key.poll(KeyEvent.VK_UP))
        {
            moveToPostion(getCurrentPlayerX(), getCurrentPlayerY() + P_MOVE);
        }
        if(key.poll(KeyEvent.VK_S) || key.poll(KeyEvent.VK_DOWN))
        {
            moveToPostion(getCurrentPlayerX(), getCurrentPlayerY() - P_MOVE);
        }
        if(key.poll(KeyEvent.VK_A) || key.poll(KeyEvent.VK_LEFT))
        {
            moveToPostion(getCurrentPlayerX() - P_MOVE, getCurrentPlayerY());
        }
        if(key.poll(KeyEvent.VK_D) || key.poll(KeyEvent.VK_RIGHT))
        {
            moveToPostion(getCurrentPlayerX() + P_MOVE, getCurrentPlayerY());
        }

        if(key.poll(KeyEvent.VK_SPACE))
        {
            interact();
        }

        super.update();
    }


    
    public abstract void drawLightBeams(GL gl);

    @Override
    public void draw(GL gl)
    {

        gl.glTranslatef(-player.getTranslation()[0], -player.getTranslation()[1], 0);

        gl.glPushMatrix();
            super.draw(gl);
        gl.glPopMatrix();


        //draw light beams
        gl.glPushMatrix();
            gl.glScalef(2.0f, 2.0f, 1.0f);
            gl.glTranslatef(-getWidth()/2, -getHeight()/2, 0.1f);
            drawLightBeams(gl);
        gl.glPopMatrix();

        
        
    }
}
