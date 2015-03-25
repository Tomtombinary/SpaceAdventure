/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import controller.Updateable;
import game.Game;
import game.States;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Transform;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;

/**
 *
 * @author thomas
 */
public class Laser extends Rectangle implements Updateable,CollideListener{

    private static final float WIDTH=25,HEIGHT=5;
    private static final float MAX_DISTANCE = 1000;
    private boolean detruit;
    private Spaceship source;
    private float angle;
    private Vector2f vitesse;
    
    public Laser(float x, float y, float angle,float speed,Spaceship source) {
        super(x-WIDTH/2,y-HEIGHT/2, WIDTH,HEIGHT);
        this.angle = angle;
        this.vitesse = new Vector2f(
                (float)(Math.cos(Math.toRadians(angle))*speed),
                (float)(Math.sin(Math.toRadians(angle))*speed)
        );
        this.source = source;
    }

    
    /**
     * @return the angle
     */
    public float getAngle() {
        return angle;
    }

    /**
     * @return the vitesse
     */
    public Vector2f getVitesse() {
        return vitesse;
    }

    /**
     * @param vitesse the vitesse to set
     */
    public void setVitesse(Vector2f vitesse) {
        this.vitesse = vitesse;
    }

    @Override
    public void setCenterX(float centerX){
        super.setCenterX(centerX);
        this.x = centerX-WIDTH/2;
    }
    
    @Override
    public void setCenterY(float centerY){
        super.setCenterY(centerY);
        this.y = centerY-HEIGHT/2;
    }
    
    @Override
    public void update(GameContainer gc, StateBasedGame sbg, int i) throws SlickException {
        if(!this.detruit){
            setCenterX(getCenterX()+ vitesse.x);
            setCenterY(getCenterY()+ vitesse.y);
            float distance = (float)(Math.sqrt(Math.pow(source.getCenterX()-center[0],2)+Math.pow(source.getCenterY()-center[1],2)));
            if(distance>MAX_DISTANCE){
                Game g = (Game)sbg.getState(States.GAME.getID());
                this.detruit = true;
                g.removeObject(this);
            }
        }
    }
    
    @Override
    public String toString(){
        return "Laser ("+getCenterX()+","+getCenterY()+") angle : "+angle+" vecteur vitesse : "+vitesse;
    }

    @Override
    public boolean intersects(Shape shape){
        if(!this.detruit){
            Shape rotateBlock = this.transform(Transform.createRotateTransform((float)Math.toRadians(angle),this.getCenterX(),this.getCenterY()));
            return rotateBlock.intersects(shape);
        }else
            return false;
    }
    
    @Override
    public void Collide(Game gc, CollideListener c) {
        if(!this.detruit){
            if(c instanceof Meteorite){
                detruit = true;
            }
        }
    }

    /**
     * @return the detruit
     */
    public boolean isDetruit() {
        return detruit;
    }
}
