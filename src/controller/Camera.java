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
package controller;

import entity.CenterListener;
import entity.Spaceship;
import game.Window;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;

/**
 *
 * @author thomas
 */
public class Camera implements Updateable,CenterListener{

    private float decalX;
    private float decalY;
    
    private float diffx;
    private float diffy;
    private float tx;
    private float ty;
    private Vector2f vitesse;
    
    public Camera(Spaceship player){
        this.tx = Window.WIDTH/2-player.getCenterX();
        this.ty = Window.HEIGHT/2-player.getCenterY();
        this.vitesse = player.getVitesse();
    }
    
    @Override
    public void update(GameContainer gc, StateBasedGame sbg, int i) throws SlickException {
        this.tx-=vitesse.x-decalX*0.01;
        this.ty-=vitesse.y-decalY*0.01;
        decalX*=0.99;
        decalY*=0.99;
    }

    /**
     * @return the tx
     */
    public float getTx() {
        return tx;
    }

    /**
     * @return the ty
     */
    public float getTy() {
        return ty;
    }

    @Override
    public void centerChange(float decalX, float decalY) {
        this.decalX += decalX;
        this.decalY += decalY;
    }
    
}
