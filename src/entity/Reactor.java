/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

/**
 *
 * @author thomas
 */
public class Reactor extends Block{
    
    private float acc;
    private float max_speed;
    
    public Reactor(){
        super();
    }
    
    public Reactor(float acc,float max_speed){
        this.acc = acc;
        this.max_speed = max_speed;
    }
    
    public Reactor(float x,float y,float angle){
        super(x,y,angle);
    }

    /**
     * @return the acc
     */
    public float getAcc() {
        return acc;
    }

    /**
     * @param acc the acc to set
     */
    public void setAcc(float acc) {
        this.acc = acc;
    }

    /**
     * @return the max_speed
     */
    public float getMax_speed() {
        return max_speed;
    }

    /**
     * @param max_speed the max_speed to set
     */
    public void setMax_speed(float max_speed) {
        this.max_speed = max_speed;
    }
}
