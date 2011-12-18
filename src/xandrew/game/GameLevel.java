

package xandrew.game;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
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
import xandrew.game.light.Obstacle;

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

     /** All the beams of light */
     private ArrayList<LightBeam> lightBeams = new ArrayList<LightBeam>();
     /** All the obstacles */
     private ArrayList<Obstacle> obstacles = new ArrayList<Obstacle>();

     /** The one and only exit portal */
     private ExitPortal exitPortal;



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
    public void interact()
    {
        CameraController key = CameraController.DEFAULT_CONTROLLER;

        float playerX = getCurrentPlayerX();
        float playerY = getCurrentPlayerY();

        //check if the player is by any light sources
        for (LightBeam lb : lightBeams)
        {
            //and ensure it is on
           if(lb.isEmitting())
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
    }


    public void checkLightBeams()
    {
        //get all the source lights and ensure they are powered
        //source light is always powered
        for (LightBeam lb : lightBeams) {
            if(lb.isSourceLight)
                lb.isPowered = true;
        }

        //save the states of all the lights (if one changes to on then we will play a sound */
        Map<LightBeam, Boolean> lightStates = new HashMap<LightBeam, Boolean>();

        for (LightBeam lb : lightBeams) {
            lightStates.put(lb, lb.isEmitting());
        }

        //power off all the non-source lights lightbeams to begin with
        for (LightBeam lb : lightBeams) {
            if(!lb.isSourceLight)
                lb.isPowered = false;
        }




        //always project source light.
        for (LightBeam lb : lightBeams) {
            if(lb.isSourceLight)
                projectLight(lb);
        }
        
        //sourceLight.rotation ++;
        //find out if any of the other light beams are powered
        for(LightBeam lSource :lightBeams)
        {
            for(LightBeam lDest : lightBeams)
            {
                final float x = lSource.destX;
                final float y = lSource.destY;
                if(lSource != lDest && lSource.isEmitting())
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
        boolean originalPortalState = exitPortal.isActive;
        exitPortal.isActive = false;
        for(LightBeam lb :lightBeams)
        {
            if(checkExitPortal(lb))
                exitPortal.isActive = true;
        }

        if(!originalPortalState && exitPortal.isActive)
        {
            playSound(Sound.PortalActivated);
        }


        //check if the state of any changed
        for (LightBeam lb : lightBeams) {
            if(lightStates.get(lb) == false && lb.isEmitting()) //then it has changed
            {
                playSound(Sound.LightOn);
            }
        }
    }


    
    public void addObstacle(Obstacle ob)
    {
        this.obstacles.add(ob);
    }
    

    public void addLightBeam(LightBeam lb)
    {
        this.lightBeams.add(lb);
    }



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
        while( checkPosition(light.destX, light.destY, light, true) && !checkExitPortal(light))
        {
            light.destX = (float) (light.xPos + length * Math.cos(Math.toRadians(light.rotation)));
            light.destY = (float) (light.yPos + length * Math.sin(Math.toRadians(light.rotation)));
            length++;
        }
    }

    /**
     * Draws all the light beams and exit
     * @param gl
     */
    public void drawLightBeams(GL gl)
    {
        //sourceLight.draw(gl);

        gl.glPushMatrix();
        gl.glTranslatef(0.0f, 0.0f, 6f);
        for(LightBeam lb :lightBeams)
            lb.draw(gl);
        gl.glPopMatrix();

        exitPortal.draw(gl);
    }

    /**
     * Checks if a light beam is touching the exit portal
     * @param light
     * @return
     */
    public boolean checkExitPortal(LightBeam light)
    {
        if(light.isEmitting())
        {
            if(light.destX > exitPortal.xPos - exitPortal.scale && light.destX < exitPortal.xPos + exitPortal.scale)
            {
                if(light.destY > exitPortal.yPos - exitPortal.scale && light.destY < exitPortal.yPos + exitPortal.scale)
                {
                    //exitPortal.isActive = true;
                    return true;
                }
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



        for (LightBeam lightBeam : lightBeams) {
            lightBeam.init(gl);
        }

    }
    
    /** Audio manager  */
    private AudioManager soundLib = new AudioManager();


    public enum Sound
    {
        LightOn("lightActive.wav"), PortalActivated("portalActive.wav");

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
        if(checkPosition(pX, pY, null, false) )
        {
            player.setTranslation((-getWidth()/2) + pX, (-getHeight()/2) + pY);
        }
    }


    /**
     * Checks if the position x, y is valid
     * @param x
     * @param y
     * @param ignoreThisLight a light to ignore (or null)
     * @param isLaser true if this is a laser calling the method - as some obstacles could be unaffected by laster
     * @return true if valid, false if not
     */
    public boolean checkPosition(float x, float y, LightBeam ignoreThisLight, boolean isLaser)
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

        //need to check any obstacles in the way
        for (Obstacle ob : obstacles) {
            if(ob.isActive())
            {
                //if it;s NOT the laser OR it is the laser and the object affects it
                if(!isLaser || (ob.affectsLaser() && isLaser) )
                {
                    if(ob.contains(x, y))
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


        if(key.poll(KeyEvent.VK_W))// || key.poll(KeyEvent.VK_UP))
        {
            moveToPostion(getCurrentPlayerX(), getCurrentPlayerY() + P_MOVE);
        }
        if(key.poll(KeyEvent.VK_S))// || key.poll(KeyEvent.VK_DOWN))
        {
            moveToPostion(getCurrentPlayerX(), getCurrentPlayerY() - P_MOVE);
        }
        if(key.poll(KeyEvent.VK_A))// || key.poll(KeyEvent.VK_LEFT))
        {
            moveToPostion(getCurrentPlayerX() - P_MOVE, getCurrentPlayerY());
        }
        if(key.poll(KeyEvent.VK_D))// || key.poll(KeyEvent.VK_RIGHT))
        {
            moveToPostion(getCurrentPlayerX() + P_MOVE, getCurrentPlayerY());
        }

        interact();

        checkLightBeams();

        for (LightBeam lightBeam : lightBeams) {
            lightBeam.update();
        }

        for (Obstacle obstacle : obstacles) {
            obstacle.update();
        }
        checkLightBeams();

        super.update();
    }


    

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
            for (Obstacle obstacle : obstacles) {
                obstacle.draw(gl);
            }
            drawLightBeams(gl);
        gl.glPopMatrix();

        
        
    }
}
