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

import entity.*;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

/**
 *
 * @author thomas
 */
public abstract class BlockRenderer implements Renderable{
    
    private static boolean load = false;
    protected static Image reactor;
    protected static Image armor;
    protected static Image weapon;
    
    protected Block objectToRender;
    
    public Object getObjectToRender(){
        return objectToRender;
    }
    public BlockRenderer(Block block){
        if(!load){
            try {
                loadRessources();
            } catch (SlickException ex) {
                System.err.println(ex.getMessage());
            }
        }
        this.objectToRender = block;
    }
    
    private static final void loadRessources() throws SlickException{
        BlockRenderer.armor  = new Image("res/armor.png");
        BlockRenderer.weapon = new Image("res/weapon.png"); 
        BlockRenderer.reactor= new Image("res/reactor_disabled.png");
    }
}
