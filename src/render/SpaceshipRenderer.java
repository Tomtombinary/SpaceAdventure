/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package render;

import entity.Armor;
import entity.Block;
import entity.Reactor;
import entity.Spaceship;
import entity.Weapon;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Point;
import org.newdawn.slick.state.StateBasedGame;

/**
 *
 * @author thomas
 */
public class SpaceshipRenderer implements Renderable{
    
    private Spaceship vaisseau;
    private ArrayList<BlockRenderer> blockRenderer = new ArrayList<BlockRenderer>();
    
    @Override
    public Object getObjectToRender(){
        return vaisseau;
    }
    
    public SpaceshipRenderer(Spaceship vaisseau){
        this.vaisseau = vaisseau;
        Set keys = vaisseau.getBlocks().keySet();
        Iterator it = keys.iterator();
        while(it.hasNext()){
            Point key = (Point)it.next();
            Block block = vaisseau.getBlocks().get(key);
            if(block instanceof Armor)
                blockRenderer.add(new ArmorRenderer((Armor)block));
            else if(block instanceof Weapon)
                blockRenderer.add(new WeaponRenderer((Weapon)block));
            else if(block instanceof Reactor)
                blockRenderer.add(new ReactorRenderer((Reactor)block));
        }
    }
    
    @Override
    public void render(GameContainer gc, StateBasedGame sbg, Graphics grphcs) throws SlickException {
        for(BlockRenderer block : blockRenderer)
            block.render(gc, sbg, grphcs);
    }
}
