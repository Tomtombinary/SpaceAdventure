/*
 * Copyright 2015 thomas.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package render;

import entity.Meteorite;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.Color;

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
        grphcs.setColor(Color.red);
        grphcs.drawRect(objectToRender.getX()-objectToRender.getCenterX(), objectToRender.getY()-objectToRender.getCenterY(), objectToRender.getWidth(), objectToRender.getHeight());
        grphcs.popTransform();
        
    }
    
}
