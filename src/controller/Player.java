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

import entity.Spaceship;
import game.Game;
import game.States;
import game.Window;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

/**
 *
 * @author thomas
 */
public class Player implements Updateable{

    private static final int TIR_WAIT=100;
    private long elapsed,last_tir;
    private Spaceship vaisseau;
    
    /**
     * Construit un controller de vaisseau par (clavier et souris)
     * @param vaisseau
     *  vaisseau controllé par le joueur
     */
    public Player(Spaceship vaisseau){
        this.vaisseau = vaisseau;
    }
    
    /**
     * Recupere l'angle en fonction de la position de souris
     * @param mx
     *  position de la souris X
     * @param my
     *  position de la souris Y
     * @return 
     *  angle
     */
    private double getAngleFromMouse(int mx,int my){
        double mouse_angle = 0;
        double diffx = mx-Window.WIDTH/2;
        double diffy = my-Window.HEIGHT/2;
        mouse_angle = Math.toDegrees(Math.atan2(diffy,diffx));
        return mouse_angle;
    }
    
    @Override
    public void update(GameContainer gc, StateBasedGame sbg, int i) throws SlickException {
        if(!vaisseau.isDetruit()){
            elapsed+=i;
            Input input = gc.getInput();
            float angle = (float)getAngleFromMouse(input.getMouseX(),input.getMouseY());
            vaisseau.setAngle(angle+90);
            if(input.isKeyDown(Input.KEY_UP))
                vaisseau.avancer(1);
            else if(input.isKeyDown(Input.KEY_DOWN))
                vaisseau.avancer(-1);
            if(input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON)&& (elapsed-last_tir)>TIR_WAIT){
                vaisseau.tirer((Game)sbg.getState(States.GAME.getID()));
                last_tir = elapsed;
            }
            if(input.isKeyPressed(Input.KEY_SPACE)){
                vaisseau.detruire((Game)sbg.getState(States.GAME.getID()));
            }
        }
    }
    
    @Override
    public String toString(){
        return "Player";
    }
}
