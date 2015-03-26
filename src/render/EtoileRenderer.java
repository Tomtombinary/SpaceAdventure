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
