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

import entity.Weapon;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

/**
 *
 * @author thomas
 */
public class WeaponRenderer extends BlockRenderer{

    public WeaponRenderer(Weapon block) {
        super(block);
    }

    @Override
    public void render(GameContainer gc, StateBasedGame sbg, Graphics grphcs) throws SlickException {
        grphcs.pushTransform();
        grphcs.translate(objectToRender.getCenterX(),objectToRender.getCenterY());
        grphcs.rotate(0,0,objectToRender.getAngle()+((Weapon)objectToRender).getOrientation());
        grphcs.drawImage(weapon,
                objectToRender.getX()-objectToRender.getCenterX(),
                objectToRender.getY()-objectToRender.getCenterY()
        );
        grphcs.popTransform();
    }
    
}
