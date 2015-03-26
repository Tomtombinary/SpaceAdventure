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
