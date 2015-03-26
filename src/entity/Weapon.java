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
package entity;

import game.Game;
import render.LaserRenderer;

/**
 *
 * @author thomas
 */
public class Weapon extends Block{

    private float orientation;
    
     
    public Weapon(){
        super();
        this.orientation = 0;
    }
    
    public Weapon(float orientation){
        this.orientation = orientation;
    }
    
    public Weapon(float x, float y, float angle) {
        super(x, y, angle);
    }

    /**
     * @return the orientation
     */
    public float getOrientation() {
        return orientation;
    }

    /**
     * @param orientation the orientation to set
     */
    public void setOrientation(float orientation) {
        this.orientation = orientation;
    }
    
    @Override
    public String toString(){
        return "Block d'arme "+super.toString();
    }
    
    /**
     * Méthode de tir , ajoute un laser en fonction de la position du bloc et de son
     * orientation
     * @param gc 
     *  Game container
     */
    public void tirer(Game gc){
        if(source!=null){
            float lCenterX = center[0]-(float)(Math.sin(Math.toRadians(angle))*(-HEIGHT/2));
            float lCenterY = center[1]+(float)(Math.cos(Math.toRadians(angle))*(-HEIGHT/2));
            Laser l = new Laser(lCenterX,lCenterY,angle-90,source.getCurrent_speed()+6f,source);
            LaserRenderer r = new LaserRenderer(l);
            gc.addNewObject(l, r);
            gc.addCollideListener(l);
        }
    }
}
