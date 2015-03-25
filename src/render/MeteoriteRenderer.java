/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package render;

import entity.Meteorite;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.state.StateBasedGame;

/**
 *
 * @author thomas
 */
public class MeteoriteRenderer implements Renderable{

    private static Image spriteSheet;
    private static SpriteSheet meteorSpriteBig;
    private static SpriteSheet meteorSpriteMedium;
    private static SpriteSheet meteorSpriteSmall;
    
    private Image current;
    private Meteorite objectToRender;
    
    @Override
    public Object getObjectToRender(){
        return objectToRender;
    }
    
    public MeteoriteRenderer(Meteorite meteor){
        try {
            if(spriteSheet==null)
                spriteSheet = new Image("res/spriteMeteorite.png");
            if(meteorSpriteBig==null)
                meteorSpriteBig = new SpriteSheet(spriteSheet.getScaledCopy(1f),Meteorite.BIG_SIZE,Meteorite.BIG_SIZE);
            if(meteorSpriteMedium==null)
                meteorSpriteMedium = new SpriteSheet(spriteSheet.getScaledCopy(0.5f),Meteorite.MEDIUM_SIZE,Meteorite.MEDIUM_SIZE);
            if(meteorSpriteSmall==null)
                meteorSpriteSmall = new SpriteSheet(spriteSheet.getScaledCopy(0.25f),Meteorite.SMALL_SIZE,Meteorite.SMALL_SIZE);
        } catch (SlickException ex) {
            System.err.println(ex.getMessage());
        }
        switch(meteor.getType()){
            case Meteorite.SMALL:
                this.current = meteorSpriteSmall.getSprite((int)(Math.random()*2),(int)(Math.random()*2));
            break;
            case Meteorite.MEDIUM:
                this.current = meteorSpriteMedium.getSprite((int)(Math.random()*2),(int)(Math.random()*2));
            break;
            case Meteorite.BIG:
                this.current = meteorSpriteBig.getSprite((int)(Math.random()*2),(int)(Math.random()*2));
            break;
        }
        this.objectToRender = meteor;
    }
    @Override
    public void render(GameContainer gc, StateBasedGame sbg, Graphics grphcs) throws SlickException {
        grphcs.pushTransform();
        grphcs.translate(objectToRender.getCenterX(),objectToRender.getCenterY());
        grphcs.rotate(0,0,objectToRender.getAngle());
        grphcs.drawImage(current,
                objectToRender.getX()-objectToRender.getCenterX(),
                objectToRender.getY()-objectToRender.getCenterY()
        );
        grphcs.popTransform();
    }
    
}
