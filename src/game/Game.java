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
package game;

// C'est Joli
import controller.Player;
import controller.Updateable;
import entity.Armor;
import entity.Block;
import entity.CollideListener;
import entity.Meteorite;
import entity.Reactor;
import entity.Spaceship;
import entity.Weapon;
import java.util.ArrayList;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.state.*;
import render.EtoileRenderer;
import render.MeteoriteRenderer;
import render.Renderable;
import render.SpaceshipRenderer;

/**
 *
 * @author thomas
 */
public class Game extends BasicGameState{
    
    private final ArrayList<Updateable>      objectsAddToUpdate = new ArrayList();
    private final ArrayList<Renderable>      objectsAddToRender = new ArrayList();
    private final ArrayList<CollideListener> addCollideListener = new ArrayList();
    
    private final ArrayList<Updateable> objectsSupToUpdate = new ArrayList();
    private final ArrayList<Renderable>  objectsSupToRender = new ArrayList();
    private final ArrayList<CollideListener> supCollideListener   = new ArrayList();
    
    private final ArrayList<Renderable>  objectsToRender    = new ArrayList();
    private final ArrayList<Updateable>  objectsToUpdate    = new ArrayList();
    private final ArrayList<CollideListener> collideListeners   = new ArrayList();
    
    private final Spaceship player = new Spaceship(Window.WIDTH/2,Window.HEIGHT/2);
    
    private final Spaceship test = new Spaceship(1000,1000);
    
    @Override
    public int getID() {
        return States.GAME.getID();
    }
    
    /**
     * Prépare l'ajout d'un listener de colision,
     * la méthode collide de l'objet sera appelé si celui ci est en 
     * collision avec un autre objet de type CollideListener
     * @param listener 
     */
    public void addCollideListener(CollideListener listener){
        this.addCollideListener.add(listener);
    }
    
    /**
     * Supprime un listener de colision, la réference de celui-ci 
     * est mis en attente dans une liste d'objet a supprimer
     * @param listener 
     */
    public void removeCollideListener(CollideListener listener){
        this.supCollideListener.add(listener);
    }
    
    /**
     * Prépare l'ajout d'un nouvel objet
     * @param u
     *  L'objet a ajouter
     * @param r 
     *  Son renderer (si il n'est pas invisible)
     */
    public void addNewObject(Updateable u,Renderable r){
        if(u!=null)
            objectsAddToUpdate.add(u);
        if(r!=null)
            objectsAddToRender.add(r);
    }
    
    /**
     * Prépare la supression d'un objet (et de son renderer)
     * @param u 
     *  l'objet a supprimer
     */
    public void removeObject(Updateable u){
        if(u!=null)
            objectsSupToUpdate.add(u);
        for(Renderable r : objectsToRender){
            if(r.getObjectToRender()==u)
                objectsSupToRender.add(r);
            if(r instanceof SpaceshipRenderer && u instanceof Block){
                ((SpaceshipRenderer)r).removeBlock((Block)u);
            }
        }
    }
    
    private void supWaitedObjects(){
        for(Updateable u : objectsSupToUpdate)
            objectsToUpdate.remove(u);
        for(Renderable r : objectsSupToRender)
            objectsToRender.remove(r);
        for(CollideListener l : supCollideListener)
            collideListeners.remove(l);
        objectsSupToUpdate.clear();
        objectsSupToRender.clear();
        supCollideListener.clear();
    }
    
    private void addWaitedObjects(){
        for(Updateable u : objectsAddToUpdate)
            objectsToUpdate.add(u);
        for(Renderable r : objectsAddToRender)
            objectsToRender.add(r);
        for(CollideListener c : addCollideListener)
            collideListeners.add(c);
        objectsAddToUpdate.clear();
        objectsAddToRender.clear();
        addCollideListener.clear();
    }
    
    @Override
    public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
        player.addBlock(new Weapon(),0,0);
        player.addBlock(new Reactor(1f,10f),1,0);
        player.initSpaceship();
        player.setCenterX(Window.WIDTH/2);
        player.setCenterY(Window.HEIGHT/2);
        
        for(int i=-2;i<2;i++){
            for(int j=-2;j<2;j++){
                test.addBlock(new Armor(), i, j);
            }
        }
        test.initSpaceship();
        test.setCenterX(Window.WIDTH/2);
        test.setCenterY(Window.HEIGHT/2);
        
        for(int i=0;i<100;i++){
            objectsToRender.add(new EtoileRenderer(
                    (float)Math.random()*Window.WIDTH*4-Window.WIDTH*2,
                    (float)Math.random()*Window.HEIGHT*4-Window.HEIGHT*2,
                   (int)(Math.random()*2))
            );
        }
        
        /*for(int i=0;i<5;i++){
            Meteorite meteor = new Meteorite(
                    (float)(Math.random())*Window.WIDTH,
                    (float)(Math.random())*Window.HEIGHT,
                    Meteorite.BIG
                );
            MeteoriteRenderer meteorRender = new MeteoriteRenderer(meteor);
            while(meteor.intersects(player)){
                meteor = new Meteorite(
                    (float)(Math.random())*Window.WIDTH,
                    (float)(Math.random())*Window.HEIGHT,
                    Meteorite.BIG
                );
                meteorRender = new MeteoriteRenderer(meteor);
            }
            objectsToUpdate.add(meteor);
            objectsToRender.add(meteorRender);
            collideListeners.add(meteor);
        }*/
        
        objectsToRender.add(new SpaceshipRenderer(player));
        objectsToRender.add(new SpaceshipRenderer(test));
        objectsToUpdate.add(new Player(player));
        objectsToUpdate.add(player);
        objectsToUpdate.add(test);
        collideListeners.add(player);
        collideListeners.add(test);
    }

    @Override
    public void render(GameContainer gc, StateBasedGame sbg, Graphics grphcs) throws SlickException {
        grphcs.translate(Window.WIDTH/2-player.getCenterX(),Window.HEIGHT/2-player.getCenterY());
        for(Renderable r : objectsToRender)
            r.render(gc, sbg, grphcs);
    }

    @Override
    public void update(GameContainer gc, StateBasedGame sbg, int i) throws SlickException {
        for(Updateable u : objectsToUpdate)
            u.update(gc, sbg, i);
        for(CollideListener c1 : collideListeners){
            for(CollideListener c2 : collideListeners){
                if(c1!=c2 && c1.intersects((Shape)c2))
                    c1.Collide(this, c2);
            }
        }
        addWaitedObjects();
        supWaitedObjects();
    }
    
}
