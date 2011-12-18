

package xandrew.game;

import javax.media.opengl.GL;
import scene.GLRenderable;
import scene.Node;
import scene.RenderableNode;
import scene.Scene;
import scene.ShaderNode;
import scene.shapes.GLSquare;
import xandrew.game.levels.Level_0;

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

    private RenderableNode maskNode;

    public void init(GL gl)
    {

        level = new Level_0();
        
        rootNode.addChild(level);


        GLSquare mask = new GLSquare();
        mask.setFileName("images/mask.png");
        maskNode = new RenderableNode("Mask");
        maskNode.setRenderTarget(mask);


        maskNode.init(gl);
        


        scene.init(gl);

        maskNode.setScale(level.getWidth(), level.getHeight(), 1);
        //now add some more shapes on so the rest is blacked out
        GLSquare blackness = new GLSquare();
        blackness.setColour(new float[]{0.0f, 0.0f, 0.0f, 1.0f});

        //set a big scale so we can
        float bigScale = (level.getHeight() > level.getWidth()) ? level.getHeight() * 2 : level.getWidth() * 2;


        //top
        RenderableNode top = new RenderableNode("MaskTop", blackness);
        top.setTranslation(0, level.getHeight()/2);
        top.setScale(bigScale);
        RenderableNode bottom = new RenderableNode("MaskTop", blackness);
        bottom.setTranslation(0, -level.getHeight()/2);
        bottom.setScale(bigScale);
        RenderableNode left = new RenderableNode("MaskTop", blackness);
        left.setTranslation(-level.getWidth()/2, 0);
        left.setScale(bigScale);
        RenderableNode right = new RenderableNode("MaskTop", blackness);
        right.setTranslation(+level.getWidth()/2, 0);
        right.setScale(bigScale);

        maskNode.addChild(top);
        maskNode.addChild(bottom);
        maskNode.addChild(left);
        maskNode.addChild(right);


        
        maskShader = new ShaderNode("MaskShader");
        maskShader.loadShaderSource(GL.GL_VERTEX_SHADER, "shaders/mask/v.glsl");
        maskShader.loadShaderSource(GL.GL_FRAGMENT_SHADER, "shaders/mask/f.glsl");
        maskShader.init(gl);

        RenderableNode bigNode = new RenderableNode("BigMaskNode", new GLSquare());
        bigNode.setScale(10000);
        maskShader.addChild(bigNode);
        

    }


    

    ShaderNode maskShader;

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
        //maskNode.draw(gl);
        
        //set blending for mask
        
        gl.glEnable(GL.GL_BLEND);
        gl.glBlendFunc(GL.GL_SRC_ALPHA, GL.GL_ONE_MINUS_SRC_ALPHA);

        maskShader.setUniformF(gl, "playerX", player.getNodeGlobalX());
        maskShader.setUniformF(gl, "playerY", player.getNodeGlobalY());
        maskShader.draw(gl);
        



    }

}
