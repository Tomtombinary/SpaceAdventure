/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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
