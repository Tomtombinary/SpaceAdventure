/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

import java.util.logging.*;
import org.newdawn.slick.*;
import org.newdawn.slick.state.*;

/**
 *
 * @author Thomas
 */
public class Engine extends StateBasedGame {

    public Engine(String title){
        super(title);
    }
    
    public static void main(String[] args) {
        try{
            AppGameContainer appgc;
            appgc = new AppGameContainer(new Engine(Window.title));
            appgc.setDisplayMode(
                    Window.WIDTH,
                    Window.HEIGHT,
                    Window.fullscreen
            );
            appgc.start();
        }catch (SlickException ex){
            Logger.getLogger(Engine.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void initStatesList(GameContainer gc) throws SlickException {
        this.addState(new Game());
    }
    
}
