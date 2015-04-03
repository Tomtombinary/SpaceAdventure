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
import java.awt.Font;
import org.newdawn.slick.Color;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.state.StateBasedGame;

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
    protected Image current;
    
    @Override
    public Object getObjectToRender(){
        return objectToRender;
    }
    
    /**
     * Créer un object qui va permettre de dessiner le block en question
     * charge les ressources lors de la première instance
     * @param block 
     *  le block
     */
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
    
    /**
     * Méthode de rendu
     * @param gc
     * @param sbg
     * @param grphcs
     *  objet qui va dessiner le block
     * @throws SlickException 
     */
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
    
    private static final void loadRessources() throws SlickException{
        BlockRenderer.armor  = new Image("res/armor.png");
        BlockRenderer.weapon = new Image("res/weapon.png"); 
        BlockRenderer.reactor= new Image("res/reactor_disabled.png");
    }
}
