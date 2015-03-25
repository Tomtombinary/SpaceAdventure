/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package render;

import entity.*;
import java.util.logging.Level;
import java.util.logging.Logger;
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
