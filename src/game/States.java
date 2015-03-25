/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

/**
 *
 * @author thomas
 */
public enum States {
    GAME(1),
    MENU(2);
    
    private int id;
    
    private States(int id){
        this.id = id;
    }
    
    public int getID(){
        return this.id;
    }
}
