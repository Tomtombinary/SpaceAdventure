/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import game.Game;
import org.newdawn.slick.geom.Shape;

/**
 *
 * @author thomas
 */
public interface CollideListener {
    public boolean intersects(Shape shape);
    public void Collide(Game gc,CollideListener c);
}
