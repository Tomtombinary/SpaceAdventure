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
public class Armor extends Block{

    public Armor(float x, float y, float angle) {
        super(x, y, angle);
    }
    
    public Armor(){
        super();
    }
    
    @Override
    public String toString(){
        return "Block d'armure : "+super.toString();
    }
}
