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
import java.util.ArrayList;
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
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Transform;
import render.ExplosionRenderer;

/**
 *
 * @author thomas
 */
public class Spaceship extends Rectangle implements Updateable, CollideListener{
    
    /* Liste des blocks a supprimer */
    private final ArrayList<Point> blockToRemove = new ArrayList();
    private static Audio laserEffect;
    private boolean detruit;
    private float deceleration;
    private float acc;
    private float current_speed;
    private float max_speed;
    
    private float [] centreConstruction = new float[2];
    private float angle;
    private Vector2f vitesse;
    private Map<Point,Block> blocks = new HashMap();
    private CenterListener listener;
    /**
     * Créer un vaisseau avec pour centre TEMPORAIRE centre_x,centre_y
     * @param center_x
     *  centre en X
     * @param center_y 
     *  centre en Y
     */
    public Spaceship(float center_x,float center_y){
        super(center_x,center_y,0,0);
        if(laserEffect == null){
            try {
                laserEffect = AudioLoader.getAudio("WAV",ResourceLoader.getResourceAsStream("res/laser.wav"));
            } catch (IOException ex) {
                System.err.println(ex);
            }
        }
        this.vitesse = new Vector2f(0,0);
        this.centreConstruction[0] = center_x;
        this.centreConstruction[1] = center_y;
        this.deceleration = 0.99f;
        this.current_speed = 0;
        this.acc = 0;
        this.max_speed = 0;
    }

    public void setCenterListener(CenterListener listener){
        this.listener = listener;
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
        b.setCenterX(centreConstruction[0]+colonne*Block.WIDTH);
        b.setCenterY(centreConstruction[1]+ligne*Block.HEIGHT);
        b.setSource(this);
        if(b.getX()<this.getX()){
            this.setX(b.getX());
            this.setWidth(this.getWidth()+Block.WIDTH);
        }
        if(b.getY()<this.getY()){
            this.setY(b.getY());
            this.setHeight(this.getHeight()+Block.HEIGHT);
        }
        if((this.getWidth())<Math.abs(this.getX()-(b.getX()+Block.WIDTH)))
            this.setWidth(this.getWidth()+Block.WIDTH);
        if((this.getHeight())<Math.abs(this.getY()-(b.getY()+Block.HEIGHT)))
            this.setHeight(this.getHeight()+Block.HEIGHT);
        blocks.put(new Point(colonne*Block.WIDTH,ligne*Block.HEIGHT), b);
        if(b instanceof Reactor){
            acc+=((Reactor)b).getAcc();
            max_speed+=((Reactor)b).getMax_speed();
        }
    }
    
    /**
     * Calcul le nouveau centre du vaisseau
     */
    public void initSpaceship(){
        center[0] = x+width/2;
        center[1] = y+height/2;
        float diffx = centreConstruction[0]-center[0];
        float diffy = centreConstruction[1]-center[1];
        Set keys = blocks.keySet();
        Iterator it = keys.iterator();
        while(it.hasNext()){
            Point key = (Point)it.next();
            key.setLocation(key.getX()+diffx,key.getY()+diffy);
        }
        updateBlocks();
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
            b.setCenterX((float)(getCenterX()+Math.cos(Math.toRadians(angle))*(p.getX())-Math.sin(Math.toRadians(angle))*(p.getY())));
            b.setCenterY((float)(getCenterY()+Math.sin(Math.toRadians(angle))*(p.getX())+Math.cos(Math.toRadians(angle))*(p.getY())));
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
        this.x = this.getCenterX()-width/2;
    }
    
