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

import controller.Updateable;
import game.Game;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Point;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.openal.Audio;
import org.newdawn.slick.openal.AudioLoader;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.util.ResourceLoader;

/**
 *
 * @author thomas
 */
public class Spaceship extends Rectangle implements Updateable{
    
    private static Audio laserEffect;
    
    private boolean detruit;
    private float deceleration;
    private float acc;
    private float current_speed;
    private float max_speed;
    
    private float angle;
    private Vector2f vitesse;
    private Map<Point,Block> blocks = new HashMap();
    
    /**
     * Créer un vaisseau avec pour centre centre_x,centre_y
     * @param center_x
     *  centre en X
     * @param center_y 
     *  centre en Y
     */
    public Spaceship(float center_x,float center_y){
        super(0,0,0,0);
        if(laserEffect == null){
            try {
                laserEffect = AudioLoader.getAudio("WAV",ResourceLoader.getResourceAsStream("res/laser.wav"));
            } catch (IOException ex) {
                System.err.println(ex);
            }
        }
        this.vitesse = new Vector2f(0,0);
        this.center[0] = center_x;
        this.center[1] = center_y;
        this.deceleration = 0.99f;
        this.current_speed = 0;
        this.acc = 0;
        this.max_speed = 0;
    }

    /**
     * Ajoute un block au vaisseau, celui-ci se positionne automatiquement
     * en fonction de la ligne , et de la colonne par rapport au centre du vaisseau
     * xBlock = colonne * Largeur du block
     * yBlock = ligne   * Hauteur du block
     * @param b
     *  Le block
     * @param ligne
     *  ligne par rapport au centre du vaisseau
     * @param colonne 
     *  colonne par rapport au centre du vaisseau
     */
    public void addBlock(Block b,int ligne,int colonne){
        b.setCenterX(getCenterX()+colonne*Block.WIDTH);
        b.setCenterY(getCenterY()+ligne*Block.HEIGHT);
        b.setSource(this);
        blocks.put(new Point(colonne,ligne), b);
        if(b instanceof Reactor){
            acc+=((Reactor)b).getAcc();
            max_speed+=((Reactor)b).getMax_speed();
        }
    }
    
    /**
     * Avance/Recule le vaisseau dans la direction définie par l'angle , et a la vitesse definie par les reacteurs
     * @param direction 
     *  1 pour avancer , -1 pour reculer
     */
    public void avancer(int direction){
        if(Math.abs(getCurrent_speed())<getMax_speed()){
            if(direction>0)
                current_speed += acc;
            else if(direction<0)
                current_speed -= acc;
        }
    }
    
    private void updateBlocks(){
        Set keys = blocks.keySet();
        Iterator it = keys.iterator();
        while(it.hasNext()){
            Point p = (Point)it.next();
            Block b = (Block)blocks.get(p);
            b.setAngle(angle);
            b.setCenterX((float)(getCenterX()+Math.cos(Math.toRadians(angle))*(p.getX()*Block.WIDTH)-Math.sin(Math.toRadians(angle))*(p.getY()*Block.HEIGHT)));
            b.setCenterY((float)(getCenterY()+Math.sin(Math.toRadians(angle))*(p.getX()*Block.WIDTH)+Math.cos(Math.toRadians(angle))*(p.getY()*Block.HEIGHT)));
        }
    }
    /**
     * @return the angle
     */
    public float getAngle() {
        return angle;
    }

    @Override
    public void setCenterX(float centerx){
        super.setCenterX(centerx);
        this.x = centerx-width/2;
    }
    
    @Override
    public void setCenterY(float centery){
        super.setCenterY(centery);
        this.y = centery-height/2;
    }
    /**
     * @param angle the angle to set
     */
    public void setAngle(float angle) {
         if(angle>=0)
            this.angle = angle%360;
        else
            this.angle = 360+angle%360;
        updateBlocks();
    }

    /**
     * @return the blocks
     */
    public Map<Point,Block> getBlocks() {
        return blocks;
    }

    /**
     * Méthode pour tirer
     * @param gc 
     *  Game container
     */
    public void tirer(Game gc){
        laserEffect.playAsSoundEffect(1f,0.5f,false);
        Set keys = blocks.keySet();
        Iterator it = keys.iterator();
        while(it.hasNext()){
            Point p = (Point)it.next();
            Block b = (Block)blocks.get(p);
            if(b instanceof Weapon)
                ((Weapon)b).tirer(gc);
        }
    }

    /**
     * Méthode destruction du vaisseau
     * @param gc 
     */
    public void detruire(Game gc){
        if(!detruit){
            detruit = true;
            Set keys = blocks.keySet();
            Iterator it = keys.iterator();
            while(it.hasNext()){
                Point p = (Point)it.next();
                Block b = (Block)blocks.get(p);
                b.setSource(null);
                b.setDeplacement(new Vector2f(
                        vitesse.x+(float)((b.getCenterX()-this.getCenterX()+1)*(Math.random()*0.10f)),
                        vitesse.y+(float)((b.getCenterY()-this.getCenterY()+1)*(Math.random()*0.10f))
                ));
                b.setAngleSpeed((float)(Math.random()*5));
                gc.addNewObject(b, null);
            }
        }
    }
    
    @Override
    public void update(GameContainer gc, StateBasedGame sbg, int i) throws SlickException {
        if(!detruit){
            current_speed*=deceleration;
            vitesse.x = (float)Math.cos(Math.toRadians(angle-90))*getCurrent_speed();
            vitesse.y = (float)Math.sin(Math.toRadians(angle-90))*getCurrent_speed();
            this.setCenterX(this.getCenterX()+vitesse.getX());
            this.setCenterY(this.getCenterY()+vitesse.getY());
            updateBlocks();
        }
    }

    /**
     * @return the current_speed
     */
    public float getCurrent_speed() {
        return current_speed;
    }

    /**
     * @return the detruit
     */
    public boolean isDetruit() {
        return detruit;
    }

    /**
     * @return the max_speed
     */
    public float getMax_speed() {
        return max_speed;
    }
}
