/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package render;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

/**
 *
 * @author thomas
 */
public class EtoileRenderer implements Renderable{

    private static boolean load = false;
    private static Image normal,small,big;
    public static final int NORMAL=0,SMALL=1,BIG=2;
    private float x,y;
    private Image current;
    
    @Override
    public Object getObjectToRender(){
        return null;
    }
    
    public EtoileRenderer(float x,float y,int type){
        if(!load){
            try {
                loadRessources();
            } catch (SlickException ex) {
                System.err.println(ex.getMessage());
            }
        }
        this.x = x;
        this.y = y;
        switch(type){
            case NORMAL:
                current = normal;
            break;
            case SMALL:
                current = small;
            break;
            case BIG:
                current = big;
            break;
        }
    }
    
    private static void loadRessources() throws SlickException{
        normal = new Image("res/etoile-bleue.png");
        small  = new Image("res/etoile-bleue.png").getScaledCopy(0.5f);
        big    = new Image("res/etoile-bleue.png").getScaledCopy(1.5f);
    }
    
    @Override
    public void render(GameContainer gc, StateBasedGame sbg, Graphics grphcs) throws SlickException {
        grphcs.pushTransform();
        grphcs.drawImage(current, x, y);
        grphcs.popTransform();
    }
    
}
