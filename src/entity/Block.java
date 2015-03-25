/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import controller.Updateable;
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
public abstract class Block extends Rectangle implements Updateable{
    
    
    public static final int WIDTH=32,HEIGHT=32;
    
    protected Spaceship source;
    protected int pv;
    protected int prix;
    protected int type;
    private Vector2f deplacement;
    private float angleSpeed;
    protected float angle;

    public Block(float x, float y, float angle) {
        super(x-WIDTH/2, y-HEIGHT/2,WIDTH,HEIGHT);
        this.angle = angle;
        this.angleSpeed = 0;
        this.deplacement = new Vector2f(0,0);
    }
    
    public Block(){
        super(0,0,WIDTH,HEIGHT);
        this.angle = 0;
    }
    
    @Override
    public boolean intersects(Shape shape){
        Shape rotateBlock = this.transform(Transform.createRotateTransform((float)Math.toRadians(angle),this.getCenterX(),this.getCenterY()));
        return rotateBlock.intersects(shape);
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
    
    /**
     * @return the angle
     */
    public float getAngle() {
        return angle;
    }

    /**
     * @param angle the angle to set
     */
    public void setAngle(float angle) {
        if(angle>=0)
            this.angle = angle%360;
        else
            this.angle = 360+angle%360;
    }
    
    @Override
    public String toString(){
        return " centre ("+center[0]+","+center[1]+") angle : "+angle;
    }

    /**
     * @return the source
     */
    public Spaceship getSource() {
        return source;
    }

    /**
     * @param source the source to set
     */
    public void setSource(Spaceship source) {
        this.source = source;
    }
    
    @Override
    public void update(GameContainer gc, StateBasedGame sbg, int i) throws SlickException{
        if(source==null){
            this.setCenterX(this.getCenterX()+getDeplacement().x);
            this.setCenterY(this.getCenterY()+getDeplacement().y);
            this.setAngle(this.getAngle()+getAngleSpeed());
        }
    }

    /**
     * @return the deplacement
     */
    public Vector2f getDeplacement() {
        return deplacement;
    }

    /**
     * @param deplacement the deplacement to set
     */
    public void setDeplacement(Vector2f deplacement) {
        this.deplacement = deplacement;
    }

    /**
     * @return the angleSpeed
     */
    public float getAngleSpeed() {
        return angleSpeed;
    }

    /**
     * @param angleSpeed the angleSpeed to set
     */
    public void setAngleSpeed(float angleSpeed) {
        this.angleSpeed = angleSpeed;
    }
}
