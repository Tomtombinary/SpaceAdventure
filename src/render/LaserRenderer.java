/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package render;

import entity.Laser;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

/**
 *
 * @author thomas
 */
public class LaserRenderer implements Renderable{

    private static Image laser;
    private Laser objectToRender;
    
    @Override
    public Object getObjectToRender(){
        return objectToRender;
    }
    
    public LaserRenderer(Laser objectToRender){
        this.objectToRender = objectToRender;
    }
    
    @Override
    public void render(GameContainer gc, StateBasedGame sbg, Graphics grphcs) throws SlickException {
        if(!objectToRender.isDetruit()){
            grphcs.pushTransform();
            grphcs.translate(objectToRender.getCenterX(),objectToRender.getCenterY());
            grphcs.rotate(0,0,objectToRender.getAngle());
            /*grphcs.drawImage(laser,
                    objectToRender.getX()-objectToRender.getCenterX(),
                    objectToRender.getY()-objectToRender.getCenterY()
            );*/
            grphcs.setColor(Color.green);
            grphcs.fillRect(
                    objectToRender.getX()-objectToRender.getCenterX(),
                    objectToRender.getY()-objectToRender.getCenterY(),
                    objectToRender.getWidth(),
                    objectToRender.getHeight()
            );
            grphcs.popTransform();
        }
    }
    
    public static void LoadRessources() throws SlickException{
        laser = new Image("res/laser.png");
    }
    
}
