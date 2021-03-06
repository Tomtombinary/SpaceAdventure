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
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Transform;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;
import render.MeteoriteRenderer;

/**
 *
 * @author thomas
 */
public class Meteorite extends Rectangle implements Updateable, CollideListener, Destroyable {

    private int type;
    public static final int SMALL = 0, MEDIUM = 1, BIG = 2;

    public static final int SMALL_SIZE = 32, MEDIUM_SIZE = 64, BIG_SIZE = 128;
    private int pv;
    private float angle;
    private float angleSpeed;
    private Vector2f vitesse;

    /**
     * Créer une météorite plus petite en fonction de la météorite passé en
     * paramètre
     *
     * @param m météorite
     */
    public Meteorite(Meteorite m) {
        super(0, 0, 0, 0);
        switch (m.type) {
            case MEDIUM:
                this.type = SMALL;
                this.pv = 2;
                setWidth(SMALL_SIZE);
                setHeight(SMALL_SIZE);
                break;
            case BIG:
                this.type = MEDIUM;
                this.pv = 4;
                setWidth(MEDIUM_SIZE);
                setHeight(MEDIUM_SIZE);
                break;
        }
        setCenterX(m.getCenterX());
        setCenterY(m.getCenterY());
        this.angle = 0;
        this.angleSpeed = (float) (Math.random() * 2);
        this.vitesse = m.getVitesse();
    }

    /**
     * Créer un métorite avec pour centre x,y type = SMALL,MEDIUM,BIG
     *
     * @param x centre x
     * @param y centre y
     * @param type type de météorite ou taille SMALL,MEDIUM,BIG
     */
    public Meteorite(float x, float y, int type) {
        super(0, 0, 0, 0);
        this.type = type;
        switch (type) {
            case SMALL:
                this.pv = 2;
                setWidth(SMALL_SIZE);
                setHeight(SMALL_SIZE);
                break;
            case MEDIUM:
                this.pv = 4;
                setWidth(MEDIUM_SIZE);
                setHeight(MEDIUM_SIZE);
                break;
            case BIG:
                this.pv = 16;
                setWidth(BIG_SIZE);
                setHeight(BIG_SIZE);
                break;
        }
        this.setCenterX(x);
        this.setCenterY(y);
        this.angle = 0;
        this.angleSpeed = (float) (Math.random() * 2);
        this.vitesse = new Vector2f(
                (float) (Math.random() * 10 - 5) * 0.1f,
                (float) (Math.random() * 10 - 5) * 0.1f
        );
    }

    public Vector2f getVitesse() {
        return this.vitesse;
    }

    /**
     * Défini un nouveau vecteur vitesse (ou vecteur de direction) pour la
     * météorite
     *
     * @param v
     */
    public void setVitesse(Vector2f v) {
        this.vitesse = v;
    }

    @Override
    public void setCenterX(float x) {
        super.setCenterX(x);
        this.x = this.getCenterX() - this.getWidth() / 2;
    }

    @Override
    public void setCenterY(float y) {
        super.setCenterY(y);
        this.y = this.getCenterY() - this.getHeight() / 2;
    }

    @Override
    public void update(GameContainer gc, StateBasedGame sbg, int i) throws SlickException {
        this.setCenterX(this.getCenterX() + vitesse.x);
        this.setCenterY(this.getCenterY() + vitesse.y);
        this.setAngle(this.getAngle() + angleSpeed);
    }

    /**
     * @param angle the angle to set
     */
    public void setAngle(float angle) {
        if (angle >= 0) {
            this.angle = angle % 360;
        } else {
            this.angle = 360 + angle % 360;
        }
    }

    /**
     * @return the angle
     */
    public float getAngle() {
        return angle;
    }

    /**
     * @return the pv
     */
    public int getPv() {
        return pv;
    }

    /**
     * @param pv the pv to set
     */
    public void setPv(int pv) {
        this.pv = pv;
        if (this.pv < 0) {
            this.pv = 0;
        }
    }

    public int getType() {
        return this.type;
    }

    @Override
    public boolean isDetruit() {
        return (this.pv == 0);
    }

    @Override
    public boolean intersects(Shape shape) {
        Shape rotateBlock = this.transform(Transform.createRotateTransform((float) Math.toRadians(angle), this.getCenterX(), this.getCenterY()));
        return (rotateBlock.intersects(shape) || rotateBlock.contains(shape));
    }

    @Override
    public String toString() {
        return "Metorite : " + type + " pv : " + pv;
    }

    /**
     * Méthode de collision , si la météorite est touché par un laser ,
     * décremente ses PV si la météorite est détruite créer des debris .
     *
     * @param gc Game container
     * @param c Objet en collision avec la météorite
     */
    @Override
    public void Collide(Game gc, CollideListener c) {
        if (this.pv > 0) {
            if (c instanceof Laser) {
                ((Destroyable)c).detruire(gc);
                this.setPv(this.getPv() - 1);
                if (this.pv <= 0) {
                    this.detruire(gc);
                }
        }
        }
    }

    @Override
    public void detruire(Game gc) {
        gc.removeCollideListener(this);
        gc.removeObject(this);
        switch (type) {
            case MEDIUM:
                Meteorite u1 = new Meteorite(this);
                MeteoriteRenderer r1 = new MeteoriteRenderer(u1);

                Vector2f v1 = new Vector2f(
                        (float) (Math.cos(Math.toRadians(45)) * vitesse.x - Math.sin(Math.toRadians(45)) * vitesse.y),
                        (float) (Math.sin(Math.toRadians(45)) * vitesse.x + Math.sin(Math.toRadians(45)) * vitesse.y)
                );
                u1.setVitesse(v1);
                gc.addCollideListener(u1);
                gc.addNewObject(u1, r1);

                Meteorite u2 = new Meteorite(this);
                MeteoriteRenderer r2 = new MeteoriteRenderer(u2);

                Vector2f v2 = new Vector2f(
                        (float) (Math.cos(Math.toRadians(-45)) * vitesse.x - Math.sin(Math.toRadians(-45)) * vitesse.y) * 2f,
                        (float) (Math.sin(Math.toRadians(-45)) * vitesse.x + Math.cos(Math.toRadians(-45)) * vitesse.y) * 2f
                );
                u2.setVitesse(v2);
                gc.addCollideListener(u2);
                gc.addNewObject(u2, r2);
                break;
            case BIG:
                for (int i = 0; i < 4; i++) {
                    Meteorite u = new Meteorite(this);
                    MeteoriteRenderer r = new MeteoriteRenderer(u);
                    Vector2f v = new Vector2f(
                            (float) (Math.cos(Math.toRadians(i * 90 + 45)) * vitesse.x - Math.sin(Math.toRadians(i * 90 + 45)) * vitesse.y) * 4f,
                            (float) (Math.sin(Math.toRadians(i * 90 + 45)) * vitesse.x + Math.cos(Math.toRadians(i * 90 + 45)) * vitesse.y) * 4f
                    );
                    u.setVitesse(v);
                    gc.addCollideListener(u);
                    gc.addNewObject(u, r);
                }
                break;
        }
    }

}