    @Override
    public void setCenterY(float centery){
        super.setCenterY(centery);
        this.y = this.getCenterY()-height/2;
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
                        getVitesse().x+(float)((b.getCenterX()-this.getCenterX()+1)*(Math.random()*0.10f)),
                        getVitesse().y+(float)((b.getCenterY()-this.getCenterY()+1)*(Math.random()*0.10f))
                ));
                b.setAngleSpeed((float)(Math.random()*5));
                gc.addNewObject(b, null);
            }
            ExplosionRenderer r = new ExplosionRenderer(this.getCenterX(),this.getCenterY(),1);
            gc.addNewObject(r,r);
        }
    }
    
    private void hitBoxCalcul(){
            float decalX = this.getCenterX();
            float decalY = this.getCenterY();
            
            Iterator<Point> key = blocks.keySet().iterator();
            float xmin,ymin,xmax,ymax;
            float newX,newY,newWidth,newHeight,newCenterX,newCenterY;
            if(key.hasNext()){
                Point p = key.next();
                xmin = xmax = p.getX();
                ymin = ymax = p.getY();
                while(key.hasNext()){
                      p = key.next();
                      if(p.getX()<xmin)
                          xmin = p.getX();
                      if(p.getY()<ymin)
                          ymin = p.getY();
                      if(p.getX()>xmax)
                          xmax = p.getX();
                      if(p.getY()>ymax)
                          ymax = p.getY();
                }
                xmin-=Block.WIDTH/2;
                ymin-=Block.HEIGHT/2;
                xmax+=Block.WIDTH/2;
                ymax+=Block.HEIGHT/2;
                newX = xmin;
                newY = ymin;
                newWidth = xmax-xmin;
                newHeight= ymax-ymin;
                newCenterX = newX+newWidth/2;
                newCenterY = newY+newHeight/2;
                key = blocks.keySet().iterator();
                while(key.hasNext()){
                    p = key.next();
                    p.setX(p.getX()-newCenterX);
                    p.setY(p.getY()-newCenterY);
                }
                newCenterX = (float)(newCenterX*Math.cos(Math.toRadians(angle))-Math.sin(Math.toRadians(angle))*newCenterY);
                newCenterY = (float)(newCenterX*Math.sin(Math.toRadians(angle))+Math.cos(Math.toRadians(angle))*newCenterY);
                this.width= newWidth;
                this.height= newHeight;
                this.setCenterX(decalX+newCenterX);
                this.setCenterY(decalY+newCenterY);
            }
            decalX-=getCenterX();
            decalY-=getCenterY();
            if(listener!=null)this.listener.centerChange(decalX,decalY);
    }
    
    @Override
    public void update(GameContainer gc, StateBasedGame sbg, int i) throws SlickException {
        if(!detruit){
            current_speed*=deceleration;
            vitesse.x = (float)Math.cos(Math.toRadians(angle-90))*getCurrent_speed();
            vitesse.y = (float)Math.sin(Math.toRadians(angle-90))*getCurrent_speed();
            this.setCenterX(this.getCenterX()+getVitesse().getX());
            this.setCenterY(this.getCenterY()+getVitesse().getY());
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
    
    @Override
    public boolean intersects(Shape shape) {
        Shape rotateR = this.transform(Transform.createRotateTransform((float) Math.toRadians(angle), this.getCenterX(), this.getCenterY()));
        return (rotateR.intersects(shape) || rotateR.contains(shape));
    }
    
    @Override
    public void Collide(Game gc,CollideListener c){
        boolean detruitBlock = false;
        if(c instanceof Meteorite)detruitBlock = true;
        else if(c instanceof Laser){
            if(((Laser)c).getSource()!=this && !((Laser)c).isDetruit())
                detruitBlock=true;
        }
        if(detruitBlock){
            Set cles = blocks.keySet();
            Iterator it = cles.iterator();
            while (it.hasNext()){
                Point cle = (Point)it.next();
                Block block = (Block)blocks.get(cle);
                if(block.intersects((Shape)c)){
                    block.setAngleSpeed((float)(Math.random()*5));
                    block.setDeplacement(new Vector2f(
                           (float)(getCenterX()-((Shape)c).getCenterX())*0.01f,
                           (float)(getCenterY()-((Shape)c).getCenterY())*0.01f
                    ));
                    block.setSource(null);
                    gc.addNewObject(block, null);
                    gc.addCollideListener(block);
                    
                    if(block instanceof Reactor){
                        this.acc-=((Reactor)block).getAcc();
                        this.max_speed-=((Reactor)block).getMax_speed();
                    }
                    blockToRemove.add(cle);
                       if(c instanceof Laser){
                        ((Destroyable)c).detruire(gc);
                        break;
                    }
                }
            }
            for(Point key : blockToRemove)
                blocks.remove(key);
            if(blockToRemove.size()>0)hitBoxCalcul();
            blockToRemove.clear();
        }
    }
    
    @Override
    public String toString(){
        return "Rectangle : width : "+this.width+" height : "+height+" ("+center[0]+","+center[1]+")"+"("+x+","+y+")";
    }

    /**
     * @return the vitesse
     */
    public Vector2f getVitesse() {
        return vitesse;
    }
}
