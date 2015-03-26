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

import controller.Player;
import controller.Updateable;
import entity.Armor;
import entity.CollideListener;
import entity.Meteorite;
import entity.Reactor;
import entity.Spaceship;
import entity.Weapon;
import java.io.IOException;
import java.util.ArrayList;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.openal.Audio;
import org.newdawn.slick.openal.AudioLoader;
import org.newdawn.slick.state.*;
import org.newdawn.slick.util.ResourceLoader;
import render.EtoileRenderer;
import render.MeteoriteRenderer;
import render.Renderable;
import render.SpaceshipRenderer;

/**
 *
 * @author thomas
 */
public class Game extends BasicGameState{
    
    //private static Audio Theme;
    
    private ArrayList<Updateable>  objectsAddToUpdate = new ArrayList<Updateable>();
    private ArrayList<Renderable>  objectsAddToRender = new ArrayList<Renderable>();
    
    private ArrayList<Updateable> objectsSupToUpdate = new ArrayList<Updateable>();
    private ArrayList<Renderable>  objectsSupToRender = new ArrayList<Renderable>();
    private ArrayList<CollideListener> supcollideListeners   = new ArrayList<CollideListener>();
    
    private ArrayList<Renderable>  objectsToRender    = new ArrayList<Renderable>();
    private ArrayList<Updateable>  objectsToUpdate    = new ArrayList<Updateable>();
    private ArrayList<CollideListener> collideListeners   = new ArrayList<CollideListener>();
    
    private Spaceship vaisseau = new Spaceship(400,300);
    
    @Override
    public int getID() {
        return States.GAME.getID();
    }
    
    public void addCollideListener(CollideListener listener){
        this.collideListeners.add(listener);
    }
    
    public void removeCollideListener(CollideListener listener){
        this.supcollideListeners.add(listener);
    }
    
    public void addNewObject(Updateable u,Renderable r){
        if(u!=null)
            objectsAddToUpdate.add(u);
        if(r!=null)
            objectsAddToRender.add(r);
    }
    
    public void removeObject(Updateable u){
        if(u!=null)
            objectsSupToUpdate.add(u);
        for(Renderable r : objectsToRender){
            if(r.getObjectToRender()==u)
                objectsSupToRender.add(r);
        }
    }
    
    private void supWaitedObjects(){
        for(Updateable u : objectsSupToUpdate)
            objectsToUpdate.remove(u);
        for(Renderable r : objectsSupToRender)
            objectsToRender.remove(r);
        for(CollideListener l : supcollideListeners)
            collideListeners.remove(l);
    }
    
    private void addWaitedObjects(){
        for(Updateable u : objectsAddToUpdate)
            objectsToUpdate.add(u);
        for(Renderable r : objectsAddToRender)
            objectsToRender.add(r);
        objectsAddToUpdate.clear();
        objectsAddToRender.clear();
    }
    
    @Override
    public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
        /*if(Theme==null){
            try {
                Theme = AudioLoader.getAudio("WAV",ResourceLoader.getResourceAsStream("res/Skyrim.wav"));
            } catch (IOException ex) {
                System.err.println(ex.getMessage());
            }
        }*/
        vaisseau.addBlock(new Armor(),0,0);
        vaisseau.addBlock(new Armor(),0,-1);
        vaisseau.addBlock(new Armor(),0,1);
        vaisseau.addBlock(new Weapon(),-1,1);
        vaisseau.addBlock(new Weapon(),-1,-1);
        vaisseau.addBlock(new Armor(),1,0);
        vaisseau.addBlock(new Reactor(1f,10f),2,0);
        vaisseau.addBlock(new Armor(),1,-1);
        vaisseau.addBlock(new Armor(),1,1);
        
        for(int i=0;i<100;i++){
            objectsToRender.add(new EtoileRenderer(
                    (float)Math.random()*Window.WIDTH*4-Window.WIDTH*2,
                    (float)Math.random()*Window.HEIGHT*4-Window.HEIGHT*2,
                   (int)(Math.random()*2))
            );
        }
        
        for(int i=0;i<10;i++){
            Meteorite meteor = new Meteorite(
                    (float)(Math.random())*Window.WIDTH,
                    (float)(Math.random())*Window.HEIGHT,
                    Meteorite.BIG
            );
            MeteoriteRenderer meteorRender = new MeteoriteRenderer(meteor);
            objectsToUpdate.add(meteor);
            objectsToRender.add(meteorRender);
            collideListeners.add(meteor);
        }
        
        objectsToRender.add(new SpaceshipRenderer(vaisseau));
        objectsToUpdate.add(new Player(vaisseau));
        objectsToUpdate.add(vaisseau);
        //Theme.playAsMusic(1f, 1f, true);
    }

    @Override
    public void render(GameContainer gc, StateBasedGame sbg, Graphics grphcs) throws SlickException {
        grphcs.translate(Window.WIDTH/2-vaisseau.getCenterX(),Window.HEIGHT/2-vaisseau.getCenterY());
        for(Renderable r : objectsToRender)
            r.render(gc, sbg, grphcs);
    }

    @Override
    public void update(GameContainer gc, StateBasedGame sbg, int i) throws SlickException {
        for(Updateable u : objectsToUpdate)
            u.update(gc, sbg, i);
        for(int k=0;k<collideListeners.size();k++){
            CollideListener c1 = collideListeners.get(k);
            for(int l=0;l<collideListeners.size();l++){
                CollideListener c2 = collideListeners.get(l);
                if(c1!=c2 && c1.intersects((Shape)c2))
                    c1.Collide(this, c2);
            }
        }
        addWaitedObjects();
        supWaitedObjects();
    }
    
}
