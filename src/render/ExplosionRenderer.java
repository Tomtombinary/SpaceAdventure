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

import controller.Updateable;
import game.Engine;
import game.Game;
import game.States;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.newdawn.slick.Animation;
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
public class ExplosionRenderer implements Renderable,Updateable{
    
    private static final int SPRITE_HEIGHT=64,SPRITE_WIDTH=64;
    private static SpriteSheet explosion_spritesheet;
    private Animation anim;
    private float x,y;
    private float scaleFactor;
    
    public ExplosionRenderer(float Centerx,float Centery,float scaleFactor){
        try {
            if(explosion_spritesheet==null)
                explosion_spritesheet = new SpriteSheet(new Image("res/explosion_spritesheet.png"),SPRITE_WIDTH,SPRITE_HEIGHT);
        } catch (SlickException ex) {
            ex.printStackTrace();
        }
        this.x = Centerx-(SPRITE_WIDTH/2)*scaleFactor;
        this.y = Centery-(SPRITE_HEIGHT/2)*scaleFactor;
        this.scaleFactor = scaleFactor;
        this.anim = new Animation();
        for(int i=0;i<5;i++){
            for(int j=0;j<5;j++){
                anim.addFrame(explosion_spritesheet.getSprite(j,i),50);
            }
        }
        this.anim.stopAt(24);
    }

    @Override
    public Object getObjectToRender() {
        return this;
    }

    @Override
    public void render(GameContainer gc, StateBasedGame sbg, Graphics grphcs) throws SlickException {
        grphcs.pushTransform();
        grphcs.translate(x, y);
        grphcs.scale(scaleFactor,scaleFactor);
        grphcs.drawAnimation(anim,0,0);
        grphcs.popTransform();
    }

    @Override
    public void update(GameContainer gc, StateBasedGame sbg, int i) throws SlickException {
        if(anim.isStopped()){
            ((Game)(((Engine)sbg).getState(States.GAME.getID()))).removeObject(this);
        }
    }
}
