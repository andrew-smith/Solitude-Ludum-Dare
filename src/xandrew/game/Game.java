

package xandrew.game;

import javax.media.opengl.GL;
import scene.GLRenderable;
import scene.Node;
import scene.RenderableNode;
import scene.Scene;
import scene.ShaderNode;
import scene.shapes.GLSquare;
import xandrew.game.levels.Level_0;
import xandrew.game.levels.Level_1;
import xandrew.game.levels.Level_2;

/**
 *
 * @author Andrew
 */
public class Game implements GLRenderable
{
    
    /** Min scene to use */
    private final Scene scene;
    /** Root node */
    private final Node rootNode;

    


    /** Creates a new game */
    public Game()
    {
        this.scene = new Scene();
        this.rootNode = scene.getRootNode();

    }


    GameLevel level;

    /** Node to overlay the blackness */
    private RenderableNode maskNode;
    /** Node to overlay the lazers */
    private RenderableNode globalMaskNode;


    public void setLevel(GameLevel l)
    {
        this.level = l;
    }

    public void init(GL gl)
    {

        //level = new GameLevel(3) {};
        
        rootNode.addChild(level);


        GLSquare mask = new GLSquare();
        mask.setFileName("images/mask.png");
        maskNode = new RenderableNode("Mask");
        maskNode.setRenderTarget(mask);

        GLSquare maskG = new GLSquare();
        maskG.setFileName("images/mask.png");
        maskG.setColour(new float[]{0.0f, 0.0f, 0.0f, 0.8f});
        globalMaskNode = new RenderableNode("GlobalMask");
        globalMaskNode.setRenderTarget(maskG);
        globalMaskNode.init(gl);

        maskNode.init(gl);
        


        scene.init(gl);

        maskNode.setScale(level.getWidth(), level.getHeight(), 1);
        globalMaskNode.setScale(level.getWidth(), level.getHeight(), 1);
        //now add some more shapes on so the rest is blacked out
        GLSquare blackness = new GLSquare();
        blackness.setColour(new float[]{0.0f, 0.0f, 0.0f, 1.0f});

        //set a big scale so we can
        float bigScale = (level.getHeight() > level.getWidth()) ? level.getHeight() * 2 : level.getWidth() * 2;


        GLSquare laserBlackness = new GLSquare();
        laserBlackness.setFileName("images/gmask.png");
        laserBlackness.init(gl);
        //laserBlackness.setColour(new float[]{1.0f, 0.0f, 0.0f, 0.0f});

        createExpandedMask(maskNode, blackness, bigScale);
        createExpandedMask(globalMaskNode, laserBlackness, bigScale);
        


        /*
        maskShader = new ShaderNode("MaskShader");
        maskShader.loadShaderSource(GL.GL_VERTEX_SHADER, "shaders/mask/v.glsl");
        maskShader.loadShaderSource(GL.GL_FRAGMENT_SHADER, "shaders/mask/f.glsl");
        maskShader.init(gl);

        RenderableNode bigNode = new RenderableNode("BigMaskNode", new GLSquare());
        bigNode.setScale(10000);
        maskShader.addChild(bigNode);

        */


    }

    /**
     *
     * @return
     */
    public boolean isCompleted()
    {
        return level.isCompleted();
    }


    private void createExpandedMask(Node parent, GLRenderable target, float scale)
    {
        RenderableNode top = new RenderableNode("MaskTop", target);
        top.setTranslation(0, level.getHeight()/2);
        top.setScale(scale);
        RenderableNode bottom = new RenderableNode("MaskTop", target);
        bottom.setTranslation(0, -level.getHeight()/2);
        bottom.setScale(scale);
        RenderableNode left = new RenderableNode("MaskTop", target);
        left.setTranslation(-level.getWidth()/2, 0);
        left.setScale(scale);
        RenderableNode right = new RenderableNode("MaskTop", target);
        right.setTranslation(+level.getWidth()/2, 0);
        right.setScale(scale);

        parent.addChild(top);
        parent.addChild(bottom);
        parent.addChild(left);
        parent.addChild(right);
    }

    

    //ShaderNode maskShader;


    public void update()
    {

        scene.update();
    }


    public void draw(GL gl)
    {

        gl.glTranslatef(0.0f, 0.0f, -6.0f);
        gl.glScalef(0.016f, 0.016f, 0.016f);

        gl.glPushMatrix();
        
        scene.draw(gl);
        
        gl.glPopMatrix();

        Node player = level.getPlayer();

        

        gl.glTranslatef(0.0f, 0.0f, 6.0f);
        gl.glTranslatef(player.getNodeGlobalX(), player.getNodeGlobalY(), 0);
        maskNode.draw(gl);

        gl.glTranslatef(0.0f, 0.0f, 6f);

        //this is for the lasers that appear in the dark - so we can see them but not in their full colour
        globalMaskNode.draw(gl);
        
        //set blending for mask
        /*
        gl.glEnable(GL.GL_BLEND);
        gl.glBlendFunc(GL.GL_SRC_ALPHA, GL.GL_ONE_MINUS_SRC_ALPHA);

        maskShader.setUniformF(gl, "playerX", player.getNodeGlobalX());
        maskShader.setUniformF(gl, "playerY", player.getNodeGlobalY());
        maskShader.draw(gl);
        */



    }

    public void setLevel(int GAME_LEVEL) 
    {
        switch(GAME_LEVEL)
        {
            case 0: setLevel(new Level_0()); break;
            case 1: setLevel(new Level_1()); break;
            case 2: setLevel(new Level_2()); break;
            default: setLevel(new GameLevel(GAME_LEVEL) {});
        }
    }


}
