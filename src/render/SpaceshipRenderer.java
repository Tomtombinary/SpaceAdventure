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
import org.newdawn.slick.Color;
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
    
    public void removeBlock(Block b){
        for(int i=0;i<blockRenderer.size();i++){
            if(blockRenderer.get(i).objectToRender==b){
                blockRenderer.remove(i);
                break;
            }
        }
    }
    
    public SpaceshipRenderer(Spaceship vaisseau){
        this.vaisseau = vaisseau;
        Set keys = vaisseau.getBlocks().keySet();
        Iterator it = keys.iterator();
        //Boucle pour parcourir les blocks du vaisseau
        while(it.hasNext()){
            Point key = (Point)it.next();
            Block block = vaisseau.getBlocks().get(key);
            //Associe a chaque block le blockRenderer correspondant
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
        grphcs.setColor(Color.red);
        grphcs.pushTransform();
        grphcs.rotate(vaisseau.getCenterX(),vaisseau.getCenterY(),vaisseau.getAngle());
        //grphcs.setColor(Color.white);
        //grphcs.fillRect(vaisseau.getCenterX()-5,vaisseau.getCenterY()-5,10,10);
        //grphcs.setColor(Color.red);
        //grphcs.drawRect(vaisseau.getX(), vaisseau.getY(), vaisseau.getWidth(), vaisseau.getHeight());
        grphcs.popTransform();
    }
}
