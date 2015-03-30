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
            appgc.setTargetFrameRate(60);
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
